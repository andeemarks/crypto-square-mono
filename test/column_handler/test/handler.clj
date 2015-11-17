(ns column-handler.test.handler
  (:require [column-handler.services.riemann :as riemann])
  (:use midje.sweet
        ring.mock.request
        cheshire.core        
        column-handler.handler))

  (fact "riemann failures are not fatal"
    (:status (app (request :get "/riemann-failure/2"))) => 200
    (provided (riemann/connection) =throws=> (IllegalArgumentException.)))

(against-background [(riemann/send-event anything anything) => ..riemann..]

  (facts "About main GET route"
    (fact "happy responses are 200s"
      (let [response (app (request :get "/abcd/2"))]
        (:status response) => 200))

    (fact "two arguments are needed"
      (let [response (app (request :get "/abcd/"))]
        (:status response) => 404))

    (fact "second arguments must be numeric"
      (let [response (app (request :get "/abcd/a"))]
        (:status response) => 400))

    (fact "responses are found in column-text inside response body"
      (let [response (app (request :get "/abcd/2"))
            body (parse-string (:body response))]
        (nil? (get body "column-text")) => false))

    (facts "About the responses from GET"
      (defn- response-for [input]
        (get (parse-string 
          (:body 
            (app (request :get input)))) "column-text"))

      (fact "second argument denotes number of rows"
        (response-for "/abcdef/3") => ["ad" "be" "cf"]) 

      (fact "one row does not change input"
        (response-for "/abcdef/1") => ["abcdef"])

      (fact "rows will be added if not enough input"
        (response-for "/a/2") => ["a" ""])
    )
  )
)
