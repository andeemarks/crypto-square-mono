(ns crypto-square.models.core
	(:require 
		[clj-http.client :as client]
		[riemann.client :as riemann]
    [clojure.tools.logging :as log]
		[cheshire.core :as json]))

(defn- corr-id [] (str "crypto-square-" (java.util.UUID/randomUUID)))

(defn- ciphertext-request [plaintext corr-id]
	(client/post 
		"http://localhost:3000"
		{:body (json/generate-string {:plaintext plaintext})
		 :content-type :json
		 :headers {"X-Correlation-Id" corr-id}
		 :accept :json}))

(defn- send-event [plaintext corr-id]
	(try
		(let [c (riemann/tcp-client {:host "127.0.0.1"})]
	        (riemann/send-event c
	                {:service "crypto-square" :description corr-id})
	        (riemann/close-client c))
		(catch java.io.IOException ex 
			(log/warn "Cannot find Riemann!"))))

(defn ciphertext [plaintext]
	(let [correlation-id (corr-id) 
		 		response (ciphertext-request plaintext correlation-id)
				json-body (json/parse-string (:body response))
				ciphertext (get json-body "ciphertext")]
		(send-event ciphertext correlation-id)
		ciphertext))
