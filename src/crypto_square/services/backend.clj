(ns crypto-square.services.backend
	(:require 
		[clj-http.client :as client]
		[cheshire.core :as json]))

(defn- ciphertext-request [plaintext corr-id]
	(client/post 
		"http://localhost:3000"
		{:body (json/generate-string {:plaintext plaintext})
		 :content-type :json
		 :headers {"X-Correlation-Id" corr-id}
		 :accept :json}))

(defn encrypt [plaintext corr-id]
	(get 
		(json/parse-string 
			(:body 
				(ciphertext-request plaintext corr-id))) 
		"ciphertext"))
