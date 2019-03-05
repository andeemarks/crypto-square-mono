(ns square-sizer.handler
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [square-sizer.routes.home :refer [home home-routes]]))

(defn init []
  (println "square-sizer is starting"))

(defn destroy []
  (println "square-sizer is shutting down"))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

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