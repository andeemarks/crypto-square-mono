(ns crypto-square.test.handler
  (:require [clojure.test :refer :all]
            [clj-webdriver.taxi :refer :all]
            [crypto-square.test.config :refer :all]))

(use-fixtures :once with-browser)

(defn- test-encryption-happy-path []
  (to test-base-url)

  (input-text "#plaintext" "Macromonitoring for Microservices")

  (submit "#encrypt")

  (is (= "moimesannircigcvrtfrioooocmrrse" (value "#ciphertext")) "Encryption incorrect!"))

(defmacro forever [& body] 
  `(while true ~@body))

(deftest encryption
  (test-encryption-happy-path))

(deftest ^:synth synthetic-trans-generator
  (forever 
    (test-encryption-happy-path)
    (Thread/sleep 5000)))
