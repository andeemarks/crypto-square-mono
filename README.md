# crypto-square-be

[![Build Status](https://travis-ci.org/andeemarks/crypto-square-be.svg?branch=master)](https://travis-ci.org/andeemarks/crypto-square-be)

A toy Compojure microservice built for a presentation at [Yow West 2015][1].

This service is a top-level orchestrator of the Crypto Square solution found [here][3].

![](https://github.com/andeemarks/column-handler/blob/master/resources/public/img/services.png)

[3]: http://garajeando.blogspot.com.au/2015/05/exercism-crypto-square-in-clojure.html

[1]: https://a.confui.com/-LsHgG00I

## Prerequisites

Currently building on [Leiningen][2] 2.5.1

[2]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    ./up.sh

## Tests

To run the test suite:

	lein midje

## License

Copyright © 2015
