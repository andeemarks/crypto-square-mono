(ns square-sizer.test.handler
  (:require
   [ring.mock.request :refer :all]
   [clojure.test :refer :all]
   [cheshire.core :refer :all]
   [square-sizer.handler :refer :all]))

(deftest test-routes
  (testing "gracefully handling of empty input"
    (let [response (app (request :get "/"))
          body (parse-string (slurp (:body response)))]
      (is (= 200 (:status response)))
      (is (= 0 (get body "size")))))
      
  (testing "correct calculation when plaintext length is exact square"
    (let [response (app (request :get "/abcd"))
          body (parse-string (slurp (:body response)))]
      (is (= 200 (:status response)))
      (is (= 2 (get body "size")))))
      
  (testing "correct calculation when plaintext length is not square"
    (let [response (app (request :get "/abcde"))
          body (parse-string (slurp (:body response)))]
      (is (= 200 (:status response)))
      (is (= 3 (get body "size"))))))