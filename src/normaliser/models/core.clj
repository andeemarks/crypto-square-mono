(ns normaliser.models.core
	(:require 
    	[riemann.client :as riemann]
		[clojure.string :as clj-str]))

(defn- send-event [text]
  (try
    (let [c (riemann/tcp-client {:host "127.0.0.1"})]
          (riemann/send-event c
                  {:service "normaliser" :description text})
          (riemann/close-client c))
    (catch java.io.IOException ex 
      (prn "Cannot find Riemann!"))))

(defn- no-punctuation [c]
  (or (Character/isLetter c)
      (Character/isDigit c)))
 
(defn- remove-punctuation [text]
  (clj-str/join "" (filter no-punctuation text)))
 
(defn normalise [text]
  (send-event text)
  (clj-str/lower-case (remove-punctuation text)))
