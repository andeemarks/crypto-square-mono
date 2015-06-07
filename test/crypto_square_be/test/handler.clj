(ns crypto-square-be.test.handler
  (:use midje.sweet
        ring.mock.request
        cheshire.core
        crypto-square-be.handler))

(defn- encrypt [plaintext]
  (app 
    (request :get 
      (str "/" plaintext))))

(facts "About GETs"
  (fact "graceful handling of no input"
    (let [response (encrypt "")
          body (parse-string (:body response))]
      (:status response) => 200
      (get body "ciphertext") => ""))

  (fact "single word encryption"
    (let [response (encrypt "abcd")
          body (parse-string (:body response))]
      (:status response) => 200
      (get body "ciphertext") => "acbd"))

  (fact "multi word encryption"
    (let [response (encrypt "ab+cd")
          body (parse-string (:body response))]
      (:status response) => 200
      (get body "ciphertext") => "acbd"))
  )
