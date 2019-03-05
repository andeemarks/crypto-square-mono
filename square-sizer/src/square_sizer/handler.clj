(ns square-sizer.handler
  (:require [compojure.api.sweet :refer :all]
            [square-sizer.views.layout :refer [json-response]]
            [square-sizer.models.core :refer [square-size]]
            [ring.util.http-response :refer :all]))

(defn init []
  (println "square-sizer is starting"))

(defn destroy []
  (println "square-sizer is shutting down"))

(defn home [plaintext corr-id]
  (json-response {:size (square-size plaintext corr-id)}))

(def app
  (api
   {:swagger
    {:ui "/api-docs"
     :spec "/swagger.json"
     :data {:info {:title "Square Sizer API"
                   :description "Web API provided by Square Sizer service"}
            :tags [{:name "api", :description "square-sizer"}]
            :consumes ["application/json"]
            :produces ["application/json"]}}}
   (GET  "/:plaintext" [plaintext :as request]
     :summary "Calculate the square size of the plaintext argument"
     (home plaintext (get-in request [:headers "x-correlation-id"])))
   (GET  "/"           []
     :summary "Dummy endpoint"
     (home "" ""))))