(ns crypto-square-be.test.models.core
  (:use midje.sweet
        crypto-square-be.services.riemann
        crypto-square-be.services.normaliser
        crypto-square-be.services.square-sizer
        crypto-square-be.services.column-handler
        crypto-square-be.models.core))

(against-background [(send-event anything anything anything) => ..riemann..]

(facts "About Model core"
  (fact "ciphertext generation involves helper services"
    (ciphertext "abcd") => "acbd"
    (provided (normalise-plaintext "abcd" anything) => "abcd")
    (provided (square-size "abcd" anything) => "2")
    (provided (split-into-columns "abcd" "2" anything) => ["ac" "bd"]))

  (fact "nil plaintext shortcircuits service chain"
    (ciphertext "") => "")
))