(ns crypto-square-be.services.normaliser
  (:require [clj-http.client :as client]
            [metrics.timers :as timer]
            [metrics.health.core :as health]
            [environ.core :refer [env]]
            [clojure.tools.logging :as log]
            [ring.util.codec :refer [url-encode]]
            [cheshire.core :as json]))

(def ^:private correlation-id (atom nil))

(defn- healthcheck-page []
  (str (env :normaliser-url) "/health"))

(defn available? []
  (try ((client/get (healthcheck-page) {:throw-exceptions false}):status)
  (catch Exception e -1)))

(health/defhealthcheck "normaliser-available?" (fn [] (if (== (available?) 200)
                                            (health/healthy "normaliser is available!")
                                            (health/unhealthy "normaliser is unavailable!"))))

(defn healthcheck []
  (let [health (health/check normaliser-available?)]
    {:healthy? (.isHealthy health) :message (.getMessage health)}))

(defn- normalise-request [plaintext]
  (log/info (str "Attempting to normalise request via: " (env :normaliser-url)))
  (client/get 
    (str (env :normaliser-url) "/" (url-encode plaintext))
    {:accept :json}))
 
(defn normalise-plaintext [text]
  (let [response (normalise-request text)
        json-body (json/parse-string (:body response))]
    (get json-body "normalised-text")))
