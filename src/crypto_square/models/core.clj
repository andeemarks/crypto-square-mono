(ns crypto-square.models.core
	(:require 
		[clj-http.client :as client]
		[riemann.client :as riemann]
		[cheshire.core :as json]))
 
(defn- ciphertext-request [plaintext]
	(client/post 
		"http://localhost:3000"
		{:body (json/generate-string {:plaintext plaintext})
		 :content-type :json
		 :accept :json}))

(defn- send-event [plaintext]
	(try
		(let [c (riemann/tcp-client {:host "127.0.0.1"})]
	        (riemann/send-event c
	                {:service "crypto-square" :description plaintext})
	        (riemann/close-client c))
		(catch java.io.IOException ex 
			(prn "Cannot find Riemann!"))))

(defn ciphertext [plaintext]
	(let [response (ciphertext-request plaintext)
				json-body (json/parse-string (:body response))
				ciphertext (get json-body "ciphertext")]
		(send-event ciphertext)
		ciphertext))
