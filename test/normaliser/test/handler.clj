(ns normaliser.test.handler
  (:use clojure.test
        ring.mock.request
        normaliser.handler
        cheshire.core
        ring.util.codec))

(deftest test-app
  (testing "already normal text is unchanged"
    (let [response (app (request :get "/abcd1234"))
          body (parse-string (:body response))]
      (is (= (:status response) 200))
      (is (.equals (get body "normalised-text") "abcd1234"))))

  (testing "everything is down cased"
    (let [response (app (request :get "/aBcDe"))
          body (parse-string (:body response))]
      (is (= (:status response) 200))
      (is (.equals (get body "normalised-text") "abcde"))))

  (testing "punctuation is removed"
    (let [response (app (request :get (str "/" (url-encode "a{}bcd']12#$%3&4"))))
          body (parse-string (:body response))]
      (is (= (:status response) 200))
      (is (.equals (get body "normalised-text") "abcd1234"))))

  (testing "spaces are removed"
    (let [response (app (request :get "/a+b+c+d"))
          body (parse-string (:body response))]
      (is (= (:status response) 200))
      (is (.equals (get body "normalised-text") "abcd")))))
