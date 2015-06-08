(ns column-handler.models.core
  (:require [clojure.string :as clj-str]
            [clojure.tools.logging :as log]
            [riemann.client :as riemann]
            [metrics.timers :as timer]
            [cheshire.core :as json]))

(timer/deftimer processing-time)

(defn plaintext-segments [normalized-text segment-size]
    (map clj-str/join (partition-all segment-size normalized-text)))
 
(defn- pad-segments [segments segments-size]
  (map #(format (str "%-" segments-size "s") %) segments))

(defn send-event [elapsed-time corr-id]
  (try
    (let [c (riemann/tcp-client {:host "127.0.0.1"})]
      (riemann/send-event c
        {:service "column-handler" :metric (/ elapsed-time 1000000) :state "ok" :description corr-id})
      (riemann/close-client c))
    (catch java.io.IOException ex 
      (log/warn "Cannot find Riemann!"))))
 
(defn segments-in-columns [normalized-text segment-size]
  (let [segments (plaintext-segments normalized-text segment-size)]
    (apply map
           #(clj-str/trim (apply str %&))
           (pad-segments segments segment-size))))
 
(defn columnise [normalized-text segment-size corr-id]
  (let [timer (timer/start processing-time)
        result (segments-in-columns normalized-text (read-string segment-size))
        elapsed-time (timer/stop timer)]
      (send-event elapsed-time corr-id)
      result))
