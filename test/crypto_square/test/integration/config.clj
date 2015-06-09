(ns crypto-square.test.integration.config
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [clj-webdriver.taxi :refer :all]
            [crypto-square.routes.home :refer [home-routes]]))

(def test-port 4000)
(def test-host "localhost")
(def test-base-url (str "http://" test-host ":" test-port "/"))

(defn start-server []
  (loop [server (run-jetty home-routes {:port test-port, :join? false})]
    (if (.isStarted server)
      server
      (recur server))))

(defn stop-server [server]
  (.stop server))

(defn with-server [t]
  (let [server (start-server)]
    (t)
    (stop-server server)))

(def ^:private browser-count (atom 0))

(defn browser-up
  "Start up a browser if it's not already started."
  []
  (System/setProperty "webdriver.chrome.driver" (str (System/getenv "HOME") "/bin/chromedriver"))
  (when (= 1 (swap! browser-count inc))
    (set-driver! {:browser :chrome})
    (implicit-wait 6000)))

(defn browser-down
  "If this is the last request, shut the browser down."
  [& {:keys [force] :or {force false}}]
  (when (zero? (swap! browser-count (if force (constantly 0) dec)))
    (quit)))
