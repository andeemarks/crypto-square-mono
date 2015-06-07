(ns normaliser.models.core
	(:require 
  	[normaliser.services.riemann :as riemann]
  	[metrics.timers :as timer]
		[clojure.string :as clj-str]))

(timer/deftimer processing-time)

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
      (riemann/send-event corr-id elapsed-time)
      result))