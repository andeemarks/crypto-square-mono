(ns crypto-square-be.services.column-handler
  (:require [clj-http.client :as client]
            [cheshire.core :as json]))

(defn- column-handler-request [normalized-text segment-size corr-id]
  (client/get 
    (str "http://localhost:3003/" normalized-text "/" segment-size)
    {:accept :json
     :headers {"X-Correlation-Id" corr-id}}))
 
(defn split-into-columns [normalized-text segment-size corr-id]
  (let [response (column-handler-request normalized-text segment-size corr-id)
        json-body (json/parse-string (:body response))]
    (get json-body "column-text")))

