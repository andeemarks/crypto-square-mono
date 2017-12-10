(ns square-sizer.models.core
  (:require 
    [metrics.timers :as timer]))

(timer/deftimer processing-time)

(defn- calculate-square-size [text]
  (int (Math/ceil (Math/sqrt (count text)))))

(defn square-size [text corr-id]
  (let [timer (timer/start processing-time)
        result (calculate-square-size text)
        elapsed-time (timer/stop timer)]
      result))
