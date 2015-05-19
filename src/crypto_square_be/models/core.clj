(ns crypto-square-be.models.core
  (:require [clojure.string :as clj-str]
            [clj-http.client :as client]
            [cheshire.core :as json]))
 
(defn- square-size-request [plaintext]
  (client/get 
    (str "http://localhost:3001/" plaintext)
    {:accept :json}))
 
(defn- no-punctuation [c]
  (or (Character/isLetter c)
      (Character/isDigit c)))
 
(defn- remove-punctuation [text]
  (clj-str/join "" (filter no-punctuation text)))
 
(defn normalize-plaintext [text]
  (clj-str/lower-case (remove-punctuation text)))
 
(defn square-size [text]
  (let [response (square-size-request text)
        json-body (json/parse-string (:body response))]
    (get json-body "size")))
 
(defn plaintext-segments [text]
  (let [normalized-text (normalize-plaintext text)
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
  (remove-spaces (normalize-ciphertext text)))
