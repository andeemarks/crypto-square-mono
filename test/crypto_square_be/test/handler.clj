(ns crypto-square-be.test.handler
  (:use midje.sweet
        ring.mock.request
        crypto-square-be.models.core
        cheshire.core
        crypto-square-be.handler))

(defn- encrypt [plaintext]
  (app 
    (request :get 
      (str "/" plaintext))))

(fact "Happy GETs return 200"
  (:status (encrypt "abcd")) => 200
  (provided (ciphertext "abcd" anything) => "acdb"))