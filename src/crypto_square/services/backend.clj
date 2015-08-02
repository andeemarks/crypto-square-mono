(ns crypto-square.services.backend
	(:require 
		[clj-http.client :as client]
    [environ.core :refer [env]]
		[cheshire.core :as json]))

(defn- ciphertext-request [plaintext corr-id]
	(client/post 
		(env :backend-url)
		{:body (json/generate-string {:plaintext plaintext})
		 :content-type :json
		 :headers {"X-Correlation-Id" corr-id}
		 :accept :json}))

(defn available? []
  (try ((client/get (env :backend-url) {:throw-exceptions false}):status)
  (catch Exception e -1)))

(defn encrypt [plaintext corr-id]
	(get 
		(json/parse-string 
			(:body 
				(ciphertext-request plaintext corr-id))) 
		"ciphertext"))
