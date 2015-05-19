(ns crypto-square-be.test.handler
  (:use clojure.test
        ring.mock.request
        crypto-square-be.handler))

(defn- encrypt [plaintext]
  (app (request 
        :get
        (str "/" plaintext))))

(deftest crypto_square_be
  (testing "single word encryption"
    (let [response (encrypt "abcd")]
      (is (= (:status response) 200))
      (is (.contains (:body response) "acbd"))))

  (testing "multi word encryption"
    (let [response (encrypt "ab+cd")]
      (is (= (:status response) 200))
      (is (.contains (:body response) "acbd"))))
  )
