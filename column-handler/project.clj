(defproject column-handler "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [compojure "1.4.0"]
                 [ring/ring-json "0.3.1"]
                 [riemann-clojure-client "0.4.1"]
                 [metrics-clojure "2.5.1"]
                 [metrics-clojure-health "2.5.1"]
                 [environ "1.0.0"]
                 [ring.middleware.logger "0.5.0"]
                 [ring-server "0.4.0"]]
  :plugins [[lein-ring "0.9.6"]]
  :ring {:handler column-handler.handler/app
         :init column-handler.handler/init
         :destroy column-handler.handler/destroy}
  :min-lein-version "2.0.0"         
  :profiles
  {:uberjar {:aot :all}
   :production
   {:ring
    {:open-browser? false, :stacktraces? false, :auto-reload? false}}
   :dev
   {:dependencies [ [midje "1.7.0"]
                    [ring-mock "0.1.5"] 
                    [ring/ring-devel "1.4.0"]]
    :plugins [[lein-midje "3.1.3"]]}})
