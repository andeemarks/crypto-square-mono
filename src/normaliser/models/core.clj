(ns normaliser.models.core
	(:require 
    	[riemann.client :as riemann]
    	[clojure.tools.logging :as log]
		[clojure.string :as clj-str]))

(defn- send-event [text corr-id]
  (try
    (let [c (riemann/tcp-client {:host "127.0.0.1"})]
      (riemann/send-event c
      	{:service "normaliser" :description corr-id})
      (riemann/close-client c))
    (catch java.io.IOException ex 
      (log/warn "Cannot find Riemann!"))))

(defn- no-punctuation [c]
  (or (Character/isLetter c)
      (Character/isDigit c)))
 
(defn- remove-punctuation [text]
  (clj-str/join "" (filter no-punctuation text)))
 
(defn normalise [text corr-id]
  (send-event text corr-id)
  (clj-str/lower-case (remove-punctuation text)))
