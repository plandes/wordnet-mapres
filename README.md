# Wordnet MapDictionary

This is an implementation of a [WordNet] Resource based MapBackedDictionary and
Data.  It is an extension of the [extJWNL] Java library.  The compiled jar not
only has an instance of a [MapBackedDictionary] but a compiled version of the
WordNet data.


## Obtaining

In your `pom.xml` file add
the
[dependency XML element](https://plandes.github.io/wnmap/dependency-info.html) below:
```xml
<dependency>
    <groupId>com.zensols.nlp</groupId>
    <artifactId>wnmap</artifactId>
    <version>0.0.1</version>
</dependency>
```


## WordNet Data

The WordNet data comes from the latest [extJWNL] release:
```xml
<dependency>
    <groupId>net.sf.extjwnl</groupId>
    <artifactId>extjwnl-data-wn31</artifactId>
    <version>1.2</version>
</dependency>
```


## Documentation

More [documentation](https://plandes.github.io/wnmap/):
* [Javadoc](https://plandes.github.io/wnmap/apidocs/index.html)
* [Dependencies](https://plandes.github.io/wnmap/dependencies.html)


## Building

To build from source, do the folling:

- Install [Maven](https://maven.apache.org)
- Install [GNU make](https://www.gnu.org/software/make/) (optional)
- Build the software: `make`
- Build the distribution binaries: `make dist`

Note that you can also build a single jar file with all the dependencies with: `make package`


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
