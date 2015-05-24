(ns crypto-square-be.models.core
  (:require [clojure.string :as clj-str]
            [clj-http.client :as client]
            [clojure.tools.logging :as log]
            [riemann.client :as riemann]
            [cheshire.core :as json]))

; (def ^:dynamic correlation-id)
(def ^:private correlation-id (atom nil))

(defn- normalise-request [plaintext]
  (client/get 
    (str "http://localhost:3002/" plaintext)
    {:accept :json
     :headers {"X-Correlation-Id" @correlation-id}}))
 
(defn normalise-plaintext [text]
  (let [response (normalise-request text)
        json-body (json/parse-string (:body response))]
    (get json-body "normalised-text")))
 
(defn- square-size-request [plaintext]
  (client/get 
    (str "http://localhost:3001/" plaintext)
    {:accept :json
     :headers {"X-Correlation-Id" @correlation-id}}))
 
(defn square-size [text]
  (let [response (square-size-request text)
        json-body (json/parse-string (:body response))]
    (get json-body "size")))
 
(defn plaintext-segments [text]
  (let [normalized-text (normalise-plaintext text)
        segment-size (square-size normalized-text)]
    (map clj-str/join (partition-all segment-size normalized-text))))
 
(defn- pad-segments [segments segments-size]
  (map #(format (str "%-" segments-size "s") %) segments))
 
(defn- segments-in-columns [text]
  (let [segments (plaintext-segments text)
        segment-size (count (first segments))]
    (apply map
           #(clj-str/trim (apply str %&))
           (pad-segments segments segment-size))))
 
(defn- remove-spaces [text]
  (clj-str/replace text " " ""))
 
(defn normalize-ciphertext [text]
  (->> text
       segments-in-columns
       (clj-str/join " ")))

(defn- send-event [plaintext]
  (try
    (let [c (riemann/tcp-client {:host "127.0.0.1"})]
          (riemann/send-event c
                  {:service "crypto-square-be" :description plaintext})
          (riemann/close-client c))
    (catch java.io.IOException ex 
      (log/warn "Cannot find Riemann!"))))
 
(defn ciphertext [text & corr-id]
  (reset! correlation-id corr-id)
  (send-event text)
  (if (empty? text)
    ""
    (remove-spaces (normalize-ciphertext text))))
