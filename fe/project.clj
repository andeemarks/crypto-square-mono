(defproject crypto-square "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [compojure "1.6.2"]
                 [hiccup "1.0.5"]
                 [clj-http "3.10.3"]
                 [onelog "0.5.0" :exclusions [io.aviso/pretty]]
                 [ring.middleware.logger "0.5.0" :exclusions [log4j]]
                 [org.clojure/tools.logging "1.1.0"]
                 [metrics-clojure "2.10.0"  :exclusions [org.slf4j/slf4j-api]]
                 [metrics-clojure-health "2.10.0"  :exclusions [org.slf4j/slf4j-api]]
                 [metrics-clojure-ring "2.10.0" :exclusions [org.slf4j/slf4j-api]]
                 [environ "1.2.0"]
                 [ring/ring-json "0.5.0"]
                 [org.eclipse.jetty/jetty-http "9.4.22.v20191022"]
                 [ring-server "0.5.0"]]
  :plugins [[lein-ring "0.12.5"]]
  :ring {:handler crypto-square.handler/app
         :init crypto-square.handler/init
         :destroy crypto-square.handler/destroy}
  :test-selectors { :default (complement :synth)
                    :synth :synth
                    :all (constantly true)}
  :profiles
  {:uberjar {:aot :all}
   :production
   {:ring
    {:open-browser? false, :stacktraces? false, :auto-reload? false}}
   :dev
   {:dependencies [
                   [clj-webdriver "0.7.2" :exclusions [org.seleniumhq.selenium/selenium-server]]
                   [org.seleniumhq.selenium/selenium-server "3.141.59" :exclusions 
                    [[org.eclipse.jetty/jetty-client]
                     [org.eclipse.jetty/jetty-xml]
                     [org.eclipse.jetty.websocket/websocket-api]
                     [org.eclipse.jetty.websocket/websocket-client]
                     [org.eclipse.jetty.websocket/websocket-common]
                     [org.apache.commons/commons-text]]]
                   [ring-mock "0.1.5"] 
                   [midje "1.9.9"]
                   [ring/ring-jetty-adapter "1.8.2"]
                   [ring/ring-devel "1.8.2"]]
    :plugins [[lein-midje "3.1.3"]]}})
