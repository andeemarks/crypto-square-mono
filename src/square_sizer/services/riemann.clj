(ns square-sizer.services.riemann
  (:require 
    [clojure.tools.logging :as log]
    [environ.core :refer [env]]
    [riemann.client :as riemann]))

(defn- state [] 
  (if (= 1 (rand-int 10))
    "failure"
    "ok"))

(defn- metric [elapsed-time]
  (/ elapsed-time 1000000))

(defn send-event [corr-id elapsed-time]
  (try
    (let [c (riemann/tcp-client {:host (env :riemann-url)})]
          (riemann/send-event c
                  {:service "square-sizer" 
                   :metric (metric elapsed-time) 
                   :state (state) 
                   :description corr-id}))
    (catch java.lang.Exception ex
      (log/warn (str "Error connecting to Riemann using config:" (env :riemann-host))))
    (catch java.io.IOException ex 
      (log/warn (str "Cannot find Riemann at: " (env :riemann-host))))))