(ns normaliser.services.riemann
	(:require 
    	[riemann.client :as riemann]
    	[clojure.tools.logging :as log]
    	[metrics.timers :as timer]))

(defn- state [] 
	(if (= 1 (rand-int 10))
		"warning"
		"ok"))

(defn send-event [corr-id elapsed-time]
  (try
    (let [c (riemann/tcp-client {:host "127.0.0.1"})]
      (riemann/send-event c
      	{:service "normaliser" :metric (/ elapsed-time 1000000) :state (state) :description corr-id})
      (riemann/close-client c))
    (catch java.io.IOException ex 
      (log/warn "Cannot find Riemann!"))))