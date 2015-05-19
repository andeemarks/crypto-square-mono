(ns crypto-square.models.core
	(:require 
		[clj-http.client :as client]
		[cheshire.core :as json]))
 
(defn ciphertext [plaintext]
	(:body (client/post "http://localhost:3000"
		{:body (json/generate-string {:plaintext plaintext})
	   :content-type :json
  	 :accept :json})))