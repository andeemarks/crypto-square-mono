(ns square-sizer.test.handler
  (:use clojure.test
        ring.mock.request
        square-sizer.handler))

(deftest test-app
  (testing "handles empty input gracefully"
    (let [response (app (request :get "/"))]
      (is (= (:status response) 200))
      (is (.contains (:body response) "0"))))

  (testing "size when plaintext length is exact square"
    (let [response (app (request :get "/abcd"))]
      (is (= (:status response) 200))
      (is (.contains (:body response) "2"))))

  (testing "size when plaintext length is not square"
    (let [response (app (request :get "/abcde"))]
      (is (= (:status response) 200))
      (is (.contains (:body response) "3")))))

