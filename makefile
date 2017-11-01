# build file for Wordnet MapDictionary

# maven
TARG=		target
PRJ_CLASS_DIR=	$(TARG)/classes

# wordnet data
WNDIST_VER=	1.9.3
WNDIR=		$(TARG)/wn
WNDIST_DIR=	$(WNDIR)/dist
WNDIST_ARCH=	$(WNDIST_DIR)/wnarch.zip
WNDIST_EXT=	$(WNDIST_DIR)/dist
WNDAT_EXT=	$(WNDIR)/ext
WNRES_PATH=	net/sf/extjwnl/data/wordnet/wn31
WNDAT_SRC=	$(WNDAT_EXT)/$(WNRES_PATH)
WNDAT_MAP_DIR=	$(WNDIR)/dat
WNDAT_DIR=	$(WNDAT_MAP_DIR)/$(WNRES_PATH)/map
WNDAT_CLS_DIR=	$(PRJ_CLASS_DIR)/net
DICT2MAP=	$(WNDIST_EXT)/extjwnl-$(WNDIST_VER)/bin/dict2map

all:	compile

.PHONY:		wndat
wndat:		$(WNDAT_CLS_DIR)

$(WNDAT_CLS_DIR):	$(WNDAT_DIR)
	mkdir -p $(PRJ_CLASS_DIR)
	cp -r $(WNDAT_MAP_DIR)/* $(PRJ_CLASS_DIR)

$(WNDAT_DIR):	$(WNDAT_EXT) $(WNDIST_EXT)
	mkdir -p $(WNDAT_DIR)
	/bin/bash $(DICT2MAP) src/data-resources/build_res_properties.xml $(WNDAT_DIR)
	cp src/data-resources/res_properties.xml $(WNDAT_DIR)

$(WNDAT_EXT):
	mvn -Pwordnet-data-deps dependency:unpack

$(WNDIST_EXT):	$(WNDIST_DIR)
	mkdir -p $(WNDIST_EXT)
	unzip $(WNDIST_ARCH) -d $(WNDIST_EXT) > /dev/null

$(WNDIST_DIR):
	mkdir -p $(WNDIST_DIR)
	wget -O $(WNDIST_ARCH) https://sourceforge.net/projects/extjwnl/files/extjwnl-$(WNDIST_VER).zip/download

.PHONY:	compile
compile:
	mvn compile

.PHONY:	test
test:		wndat
	mvn -Plogging-deps -Ptest-deps test

.PHONY:	package
package:	wndat
	mvn -Plogging-deps package

.PHONY:	site
site:
	mvn -Plogging-deps site

.PHONY:	run
run:
	mvn -Plogging-deps compile exec:exec

.PHONY:	docs
docs:
	mvn javadoc:javadoc

.PHONY:	dist
dist:
	mvn -Plogging-deps package appassembler:assemble

.PHONY:	clean
clean:
	rm -fr $(TARG)
	rm -fr src/site/markdown
