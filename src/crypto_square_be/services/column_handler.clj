(ns crypto-square-be.services.column-handler
  (:require [clj-http.client :as client]
		  	    [environ.core :refer [env]]
            [metrics.health.core :as health]
            [cheshire.core :as json]))


(defn available? []
  (try ((client/get (env :column-handler-url) {:throw-exceptions false}):status)
  (catch Exception e -1)))

(health/defhealthcheck "column-handler-available?" (fn [] (if (== (available?) 200)
                                            (health/healthy "column-handler is available!")
                                            (health/unhealthy "column-handler is unavailable!"))))

(defn healthcheck []
  (let [health (health/check column-handler-available?)]
    {:healthy? (.isHealthy health) :message (.getMessage health)}))

(defn- column-handler-request [normalised-text segment-size corr-id]
  (client/get 
    (str (env :column-handler-url) "/" normalised-text "/" segment-size)
    {:accept :json
     :headers {"X-Correlation-Id" corr-id}}))
 
(defn split-into-columns [normalised-text segment-size corr-id]
  (let [response (column-handler-request normalised-text segment-size corr-id)
        json-body (json/parse-string (:body response))]
    (get json-body "column-text")))

