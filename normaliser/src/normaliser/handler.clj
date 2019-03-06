(ns normaliser.handler
  (:require [compojure.api.sweet :refer :all]
            [compojure.route :refer :all]
            [normaliser.models.core :refer [normalise]]
            [normaliser.views.layout :refer [json-response]]))

(defn init []
  (println "normaliser is starting"))

(defn destroy []
  (println "normaliser is shutting down"))

(defn home [plaintext corr-id]
  (json-response {:normalised-text (normalise plaintext corr-id)}))

(def app
  (api
   {:swagger
    {:ui "/api-docs"
     :spec "/swagger.json"
     :data {:info {:title "Normaliser API"
                   :description "Web API provided by Normaliser service"}
            :tags [{:name "api", :description "normaliser"}]
            :consumes ["application/json"]
            :produces ["application/json"]}}}
   (GET  "/" []
     :summary "Dummy endpoint"
     (home "" ""))
   (GET  "/:plaintext" [plaintext :as request]
     :summary "Removes any non alphanumeric characters from plaintext"
     (home plaintext (get-in request [:headers "x-correlation-id"])))
   (undocumented
    (compojure.route/not-found (not-found {:not "found"})))))