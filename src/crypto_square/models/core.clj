(ns crypto-square.models.core
	(:require 
		[clj-http.client :as client]
		[riemann.client :as riemann]
    [clojure.tools.logging :as log]
    [metrics.timers :as timer]
		[cheshire.core :as json]))

(timer/deftimer processing-time)

(defn- corr-id [] (str "crypto-square-" (java.util.UUID/randomUUID)))

(defn- ciphertext-request [plaintext corr-id]
	(client/post 
		"http://localhost:3000"
		{:body (json/generate-string {:plaintext plaintext})
		 :content-type :json
		 :headers {"X-Correlation-Id" corr-id}
		 :accept :json}))

(defn- send-event [plaintext corr-id elapsed-time]
	(try
		(let [c (riemann/tcp-client {:host "127.0.0.1"})]
	        (riemann/send-event c
	                {:service "crypto-square" :metric (/ elapsed-time 1000) :state "ok" :description corr-id})
	        (riemann/close-client c))
		(catch java.io.IOException ex 
			(log/warn "Cannot find Riemann!"))))

(defn ciphertext [plaintext]
	(let [timer (timer/start processing-time)
				correlation-id (corr-id) 
		 		response (ciphertext-request plaintext correlation-id)
				json-body (json/parse-string (:body response))
				ciphertext (get json-body "ciphertext")
				elapsed-time (timer/stop timer)]
		(send-event ciphertext correlation-id elapsed-time)
		ciphertext))
