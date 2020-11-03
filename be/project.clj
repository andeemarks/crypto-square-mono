(defproject crypto-square-be "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [compojure "1.6.2"]
                 [metosin/compojure-api "1.1.13"]
                 [cheshire "5.10.0"]
                 [clj-http "3.10.3"]
                 [org.clojure/tools.logging "1.1.0"]
                 [onelog "0.5.0" :exclusions [io.aviso/pretty]]
                 [metrics-clojure "2.10.0"  :exclusions [org.slf4j/slf4j-api]]
                 [metrics-clojure-health "2.10.0"  :exclusions [org.slf4j/slf4j-api]]
                 [environ "1.2.0"]
                 [ring/ring-json "0.5.0"]
                 [ring.middleware.logger "0.5.0"]
                 [ring-server "0.5.0"]]
  :plugins [[lein-ring "0.12.5"]]
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
                   [clj-fakes "0.12.0"]
                   [ring/ring-devel "1.8.2"]]}})
