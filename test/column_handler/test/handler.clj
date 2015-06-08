(ns column-handler.test.handler
  (:use midje.sweet
        ring.mock.request
        column-handler.handler))

(facts "About GETs"
  (fact "main route"
    (let [response (app (request :get "/abcd/2"))]
      (:status response) => 200))
      ; (.contains (:body response) "Hello World") => truthy))

  ; (fact "not-found route"
  ;   (let [response (app (request :get "/invalid"))]
  ;     (:status response) => 404))
  )
