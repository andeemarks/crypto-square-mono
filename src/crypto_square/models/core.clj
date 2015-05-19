(ns crypto-square.models.core
	(:require 
		[clj-http.client :as client]
		[cheshire.core :as json]))
 
(defn- ciphertext-request [plaintext]
	(client/post 
		"http://localhost:3000"
		{:body (json/generate-string {:plaintext plaintext})
		 :content-type :json
		 :accept :json}))

(defn ciphertext [plaintext]
	(let [response (ciphertext-request plaintext)
				json-body (json/parse-string (:body response))]
		(get json-body "ciphertext")))
