(ns normaliser.test.handler
  (:require
   [ring.mock.request :as mock]
   [clojure.test :refer :all]
   [cheshire.core :as json]
   [normaliser.handler :as handler]))

(defn- response-for [input]
  (let [response (handler/app (mock/request :get input))]
    {:status (:status response) :body (get (json/parse-string (slurp (:body response))) "normalised-text")}))

(deftest test-routes
  (testing "gracefully handles no input"
    (let [response (response-for "/")]
      (is (= 200 (:status response)))
      (is (= "" (:body response)))))

  (testing "already normal text is unchanged"
    (let [response (response-for "/abcd1234")]
      (is (= 200 (:status response)))
      (is (= "abcd1234" (:body response)))))

  (testing "everything is down cased"
    (let [response (response-for "/aBcDe")]
      (is (= 200 (:status response)))
      (is (= "abcde" (:body response)))))

  (testing "spaces are removed"
    (let [response (response-for "/a+b+c+d")]
      (is (= 200 (:status response)))
      (is (= "abcd" (:body response))))))