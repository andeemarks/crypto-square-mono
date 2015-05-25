(ns crypto-square.test.handler
  (:require [clojure.test :refer :all]
            [clj-webdriver.taxi :refer :all]
            [crypto-square.test.config :refer :all]))

(use-fixtures :once with-browser)

(deftest encryption
  (to test-base-url)

  (input-text "#plaintext" "Macromonitoring for Microservices")

  (submit "#encrypt")

  (is (= "moimesannircigcvrtfrioooocmrrse" (value "#ciphertext")) "Encryption incorrect!"))

(deftest ^:synth synthetic-trans-generator
  (to test-base-url)

  (input-text "#plaintext" "Macromonitoring for Microservices")

  (submit "#encrypt")

  (is (= "moimesannircigcvrtfrioooocmrrse" (value "#ciphertext")) "Encryption incorrect!"))
