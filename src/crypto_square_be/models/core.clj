(ns crypto-square-be.models.core
  (:require [clojure.string :as clj-str]
            [clj-http.client :as client]
            [clojure.tools.logging :as log]
            [crypto-square-be.services.normaliser :as normaliser]
            [riemann.client :as riemann]
            [metrics.timers :as timer]
            [ring.util.codec :refer [url-encode]]
            [cheshire.core :as json]))

(def ^:private correlation-id (atom nil))

(timer/deftimer processing-time)
 
(defn- square-size-request [plaintext]
  (client/get 
    (str "http://localhost:3001/" plaintext)
    {:accept :json
     :headers {"X-Correlation-Id" @correlation-id}}))
 
(defn square-size [text]
  (let [response (square-size-request text)
        json-body (json/parse-string (:body response))]
    (get json-body "size")))
 
(defn- column-handler-request [normalized-text segment-size]
  (client/get 
    (str "http://localhost:3003/" normalized-text "/" segment-size)
    {:accept :json
     :headers {"X-Correlation-Id" @correlation-id}}))
 
(defn- remove-spaces [text]
  (clj-str/replace text " " ""))
 
(defn normalize-ciphertext [normalized-text segment-size]
  (clj-str/join 
    " " 
    (let [response (column-handler-request normalized-text segment-size)
          json-body (json/parse-string (:body response))]
      (get json-body "column-text"))))

(defn- generate-ciphertext [normalized-text segment-size]
  (remove-spaces 
    (normalize-ciphertext normalized-text segment-size)))

(defn- send-event [plaintext elapsed-time]
  (try
    (let [c (riemann/tcp-client {:host "127.0.0.1"})]
      (riemann/send-event c
        {:service "crypto-square-be" :metric (/ elapsed-time 1000000) :state "ok" :description @correlation-id})
      (riemann/close-client c))
    (catch java.io.IOException ex 
      (log/warn "Cannot find Riemann!"))))
 
(defn ciphertext [text & corr-id]
  (reset! correlation-id (first corr-id))
  (let [timer (timer/start processing-time)
        normalized-text (normaliser/normalise-plaintext text @correlation-id)
        segment-size (square-size normalized-text)
        result   (if (empty? text)
                    ""
                    (generate-ciphertext normalized-text segment-size))
        elapsed-time (timer/stop timer)]
      (send-event text elapsed-time)
      result))
