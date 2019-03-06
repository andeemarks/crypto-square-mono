(ns column-handler.test.handler
  (:require
   [ring.mock.request :refer :all]
   [clojure.test :refer :all]
   [cheshire.core :refer :all]
   [column-handler.handler :refer :all]))

(defn- response-body-for [input]
  (let [body (slurp (:body (app (request :get input))))]
    (get (parse-string body) "column-text")))

(defn- response-status-for [input]
  (:status (app (request :get input))))

(deftest test-routes
  (testing "happy responses are 200s"
    (is (= 200 (response-status-for "/abcd/2"))))

  (testing "two arguments are needed"
    (is (= 404 (response-status-for "/abcd/"))))

  (testing "second arguments must be numeric"
    (is (= 400 (response-status-for "/abcd/a"))))

  (testing "responses are found in column-text inside response body"
    (is (not (nil? (response-body-for "/abcd/2")))))

  (testing "second argument denotes number of rows"
    (is (= ["ad" "be" "cf"] (response-body-for "/abcdef/3"))))

  (testing "one row does not change input"
    (is (= ["abcdef"] (response-body-for "/abcdef/1"))))

  (testing "rows will be added if not enough input"
    (is (= ["a" ""] (response-body-for "/a/2")))))
