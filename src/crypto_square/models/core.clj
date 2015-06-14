(ns crypto-square.models.core
	(:require 
    [metrics.timers :as timer]
    [crypto-square.services.riemann :as riemann]
    [crypto-square.services.backend :as be]
		[cheshire.core :as json]))

(timer/deftimer processing-time)

(defn- corr-id [] (str "crypto-square-" (java.util.UUID/randomUUID)))

(defn ciphertext [plaintext]
	(let [timer (timer/start processing-time)
				correlation-id (corr-id) 
				ciphertext (be/encrypt plaintext correlation-id)
				elapsed-time (timer/stop timer)]
		(riemann/send-event ciphertext correlation-id elapsed-time)
		ciphertext))
