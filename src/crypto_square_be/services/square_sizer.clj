(ns crypto-square-be.services.square-sizer
  (:require [clj-http.client :as client]
	  	    [environ.core :refer [env]]
            [cheshire.core :as json]))
 
(defn- square-size-request [plaintext corr-id]
  (client/get 
    (str (env :square-sizer-url) "/" plaintext)
    {:accept :json
     :headers {"X-Correlation-Id" corr-id}}))
 
(defn square-size [text corr-id]
  (let [response (square-size-request text corr-id)
        json-body (json/parse-string (:body response))]
    (get json-body "size")))
