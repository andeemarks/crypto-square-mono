(ns normaliser.test.handler
  (:use clojure.test
        ring.mock.request
        normaliser.handler
        ring.util.codec))

(deftest test-app
  (testing "already normal text is unchanged"
    (let [response (app (request :get "/abcd1234"))]
      (is (= (:status response) 200))
      (is (.contains (:body response) "abcd1234"))))

  (testing "everything is down cased"
    (let [response (app (request :get "/aBcDe"))]
      (is (= (:status response) 200))
      (is (.contains (:body response) "abcde"))))

  (testing "punctuation is removed"
    (let [response (app (request :get (str "/" (url-encode "a{}bcd']12#$%3&4"))))]
      (is (= (:status response) 200))
      (is (.contains (:body response) "abcd1234"))))

  (testing "spaces are removed"
    (let [response (app (request :get "/a+b+c+d"))]
      (is (= (:status response) 200))
      (is (.contains (:body response) "abcd")))))
