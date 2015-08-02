(ns crypto-square.services.riemann
	(:require 
		[riemann.client :as riemann]
    [environ.core :refer [env]]
  	[clojure.tools.logging :as log]))

(defn available? []
	(try
		(let [c (riemann/tcp-client {:host (env :riemann-host)})]
			(riemann/connected? c))
		(catch java.io.IOException ex 
			false)))

(defn send-event [plaintext corr-id elapsed-time]
	(try
		(let [c (riemann/tcp-client {:host (env :riemann-host)})]
	        (riemann/send-event c
	                {:service "crypto-square" :metric (/ elapsed-time 1000000) :state "ok" :description corr-id}))
		(catch java.io.IOException ex 
			(log/warn "Cannot find Riemann!"))))
