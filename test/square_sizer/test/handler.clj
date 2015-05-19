(ns square-sizer.test.handler
  (:use clojure.test
        ring.mock.request
        cheshire.core        
        square-sizer.handler))

(deftest test-app
  (testing "handles empty input gracefully"
    (let [response (app (request :get "/"))
          body (parse-string (:body response))]
      (is (= (:status response) 200))
      (is (= (get body "size") 0))))

  (testing "size when plaintext length is exact square"
    (let [response (app (request :get "/abcd"))
          body (parse-string (:body response))]
      (is (= (:status response) 200))
      (is (= (get body "size") 2))))

  (testing "size when plaintext length is not square"
    (let [response (app (request :get "/abcde"))
          body (parse-string (:body response))]
      (is (= (:status response) 200))
      (is (= (get body "size") 3)))))

