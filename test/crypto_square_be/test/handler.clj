(ns crypto-square-be.test.handler
  (:require [prometheus.core :as prometheus])
  (:use midje.sweet
        ring.mock.request
        crypto-square-be.models.core
        cheshire.core
        crypto-square-be.handler))

(defn- encrypt [plaintext]
  (app 
    (request :get 
      (str "/" plaintext))))

(background (before :facts (prometheus/init! "crypto_square-be")))

(fact "Happy GETs return 200"
  (:status (encrypt "abcd")) => 200
  (provided 
    (ciphertext "abcd" anything) => "acdb"))
