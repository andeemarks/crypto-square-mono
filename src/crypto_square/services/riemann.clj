(ns crypto-square.services.riemann
	(:require 
		[riemann.client :as riemann]
    	[clojure.tools.logging :as log]))

(defn send-event [plaintext corr-id elapsed-time]
	(try
		(let [c (riemann/tcp-client {:host "127.0.0.1"})]
	        (riemann/send-event c
	                {:service "crypto-square" :metric (/ elapsed-time 1000000) :state "ok" :description corr-id})
	        (riemann/close-client c))
		(catch java.io.IOException ex 
			(log/warn "Cannot find Riemann!"))))
