(ns column-handler.services.riemann
  (:require [clojure.tools.logging :as log]
            [riemann.client :as riemann]))

(defn send-event [elapsed-time corr-id]
  (try
    (let [c (riemann/tcp-client {:host "127.0.0.1"})]
      (riemann/send-event c
        {:service "column-handler" :metric (/ elapsed-time 1000000) :state "ok" :description corr-id})
      (riemann/close-client c))
    (catch java.io.IOException ex 
      (log/warn "Cannot find Riemann!"))))
