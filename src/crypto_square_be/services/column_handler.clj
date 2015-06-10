(ns crypto-square-be.services.column-handler
  (:require [clj-http.client :as client]
		  	    [environ.core :refer [env]]
            [cheshire.core :as json]))

(defn- column-handler-request [normalised-text segment-size corr-id]
  (client/get 
    (str (env :column-handler-url) "/" normalised-text "/" segment-size)
    {:accept :json
     :headers {"X-Correlation-Id" corr-id}}))
 
(defn split-into-columns [normalised-text segment-size corr-id]
  (let [response (column-handler-request normalised-text segment-size corr-id)
        json-body (json/parse-string (:body response))]
    (get json-body "column-text")))

