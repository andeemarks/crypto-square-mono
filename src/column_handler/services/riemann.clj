(ns column-handler.services.riemann
  (:require [clojure.tools.logging :as log]
    	    	[environ.core :refer [env]]
            [metrics.health.core :as health]
            [riemann.client :as riemann]))

(defn connection []
  (riemann/tcp-client {:host (env :riemann-host)}))

(defn available? []
  (try
    (let [c (connection)]
      (riemann/connected? c))
    (catch Exception ex 
      false)))

(health/defhealthcheck "riemann-available?" (fn [] (if (not (available?))
                                            (health/unhealthy "riemann is unavailable!")
                                            (health/healthy "riemann is available!"))))

(defn healthcheck []
  (try
    (let [health (health/check riemann-available?)]
      {:healthy? (.isHealthy health) :message (.getMessage health)})))

(defn send-event [elapsed-time corr-id]
  (try
    (let [c (connection)]
      (riemann/send-event c
        {:service "column-handler" :metric (/ elapsed-time 1000000) :state "ok" :description corr-id}))
    (catch java.lang.IllegalArgumentException ex 
      (log/warn "Cannot find Riemann!"))
    (catch java.io.IOException ex 
      (log/warn "Cannot find Riemann!"))))
