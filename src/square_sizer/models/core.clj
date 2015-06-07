(ns square-sizer.models.core
  (:require 
    [square-sizer.services.riemann :as riemann]
    [metrics.timers :as timer]))

(timer/deftimer processing-time)

(defn- calculate-square-size [text]
  (int (Math/ceil (Math/sqrt (count text)))))

(defn square-size [text corr-id]
  (let [timer (timer/start processing-time)
        result (calculate-square-size text)
        elapsed-time (timer/stop timer)]
      (riemann/send-event corr-id elapsed-time)
      result))
