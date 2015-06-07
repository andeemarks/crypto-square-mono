(ns normaliser.test.handler
  (:use clojure.test
        ring.mock.request
        normaliser.handler
        cheshire.core
        ring.util.codec))

(defn- success-response [response]
  (= (:status response) 200))

(deftest test-app
  (testing "gracefully handles no input"
    (let [response (app (request :get "/"))
          body (parse-string (:body response))]
      (is (success-response response))
      (is (.equals (get body "normalised-text") ""))))

  (testing "already normal text is unchanged"
    (let [response (app (request :get "/abcd1234"))
          body (parse-string (:body response))]
      (is (success-response response))
      (is (.equals (get body "normalised-text") "abcd1234"))))

  (testing "everything is down cased"
    (let [response (app (request :get "/aBcDe"))
          body (parse-string (:body response))]
      (is (success-response response))
      (is (.equals (get body "normalised-text") "abcde"))))

  (testing "punctuation is removed"
    (let [response (app (request :get (str "/" (url-encode "a{}bcd']12#$%3&4"))))
          body (parse-string (:body response))]
      (is (success-response response))
      (is (.equals (get body "normalised-text") "abcd1234"))))

  (testing "spaces are removed"
    (let [response (app (request :get "/a+b+c+d"))
          body (parse-string (:body response))]
      (is (success-response response))
      (is (.equals (get body "normalised-text") "abcd")))))
