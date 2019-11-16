(defproject column-handler "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [compojure "1.6.1"]
                 [ring/ring-json "0.5.0"]
                 [metosin/compojure-api "1.1.13"]
                 [metrics-clojure "2.10.0" :exclusions [org.slf4j/slf4j-api]]
                 [metrics-clojure-health "2.10.0" :exclusions [org.slf4j/slf4j-api]]
                 [environ "1.1.0"]
                 [ring.middleware.logger "0.5.0"]
                 [ring-server "0.5.0"]]
  :plugins [[lein-ring "0.12.5"]]
  :ring {:handler column-handler.handler/app
         :init column-handler.handler/init
         :destroy column-handler.handler/destroy}
  :min-lein-version "2.8.0"
  :profiles
  {:uberjar {:aot :all}
   :production
   {:ring
    {:open-browser? false, :stacktraces? false, :auto-reload? false}}
   :dev
   {:dependencies [[ring-mock "0.1.5"]
                   [ring/ring-devel "1.8.0"]]}})
