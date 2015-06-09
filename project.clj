(defproject crypto-square "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.1.6"]
                 [hiccup "1.0.5"]
                 [clj-http "1.1.2"]
                 [ring.middleware.logger "0.5.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [metrics-clojure "2.5.1"]
                 [metrics-clojure-ring "2.0.0"]
                 [riemann-clojure-client "0.2.11"]
                 [ring-server "0.3.1"]]
  :plugins [[lein-ring "0.8.12"]]
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
      [clj-webdriver "0.6.1" :exclusions [org.seleniumhq.selenium/selenium-server]]
      [org.seleniumhq.selenium/selenium-server "2.45.0"]      
      [ring-mock "0.1.5"] 
      [midje "1.6.3"]
      [ring/ring-jetty-adapter "1.3.2"]
      [ring/ring-devel "1.3.1"]]
    :plugins [[lein-midje "3.0.0"]]}})
