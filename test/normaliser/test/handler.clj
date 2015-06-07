(ns normaliser.test.handler
  (:use clojure.test
        ring.mock.request
        normaliser.handler
        cheshire.core
        ring.util.codec))

(defn- success-response [response]
  (= (:status response) 200))

(defn- expect-cipher-to-be [response ciphertext]
  (.equals (get (parse-string (:body response)) "normalised-text") ciphertext))

(deftest test-app
  (testing "gracefully handles no input"
    (let [response (app (request :get "/"))]
      (is (success-response response))
      (is (expect-cipher-to-be response ""))))

  (testing "already normal text is unchanged"
    (let [response (app (request :get "/abcd1234"))]
      (is (success-response response))
      (is (expect-cipher-to-be response "abcd1234"))))

  (testing "everything is down cased"
    (let [response (app (request :get "/aBcDe"))]
      (is (success-response response))
      (is (expect-cipher-to-be response "abcde"))))

  (testing "punctuation is removed"
    (let [response (app (request :get (str "/" (url-encode "a{}bcd']12#$%3&4"))))]
      (is (success-response response))
      (is (expect-cipher-to-be response "abcd1234"))))

  (testing "spaces are removed"
    (let [response (app (request :get "/a+b+c+d"))]
      (is (success-response response))
      (is (expect-cipher-to-be response "abcd")))))
