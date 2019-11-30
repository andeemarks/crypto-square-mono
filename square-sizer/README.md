# square-sizer

[![Build Status](https://travis-ci.org/andeemarks/square-sizer.svg?branch=master)](https://travis-ci.org/andeemarks/square-sizer)

A toy Compojure microservice built for a presentation at [Yow West 2015][1].

This service implements a small part of the Crypto Square solution found [here][3].  The granularity of this service is extreme and should _not_ be used as an example of how to pull apart a larger solution into services.

[3]: http://garajeando.blogspot.com.au/2015/05/exercism-crypto-square-in-clojure.html

[1]: https://a.confui.com/-LsHgG00I

## Prerequisites

Currently building on [Leiningen][2] 2.5.1

[2]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application locally, run:

    ./up.sh

## Tests

To run the test suite:

	lein test

To manually test a hosted version, try:

    curl https://cs-square-sizer.herokuapp.com/abcd

## License

Copyright © 2015
