(ns crypto-square.models.core
	(:require 
		[clj-http.client :as client]
		[cheshire.core :as json]))
 
(defn ciphertext [plaintext]
	(let [response (client/post "http://localhost:3000"
					{:body (json/generate-string {:plaintext plaintext})
	   			 :content-type :json
  	 			 :accept :json})
				body (:body response)
				json-body (json/parse-string body)]
		(get json-body "ciphertext")))
