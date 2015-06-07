(ns crypto-square-be.services.square-sizer
  (:require [clj-http.client :as client]
            [cheshire.core :as json]))
 
(defn- square-size-request [plaintext corr-id]
  (client/get 
    (str "http://localhost:3001/" plaintext)
    {:accept :json
     :headers {"X-Correlation-Id" corr-id}}))
 
(defn square-size [text corr-id]
  (let [response (square-size-request text corr-id)
        json-body (json/parse-string (:body response))]
    (get json-body "size")))
