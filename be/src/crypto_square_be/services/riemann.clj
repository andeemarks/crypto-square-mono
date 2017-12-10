(ns crypto-square-be.services.riemann
  (:require 
    [clojure.tools.logging :as log]
    [environ.core :refer [env]]
    [metrics.health.core :as health]
    [riemann.client :as riemann]))

(defn available? []
  (try
    (let [c (riemann/tcp-client {:host (env :riemann-host)})]
      (riemann/connected? c))
    (catch java.io.IOException ex 
      false)))

(health/defhealthcheck "riemann-available?" (fn [] (if (not (available?))
                                            (health/unhealthy "riemann is unavailable!")
                                            (health/healthy "riemann is available!"))))

(defn healthcheck []
  (let [health (health/check riemann-available?)]
    {:healthy? (.isHealthy health) :message (.getMessage health)}))

(defn send-event [plaintext elapsed-time corr-id]
  (try
    (let [c (riemann/tcp-client {:host (env :riemann-host)})]
      (riemann/send-event c
        {:service "crypto-square-be" :metric (/ elapsed-time 1000000) :state "ok" :description corr-id}))
    (catch java.lang.Exception ex
      (log/warn (str "Error connecting to Riemann using config:" (env :riemann-host))))
    (catch java.io.IOException ex 
      (log/warn (str "Cannot find Riemann at: " (env :riemann-host))))))