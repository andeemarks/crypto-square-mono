(defproject column-handler "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.1.6"]
                 [ring/ring-json "0.3.1"]
                 [riemann-clojure-client "0.2.11"]
                 [metrics-clojure "2.5.1"]
                 [ring.middleware.logger "0.5.0"]
                 [ring-server "0.3.1"]]
  :plugins [[lein-ring "0.8.12"]]
  :ring {:handler column-handler.handler/app
         :init column-handler.handler/init
         :destroy column-handler.handler/destroy}
  :profiles
  {:uberjar {:aot :all}
   :production
   {:ring
    {:open-browser? false, :stacktraces? false, :auto-reload? false}}
   :dev
   {:dependencies [ [midje "1.6.3"]
                    [ring-mock "0.1.5"] 
                    [ring/ring-devel "1.3.1"]]
    :plugins [[lein-midje "3.0.0"]]}})
