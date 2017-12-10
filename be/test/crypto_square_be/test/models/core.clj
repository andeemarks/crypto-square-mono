(ns crypto-square-be.test.models.core
  (:use midje.sweet)
  (:require 
        [crypto-square-be.services.normaliser :refer [normalise-plaintext]]
        [crypto-square-be.services.square-sizer :refer [square-size]]
        [crypto-square-be.services.column-handler :refer [split-into-columns]]
        [crypto-square-be.models.core :as model]))

(facts "About Model core"
  (fact "ciphertext generation involves helper services"
    (model/ciphertext "abcd") => "acbd"
    (provided (normalise-plaintext "abcd") => "abcd")
    (provided (square-size "abcd") => "2")
    (provided (split-into-columns "abcd" "2") => ["ac" "bd"]))

  (fact "nil plaintext shortcircuits service chain"
    (model/ciphertext "") => "")
)