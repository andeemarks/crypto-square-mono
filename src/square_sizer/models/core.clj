(ns square-sizer.models.core
  (:require 
    [riemann.client :as riemann]))

(defn- send-event [text]
  (try
    (let [c (riemann/tcp-client {:host "127.0.0.1"})]
          (riemann/send-event c
                  {:service "square-sizer" :description text})
          (riemann/close-client c))
    (catch java.io.IOException ex 
      (prn "Cannot find Riemann!"))))
 
(defn square-size [text]
  (send-event text)
  (int (Math/ceil (Math/sqrt (count text)))))
