(defproject normaliser "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.6.1"]
                 [org.clojure/tools.logging "0.4.1"]
                 [ring.middleware.logger "0.5.0"]
                 [environ "1.1.0"]
                 [metrics-clojure "2.10.0"]
                 [metrics-clojure-health "2.10.0"]
                 [ring/ring-json "0.4.0"]
                 [ring-server "0.5.0"]]
  :plugins [[lein-ring "0.9.6"]]
  :ring {:handler normaliser.handler/app
         :init normaliser.handler/init
         :destroy normaliser.handler/destroy}
  :min-lein-version "2.0.0"         
  :profiles
  {:uberjar {:aot :all}
   :production
   {:ring
    {:open-browser? false, :stacktraces? false, :auto-reload? false}}
   :dev
   {:dependencies [ [ring/ring-codec "1.1.1"]
                    [midje "1.9.1"] 
                    [ring-mock "0.1.5"] 
                    [ring/ring-devel "1.6.3"]]
    :plugins [[lein-midje "3.1.3"]]}})
