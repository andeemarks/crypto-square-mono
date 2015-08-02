(ns crypto-square-be.services.normaliser
  (:require [clj-http.client :as client]
            [metrics.timers :as timer]
            [metrics.health.core :as health]
            [environ.core :refer [env]]
            [ring.util.codec :refer [url-encode]]
            [cheshire.core :as json]))

(def ^:private correlation-id (atom nil))

(defn available? []
  (try ((client/get (env :normaliser-url) {:throw-exceptions false}):status)
  (catch Exception e -1)))

(health/defhealthcheck "normaliser-available?" (fn [] (if (== (available?) 200)
                                            (health/healthy "normaliser is available!")
                                            (health/unhealthy "normaliser is unavailable!"))))

(defn healthcheck []
  (let [health (health/check normaliser-available?)]
    {:healthy? (.isHealthy health) :message (.getMessage health)}))

(defn- normalise-request [plaintext corr-id]
  (client/get 
    (str (env :normaliser-url) "/" (url-encode plaintext))
    {:accept :json
     :headers {"X-Correlation-Id" corr-id}}))
 
(defn normalise-plaintext [text corr-id]
  (let [response (normalise-request text corr-id)
        json-body (json/parse-string (:body response))]
    (get json-body "normalised-text")))
