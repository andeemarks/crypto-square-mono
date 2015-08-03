(ns square-sizer.services.riemann
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

(defn- state [] 
  (if (= 1 (rand-int 10))
    "failure"
    "ok"))

(defn- metric [elapsed-time]
  (/ elapsed-time 1000000))

(defn send-event [corr-id elapsed-time]
  (try
    (let [c (riemann/tcp-client {:host (env :riemann-host)})]
          (riemann/send-event c
                  {:service "square-sizer" 
                   :metric (metric elapsed-time) 
                   :state (state) 
                   :description corr-id}))
    (catch java.io.IOException ex 
      (log/warn "Cannot find Riemann!"))))
