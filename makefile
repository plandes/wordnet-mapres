# build file for Wordnet MapDictionary

TARG=		target
WNDIST_VER=	1.9.3
WNDIR=		$(TARG)/wn
WNDIST_DIR=	$(WNDIR)/dist
WNDIST_ARCH=	$(WNDIST_DIR)/wnarch.zip
WNDIST_EXT=	$(WNDIST_DIR)/dist
WNDAT_EXT=	$(WNDIR)/ext
WNDAT_SRC=	$(WNDAT_EXT)/net/sf/extjwnl/data/wordnet/wn31
WNDAT_DIR=	$(WNDIR)/dat
DICT2MAP=	$(WNDIST_EXT)/extjwnl-$(WNDIST_VER)/bin/dict2map

all:	tmp

tmp:	$(WNDAT_EXT) $(WNDIST_EXT)
	mkdir -p $(WNDAT_DIR)
	/bin/bash $(DICT2MAP) $(WNDAT_SRC) $(WNDAT_DIR)
	ls $(WNDAT_DIR)

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
test:
	mvn -Plogging-deps test

.PHONY:	package
package:
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
