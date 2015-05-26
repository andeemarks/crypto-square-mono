(ns normaliser.models.core
	(:require 
    	[riemann.client :as riemann]
    	[clojure.tools.logging :as log]
    	[metrics.timers :as timer]
		[clojure.string :as clj-str]))

(timer/deftimer processing-time)

(defn- state [] 
	(if (= 1 (rand-int 10))
		"warning"
		"ok"))

(defn- send-event [corr-id elapsed-time]
  (try
    (let [c (riemann/tcp-client {:host "127.0.0.1"})]
      (riemann/send-event c
      	{:service "normaliser" :metric (/ elapsed-time 1000000) :state (state) :description corr-id})
      (riemann/close-client c))
    (catch java.io.IOException ex 
      (log/warn "Cannot find Riemann!"))))

(defn- no-punctuation [c]
  (or (Character/isLetter c)
      (Character/isDigit c)))
 
(defn- remove-punctuation [text]
  (clj-str/join "" (filter no-punctuation text)))

(defn- normalise-text [text]
	(clj-str/lower-case (remove-punctuation text)))

(defn normalise [text corr-id]
  (let [timer (timer/start processing-time)
        result (normalise-text text)
        elapsed-time (timer/stop timer)]
      (send-event corr-id elapsed-time)
      result))