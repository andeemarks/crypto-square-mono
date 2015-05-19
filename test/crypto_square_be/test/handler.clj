(ns crypto-square-be.test.handler
  (:use clojure.test
        ring.mock.request
        cheshire.core
        crypto-square-be.handler))

(defn- encrypt [plaintext]
  (app (request 
        :get
        (str "/" plaintext))))

(deftest crypto_square_be
  (testing "single word encryption"
    (let [response (encrypt "abcd")
          body (parse-string (:body response))]
      (is (= (:status response) 200))
      (is (.equals (get body "ciphertext") "acbd"))))

  (testing "multi word encryption"
    (let [response (encrypt "ab+cd")
          body (parse-string (:body response))]
      (is (= (:status response) 200))
      (is (.equals (get body "ciphertext") "acbd"))))
  )
