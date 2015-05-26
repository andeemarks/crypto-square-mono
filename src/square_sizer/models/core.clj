(ns square-sizer.models.core
  (:require 
    [clojure.tools.logging :as log]
    [metrics.timers :as timer]
    [riemann.client :as riemann]))

(timer/deftimer processing-time)

(defn- state [] 
  (if (= 1 (rand-int 20))
    "failure"
    "ok"))

(defn- metric [elapsed-time]
  (/ elapsed-time 1000000))

(defn- send-event [corr-id elapsed-time]
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

(defn- calculate-square-size [text]
  (int (Math/ceil (Math/sqrt (count text)))))

(defn square-size [text corr-id]
  (let [timer (timer/start processing-time)
        result (calculate-square-size text)
        elapsed-time (timer/stop timer)]
      (send-event corr-id elapsed-time)
      result))
