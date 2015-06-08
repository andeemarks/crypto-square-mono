(ns crypto-square-be.models.core
  (:require [clojure.string :as clj-str]
            [crypto-square-be.services.normaliser :as normaliser]
            [crypto-square-be.services.square-sizer :as square-sizer]
            [crypto-square-be.services.column-handler :as column-handler]
            [crypto-square-be.services.riemann :as riemann]
            [metrics.timers :as timer]))

(def ^:private correlation-id (atom nil))

(timer/deftimer processing-time)
 
(defn- remove-spaces [text]
  (clj-str/replace text " " ""))
 
(defn normalize-ciphertext [normalized-text segment-size]
  (clj-str/join 
    " " 
    (column-handler/split-into-columns normalized-text segment-size @correlation-id)))

(defn- generate-ciphertext [text]
  (let [normalized-text (normaliser/normalise-plaintext text @correlation-id)
        segment-size (square-sizer/square-size normalized-text @correlation-id)]
    (remove-spaces 
      (normalize-ciphertext normalized-text segment-size))))
 
(defn ciphertext [text & corr-id]
  (reset! correlation-id (first corr-id))
  (let [timer (timer/start processing-time)
        result   (if (empty? text)
                    ""
                    (generate-ciphertext text))
        elapsed-time (timer/stop timer)]
      (riemann/send-event text elapsed-time @correlation-id)
      result))
