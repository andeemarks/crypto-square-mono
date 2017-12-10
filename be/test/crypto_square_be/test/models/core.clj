(ns crypto-square-be.test.models.core
  (:use midje.sweet)
  (:require [crypto-square-be.services.riemann :refer [send-event]]
        [crypto-square-be.services.normaliser :refer [normalise-plaintext]]
        [crypto-square-be.services.square-sizer :refer [square-size]]
        [crypto-square-be.services.column-handler :refer [split-into-columns]]
        [crypto-square-be.models.core :as model]))

(against-background [(send-event anything anything anything) => ..riemann..]

(facts "About Model core"
  (fact "ciphertext generation involves helper services"
    (model/ciphertext "abcd") => "acbd"
    (provided (normalise-plaintext "abcd" anything) => "abcd")
    (provided (square-size "abcd" anything) => "2")
    (provided (split-into-columns "abcd" "2" anything) => ["ac" "bd"]))

  (fact "nil plaintext shortcircuits service chain"
    (model/ciphertext "") => "")
))