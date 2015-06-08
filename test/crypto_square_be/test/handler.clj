(ns crypto-square-be.test.handler
  (:use midje.sweet
        ring.mock.request
        crypto-square-be.services.riemann
        crypto-square-be.services.normaliser
        cheshire.core
        crypto-square-be.handler))

(defn- encrypt [plaintext]
  (app 
    (request :get 
      (str "/" plaintext))))

(defn- ciphertext-for [plaintext]
  (get 
    (parse-string 
      (:body 
        (encrypt plaintext))) 
    "ciphertext"))

(against-background [(send-event anything anything anything) => ..riemann..]

(facts "About GETs"
  ; (fact "returns 200"
  ;   (:status (encrypt "")) => 200)

  ; (fact "graceful handling of no input"
  ;   (ciphertext-for "") => "")

  (fact "single word encryption"
    (ciphertext-for "abcd") => "acbd"
    (provided (normalise-plaintext "abcd" anything) => "abcd"))

  ; (fact "multi word encryption"
  ;   (ciphertext-for "ab+cd") => "acbd")
  
))