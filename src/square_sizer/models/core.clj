(ns square-sizer.models.core
  (:require 
    [clojure.tools.logging :as log]
    [riemann.client :as riemann]))

(defn- send-event [text corr-id]
  (try
    (let [c (riemann/tcp-client {:host "127.0.0.1"})]
          (riemann/send-event c
                  {:service "square-sizer" :description corr-id})
          (riemann/close-client c))
    (catch java.io.IOException ex 
      (log/warn "Cannot find Riemann!"))))
 
(defn square-size [text corr-id]
  (send-event text corr-id)
  (int (Math/ceil (Math/sqrt (count text)))))
