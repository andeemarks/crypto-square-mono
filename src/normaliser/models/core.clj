(ns normaliser.models.core
 (:require [clojure.string :as clj-str]))

(defn- no-punctuation [c]
  (or (Character/isLetter c)
      (Character/isDigit c)))
 
(defn- remove-punctuation [text]
  (clj-str/join "" (filter no-punctuation text)))
 
(defn normalise [text]
  (clj-str/lower-case (remove-punctuation text)))
