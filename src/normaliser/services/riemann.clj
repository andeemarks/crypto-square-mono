(ns normaliser.services.riemann
	(:require 
    	[riemann.client :as riemann]
      [environ.core :refer [env]]
    	[clojure.tools.logging :as log]
    	[metrics.timers :as timer]))

(defn- state [] 
	(if (= 1 (rand-int 10))
		"warning"
		"ok"))

(defn send-event [corr-id elapsed-time]
  (try
    (let [c (riemann/tcp-client {:host (env :riemann-url)})]
      (riemann/send-event c
      	{:service "normaliser" :metric (/ elapsed-time 1000000) :state (state) :description corr-id})
      (riemann/close-client c))
    (catch java.io.IOException ex 
      (log/warn "Cannot find Riemann!"))))