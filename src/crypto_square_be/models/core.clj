(ns crypto-square-be.models.core
  (:require [clojure.string :as clj-str]
            [clj-http.client :as client]
            [cheshire.core :as json]))
 
(defn- normalise-request [plaintext]
  (client/get 
    (str "http://localhost:3002/" plaintext)
    {:accept :json}))
 
(defn normalise-plaintext [text]
  (let [response (normalise-request text)
        json-body (json/parse-string (:body response))]
    (get json-body "normalised-text")))
 
(defn- square-size-request [plaintext]
  (client/get 
    (str "http://localhost:3001/" plaintext)
    {:accept :json}))
 
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
 
(defn ciphertext [text]
  (if (empty? text)
    ""
    (remove-spaces (normalize-ciphertext text))))
