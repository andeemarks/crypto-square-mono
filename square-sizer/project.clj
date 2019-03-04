(defproject square-sizer "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [cheshire "5.8.1"]
                 [ring.middleware.logger "0.5.0"]
                 [onelog "0.5.0" :exclusions [io.aviso/pretty]]
                 [org.clojure/tools.logging "0.4.1"]
                 [metrics-clojure "2.10.0" :exclusions [org.slf4j/slf4j-api]]
                 [metrics-clojure-health "2.10.0" :exclusions [org.slf4j/slf4j-api]]
                 [ring/ring-json "0.4.0"]
                 [environ "1.1.0"]
                 [ring-server "0.5.0"]]
  :plugins [[lein-ring "0.9.6"]]
  :ring {:handler square-sizer.handler/app
         :init square-sizer.handler/init
         :destroy square-sizer.handler/destroy}
  :min-lein-version "2.0.0"         
  :profiles
  {:uberjar {:aot :all}
   :production
   {:ring
    {:open-browser? false, :stacktraces? false, :auto-reload? false}}
   :dev
   {:dependencies [ [midje "1.9.6"]
                    [ring-mock "0.1.5"] 
                    [ring/ring-devel "1.7.1"]]
    :plugins [[lein-midje "3.1.3"]]}})
