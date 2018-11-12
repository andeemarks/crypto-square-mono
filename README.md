# crypto-square

[![Build Status](https://travis-ci.org/andeemarks/crypto-square-mono.svg?branch=master)](https://travis-ci.org/andeemarks/crypto-square-mono)

A minimal Compojure web-app built for a presentation at [Yow West 2015][1], and providing a basic UI for the Crypto Square solution found [here][3].

![](https://github.com/andeemarks/column-handler/blob/master/resources/public/img/services.png)

## Prerequisites

Currently building on: 
- [Leiningen][2] 2.8.1
- [Docker][6] 1.38
- [Open JDK][11] 9 
- [Docker Machine][7] 0.14.0 (install instructions [here][9])
- [Docker Compose][8] 1.17.2 (install instructions [here][10])

## Running

To make deployment and startup of the application easier, the repo for the front-end (crypto-square) is configured to start all the services using Docker Compose.  

To start all the services for the application, run:

    docker-compose up

Note: the first time you run this command, it make take 20 minutes to download all the dependencies to run the services.  This is a bootstrapping tax and subsequent invocations should be very quick.

To access the front-end of the application, you need to point your browser to port 3000 on the IP of the Docker VM.  The easiest way to do this is often via a command like:

	eval "open http://$(docker-machine ip):3000"
	
## Browser Tests

To test the app using [clj-webdriver][4] on port 4000, run:

    lein midje

Note: this assumes you have the [Chrome Driver][5] installed at ~/bin/chromedriver

## License

Copyright © 2015

[1]: https://a.confui.com/-LsHgG00I
[2]: https://github.com/technomancy/leiningen
[3]: http://garajeando.blogspot.com.au/2015/05/exercism-crypto-square-in-clojure.html
[4]: https://github.com/semperos/clj-webdriver
[5]: https://sites.google.com/a/chromium.org/chromedriver/
[6]: https://www.docker.com/
[7]: https://www.docker.com/products/docker-machine
[8]: https://www.docker.com/products/docker-compose
[9]: https://docs.docker.com/machine/install-machine/#install-machine-directly
[10]: https://docs.docker.com/compose/install/
[11]: https://openjdk.java.net/install/