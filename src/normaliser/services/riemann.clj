(ns normaliser.services.riemann
	(:require 
    	[riemann.client :as riemann]
      [environ.core :refer [env]]
    	[clojure.tools.logging :as log]
      [metrics.health.core :as health]
    	[metrics.timers :as timer]))

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
		"warning"
		"ok"))

(defn send-event [corr-id elapsed-time]
  (try
    (let [c (riemann/tcp-client {:host (env :riemann-host)})]
      (riemann/send-event c
      	{:service "normaliser" :metric (/ elapsed-time 1000000) :state (state) :description corr-id}))
    (catch java.io.IOException ex 
      (log/warn "Cannot find Riemann!"))))