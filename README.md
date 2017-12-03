# WordNet MapDictionary

This is an implementation of a [WordNet] Resource based MapBackedDictionary and
Data.  It is an extension of the [extJWNL] Java library.  The compiled jar not
only has an instance of a [MapBackedDictionary] but a compiled version of the
WordNet data.

**Important:** This repo is deprecated and moved to the [extjwnl map] project.


<!-- markdown-toc start - Don't edit this section. Run M-x markdown-toc-refresh-toc -->
## Table of Contents

- [Obtaining](#obtaining)
- [Usage](#usage)
- [Documentation](#documentation)
- [Process](#process)
- [WordNet Data](#wordnet-data)
- [Building](#building)
- [Changelog](#changelog)
- [License](#license)

<!-- markdown-toc end -->



## Obtaining

In your `pom.xml` file add
the
[dependency XML element](https://plandes.github.io/wordnet-mapres/dependency-info.html) below:
```xml
<dependency>
    <groupId>com.zensols.nlp</groupId>
    <artifactId>wordnet-mapres</artifactId>
    <version>0.0.1</version>
</dependency>
```


## Usage

Like any [extJWNL] dictionary, you resolve the dictionary using
`Dictionary.getResourceInstance()`, but use the resource XML given in the
library as such:

```java
Dictionary dict = Dictionary.getResourceInstance("/net/sf/extjwnl/data/wordnet/wn31/map/res_properties.xml");
IndexWordSet wordSet = dict.lookupAllIndexWords("cat");
Assert.assertNotNull(wordSet);
IndexWord indexWord = wordSet.getIndexWord(POS.NOUN);
System.out.println(indexWord)
```

See the [unit test] for a more in-depth example.


## Documentation

More [documentation](https://plandes.github.io/wordnet-mapres/):
* [Javadoc](https://plandes.github.io/wordnet-mapres/apidocs/index.html)
* [Dependencies](https://plandes.github.io/wordnet-mapres/dependencies.html)


## Process

The data file is built from the [WordNet] dependency data file (see [WordNet
Data section](#wordnet-data)) by:
1. Downloading by depndency definition
2. Extracting the jar data files
3. Adding XML dictionary *properties file*.
4. Compiling and adding a Princeton object map backed dictionary
   implementation.

This is done with the `make wndat` command (see [build section](#building)).


## WordNet Data

The WordNet data comes from the latest [extJWNL] release:
```xml
<dependency>
    <groupId>net.sf.extjwnl</groupId>
    <artifactId>extjwnl-data-wn31</artifactId>
    <version>1.2</version>
</dependency>
```


## Building

To build from source, do the folling:

- Install [Maven](https://maven.apache.org)
- Install [GNU make](https://www.gnu.org/software/make/) (optional)
- Build the software: `make`
- Build the distribution binaries: `make package`


## Changelog

An extensive changelog is available [here](CHANGELOG.md).


## License

Copyright © 2017 Paul Landes

Apache License version 2.0

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


<!-- links -->
[MapBackedDictionary]: http://extjwnl.sourceforge.net/javadocs/net/sf/extjwnl/dictionary/MapBackedDictionary.html
[WordNet]: https://wordnet.princeton.edu
[extJWNL]: http://extjwnl.sourceforge.net
[unit test]: src/test/java/com/zensols/nlp/wnmap/princeton/file/PrincetonResourceObjectDictionaryFileTest.java
[extjwnl map]: https://github.com/extjwnl/extjwnl-data-wn31-map/
