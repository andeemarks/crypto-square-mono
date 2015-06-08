(ns column-handler.test.handler
  (:use midje.sweet
        ring.mock.request
        cheshire.core        
        column-handler.handler))

(facts "About GETs"
  (fact "main route"
    (let [response (app (request :get "/abcd/2"))
          body (parse-string (:body response))]
      (:status response) => 200
      (get body "column-text") => ["ac" "bd"]))
  )
