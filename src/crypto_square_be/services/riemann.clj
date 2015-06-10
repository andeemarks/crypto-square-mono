(ns crypto-square-be.services.riemann
  (:require 
    [clojure.tools.logging :as log]
    [environ.core :refer [env]]
    [riemann.client :as riemann]))

(defn send-event [plaintext elapsed-time corr-id]
  (try
    (let [c (riemann/tcp-client {:host (env :riemann-host)})]
      (riemann/send-event c
        {:service "crypto-square-be" :metric (/ elapsed-time 1000000) :state "ok" :description corr-id})
      (riemann/close-client c))
    (catch java.io.IOException ex 
      (log/warn "Cannot find Riemann!"))))
