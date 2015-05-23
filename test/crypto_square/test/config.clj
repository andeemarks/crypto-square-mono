(ns crypto-square.test.config
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [clj-webdriver.taxi :refer :all]
            [crypto-square.routes.home :refer [home-routes]]))

(def test-port 5744)
(def test-host "localhost")
(def test-base-url (str "http://" test-host ":" test-port "/"))

(defn start-server []
  (loop [server (run-jetty home-routes {:port test-port, :join? false})]
    (if (.isStarted server)
      server
      (recur server))))

(defn stop-server [server]
  (.stop server))

(defn start-browser []
  (set-driver! {:browser :chrome}))

(defn stop-browser []
  (quit))

(defn with-server [t]
  (let [server (start-server)]
    (t)
    (stop-server server)))

(defn with-browser [t]
  (System/setProperty "webdriver.chrome.driver" (str (System/getenv "HOME") "/bin/chromedriver"))
  (start-browser)
  (t)
  (stop-browser))
