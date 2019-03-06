(ns column-handler.test.handler
  (:require
   [ring.mock.request :refer :all]
   [clojure.test :refer :all]
   [cheshire.core :refer :all]
   [column-handler.handler :refer :all]))

(defn- response-body-for [input]
  (let [body (slurp (:body (app (request :get input))))]
    (get (parse-string body) "column-text")))

(deftest test-routes
  (testing "happy responses are 200s"
    (let [response (app (request :get "/abcd/2"))]
      (is (= 200 (:status response)))))

  ; (testing "two arguments are needed"
  ;   (let [response (app (request :get "/abcd/"))]
  ;     (is (= 404 (:status response)))))

  (testing "second arguments must be numeric"
    (let [response (app (request :get "/abcd/a"))]
      (is (= 400 (:status response)))))

  (testing "responses are found in column-text inside response body"
    (let [response (app (request :get "/abcd/2"))
          body (parse-string (slurp (:body response)))]
      (is (not (nil? (get body "column-text"))))))

  (testing "second argument denotes number of rows"
    (is (= ["ad" "be" "cf"] (response-body-for "/abcdef/3"))))

  (testing "one row does not change input"
    (is (= ["abcdef"] (response-body-for "/abcdef/1"))))

  (testing "rows will be added if not enough input"
    (is (= ["a" ""] (response-body-for "/a/2")))))
