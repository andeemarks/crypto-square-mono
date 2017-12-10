(ns square-sizer.test.handler
  (:use midje.sweet
        ring.mock.request
        cheshire.core        
        square-sizer.handler))

(facts "about GETs"
  (fact "handles empty input gracefully"
    (let [response (app (request :get "/"))
          body (parse-string (:body response))]
      (:status response) => 200
      (get body "size") => 0))

  (fact "calculates correct size when plaintext length is exact square"
    (let [response (app (request :get "/abcd"))
          body (parse-string (:body response))]
      (:status response) => 200
      (get body "size") => 2))

  (fact "calculates correct size when plaintext length is not square"
    (let [response (app (request :get "/abcde"))
          body (parse-string (:body response))]
      (:status response) => 200
      (get body "size") => 3))

)