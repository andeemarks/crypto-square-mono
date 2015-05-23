(ns crypto-square-be.test.models.core
  (:use clojure.test
        crypto-square-be.models.core))

(deftest crypto_square_be.models
  (testing "ciphertext handle nil input"
    (let [response (ciphertext "")]
      (is (= response "")))))
