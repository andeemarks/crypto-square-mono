(ns crypto-square-be.services.square-sizer
  (:require [clj-http.client :as client]
	  	      [environ.core :refer [env]]
            [metrics.health.core :as health]
            [cheshire.core :as json]))

(defn- healthcheck-page []
  (str (env :square-sizer-url) "/health"))

(defn available? []
  (try ((client/get (healthcheck-page) {:throw-exceptions false}):status)
  (catch Exception e -1)))

(health/defhealthcheck "square-sizer-available?" (fn [] (if (== (available?) 200)
                                            (health/healthy "square-sizer is available!")
                                            (health/unhealthy "square-sizer is unavailable!"))))

(defn healthcheck []
  (let [health (health/check square-sizer-available?)]
    {:healthy? (.isHealthy health) :message (.getMessage health)}))

(defn- square-size-request [plaintext corr-id]
  (client/get 
    (str (env :square-sizer-url) "/" plaintext)
    {:accept :json
     :headers {"X-Correlation-Id" corr-id}}))
 
(defn square-size [text corr-id]
  (let [response (square-size-request text corr-id)
        json-body (json/parse-string (:body response))]
    (get json-body "size")))
