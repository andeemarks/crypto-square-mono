(ns crypto-square.models.core
	(:require 
		[clj-http.client :as client]
    [metrics.timers :as timer]
    [crypto-square.services.riemann :as riemann]
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

(defn ciphertext [plaintext]
	(let [timer (timer/start processing-time)
				correlation-id (corr-id) 
		 		response (ciphertext-request plaintext correlation-id)
				json-body (json/parse-string (:body response))
				ciphertext (get json-body "ciphertext")
				elapsed-time (timer/stop timer)]
		(riemann/send-event ciphertext correlation-id elapsed-time)
		ciphertext))
