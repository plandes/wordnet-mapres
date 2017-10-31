# build file for Wordnet MapDictionary

TARG=		target
WNDIST_DIR=	$(TARG)/wndist
WNDIST_ARCH=	$(WNDIST_DIR)/wnarch.zip

all:	tmp

tmp:	$(WNDIST_DIR)

$(WNDIST_DIR):
	mkdir -p $(WNDIST_DIR)
	wget -O $(WNDIST_ARCH) https://sourceforge.net/projects/extjwnl/files/extjwnl-1.9.3.zip/download
#	mvn -Pwordnet-data-deps dependency:unpack

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
