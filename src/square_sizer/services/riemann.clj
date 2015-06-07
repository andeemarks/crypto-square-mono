(ns square-sizer.services.riemann
  (:require 
    [clojure.tools.logging :as log]
    [riemann.client :as riemann]))

(defn- state [] 
  (if (= 1 (rand-int 20))
    "failure"
    "ok"))

(defn- metric [elapsed-time]
  (/ elapsed-time 1000000))

(defn send-event [corr-id elapsed-time]
  (try
    (let [c (riemann/tcp-client {:host "127.0.0.1"})]
          (riemann/send-event c
                  {:service "square-sizer" 
                   :metric (metric elapsed-time) 
                   :state (state) 
                   :description corr-id})
          (riemann/close-client c))
    (catch java.io.IOException ex 
      (log/warn "Cannot find Riemann!"))))
