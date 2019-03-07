(defproject crypto-square-be "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [cheshire "5.8.1"]
                 [clj-http "3.9.1"]
                 [org.clojure/tools.logging "0.4.1"]
                 [onelog "0.5.0" :exclusions [io.aviso/pretty]]
                 [metrics-clojure "2.10.0"  :exclusions [org.slf4j/slf4j-api]]
                 [metrics-clojure-health "2.10.0"  :exclusions [org.slf4j/slf4j-api]]
                 [environ "1.1.0"]
                 [ring/ring-json "0.4.0"]
                 [ring.middleware.logger "0.5.0"]
                 [ring-server "0.5.0"]]
  :plugins [[lein-ring "0.9.6"]]
  :ring {:handler crypto-square-be.handler/app
         :init crypto-square-be.handler/init
         :destroy crypto-square-be.handler/destroy}
  :profiles
  {:uberjar {:aot :all}
   :production
   {:ring
    {:open-browser? false, :stacktraces? false, :auto-reload? false}}
   :dev
   {:dependencies [[ring-mock "0.1.5"]
                   [clj-fakes "0.11.0"]
                   [ring/ring-devel "1.7.1"]]}})
