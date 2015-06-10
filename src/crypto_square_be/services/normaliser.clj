(ns crypto-square-be.services.normaliser
  (:require [clj-http.client :as client]
            [metrics.timers :as timer]
            [environ.core :refer [env]]
            [ring.util.codec :refer [url-encode]]
            [cheshire.core :as json]))

(def ^:private correlation-id (atom nil))

(defn- normalise-request [plaintext corr-id]
  (client/get 
    (str (env :normaliser-url) "/" (url-encode plaintext))
    {:accept :json
     :headers {"X-Correlation-Id" corr-id}}))
 
(defn normalise-plaintext [text corr-id]
  (let [response (normalise-request text corr-id)
        json-body (json/parse-string (:body response))]
    (get json-body "normalised-text")))
