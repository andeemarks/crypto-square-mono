(ns crypto-square-be.models.core
  (:require [clojure.string :as clj-str]
            [crypto-square-be.services.normaliser :as normaliser]
            [crypto-square-be.services.square-sizer :as square-sizer]
            [crypto-square-be.services.column-handler :as column-handler]
            [metrics.timers :as timer]))

(timer/deftimer processing-time)
 
(defn- remove-spaces [text]
  (clj-str/replace text " " ""))
 
(defn normalize-ciphertext [normalized-text segment-size]
  (clj-str/join 
    " " 
    (column-handler/split-into-columns normalized-text segment-size)))

(defn- generate-ciphertext [text]
  (let [normalized-text (normaliser/normalise-plaintext text)
        segment-size (square-sizer/square-size normalized-text)]
    (remove-spaces 
      (normalize-ciphertext normalized-text segment-size))))
 
(defn ciphertext [text]
  (let [timer (timer/start processing-time)
        result   (if (empty? text)
                    ""
                    (generate-ciphertext text))
        elapsed-time (timer/stop timer)]
      result))
