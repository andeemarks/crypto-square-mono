(ns column-handler.models.core
  (:require [clojure.string :as clj-str]
            [column-handler.services.riemann :as riemann]
            [metrics.timers :as timer]
            [cheshire.core :as json]))

(timer/deftimer processing-time)

(defn plaintext-segments [normalized-text segment-size]
    (map clj-str/join (partition-all segment-size normalized-text)))
 
(defn- pad-segments [segments segments-size]
  (map #(format (str "%-" segments-size "s") %) segments))
 
(defn segments-in-columns [normalized-text segment-size]
  (let [segments (plaintext-segments normalized-text segment-size)]
    (apply map
           #(clj-str/trim (apply str %&))
           (pad-segments segments segment-size))))
 
(defn columnise [normalized-text segment-size corr-id]
  (let [timer (timer/start processing-time)
        result (segments-in-columns normalized-text (read-string segment-size))
        elapsed-time (timer/stop timer)]
      (riemann/send-event elapsed-time corr-id)
      result))
