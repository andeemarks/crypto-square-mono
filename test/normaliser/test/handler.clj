(ns normaliser.test.handler
  (:use midje.sweet
    normaliser.services.riemann
    ring.mock.request
    normaliser.handler
    cheshire.core
    ring.util.codec))

(against-background [(send-event anything anything) => ..riemann..]

(facts "about GET"
  (fact "gracefully handles no input"
    (let [response (app (request :get "/"))]
      (:status response) => 200
      (get (parse-string (:body response)) "normalised-text") => ""))

  (fact "already normal text is unchanged"
    (let [response (app (request :get "/abcd1234"))]
      (:status response) => 200
      (get (parse-string (:body response)) "normalised-text") => "abcd1234"))

  (fact "everything is down cased"
    (let [response (app (request :get "/aBcDe"))]
      (:status response) => 200
      (get (parse-string (:body response)) "normalised-text") => "abcde"))

  (fact "punctuation is removed"
    (let [response (app (request :get (str "/" (url-encode "a{}bcd']12#$%3&4"))))]
      (:status response) => 200
      (get (parse-string (:body response)) "normalised-text") => "abcd1234"))

  (fact "spaces are removed"
    (let [response (app (request :get "/a+b+c+d"))]
      (:status response) => 200
      (get (parse-string (:body response)) "normalised-text") => "abcd"))

))