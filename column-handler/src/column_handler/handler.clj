(ns column-handler.handler
  (:require [compojure.api.sweet :refer :all]
            [compojure.route :refer :all]
            [column-handler.models.core :refer [columnise]]
            [column-handler.views.layout :refer [json-response]]))

(defn init []
  (println "column-handler is starting"))

(defn destroy []
  (println "column-handler is shutting down"))

(defn home [plaintext segment-size corr-id]
  (try
    (json-response {:column-text (columnise plaintext segment-size corr-id)})
    (catch IllegalArgumentException e
      {:status  400
       :body {:error (.getMessage e)}
       :headers {"Content-Type" "application/json; charset=utf-8"}})))

(def app
  (api
  ;  (undocumented
  ;   (compojure.route/not-found (not-found {:not "found"})))
  {:swagger
   {:ui "/api-docs"
    :spec "/swagger.json"
    :data {:info {:title "Column Handler API"
                  :description "Web API provided by Column Handler service"}
           :tags [{:name "api", :description "column-handler"}]
           :consumes ["application/json"]
           :produces ["application/json"]}}}
  (GET  "/:plaintext/:segment-size" [plaintext segment-size :as request]
    :summary "Splits the plaintext into groups of segment-size"
    (home
     plaintext
     segment-size
     (get-in request [:headers "x-correlation-id"])))))