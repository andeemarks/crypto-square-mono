(defproject crypto-square "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [compojure "1.4.0"]
                 [hiccup "1.0.5"]
                 [clj-http "2.0.0"]
                 [ring.middleware.logger "0.5.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [metrics-clojure "2.5.1"]
                 [metrics-clojure-ring "2.5.1"]
                 [metrics-clojure-health "2.5.1"]
                 [environ "1.0.0"]
                 [ring/ring-json "0.4.0"]
                 [ring-server "0.4.0"]]
  :plugins [[lein-ring "0.9.6"]]
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
      [org.seleniumhq.selenium/selenium-server "2.46.0"]      
      [ring-mock "0.1.5"] 
      [midje "1.7.0"]
      [ring/ring-jetty-adapter "1.4.0"]
      [ring/ring-devel "1.4.0"]]
    :plugins [[lein-midje "3.1.3"]]}})
