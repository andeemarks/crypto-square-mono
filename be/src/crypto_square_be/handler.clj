(ns crypto-square-be.handler
  (:require [compojure.api.sweet :refer :all]
            [compojure.core :as core]
            [compojure.handler :as handler]
            [compojure.route :refer :all]
            [crypto-square-be.services.normaliser :as normaliser]
            [crypto-square-be.services.square-sizer :as square-sizer]
            [crypto-square-be.services.column-handler :as column-handler]
            [crypto-square-be.views.layout :as layout]
            [crypto-square-be.models.core :as model]))

(defn init []
  (println "crypto-square-be is starting"))

(defn destroy []
  (println "crypto-square-be is shutting down"))

(defn home
  [plaintext corr-id]
  (layout/json-response {:ciphertext (model/ciphertext plaintext)} corr-id))

(defn- any-services-unhealthy? [services-health]
  (not (some #(false? (:healthy? %1)) (vals services-health))))

(defn health-check []
  (let [services-health
        {:column-handler (column-handler/healthcheck)
         :square-sizer (square-sizer/healthcheck)
         :normaliser (normaliser/healthcheck)}]
    {:body
     {:healthy? (any-services-unhealthy? services-health)
      :services services-health}}))

(def app
 (api
  {:swagger
   {:ui "/api-docs"
    :spec "/swagger.json"
    :data {:info {:title "Crypto Square Backend API"
                  :description "Web API provided by Crypto Square Backend service"}
           :tags [{:name "api", :description "backend"}]
           :consumes ["application/json"]
           :produces ["application/json"]}}}
  (POST "/" request
    (home
     (get-in request [:body "plaintext"])
     (get-in request [:headers "x-correlation-id"])))
  (GET  "/health"  request (health-check))
  (GET  "/:plaintext" [plaintext] (home plaintext nil))
  (GET  "/" []
    :summary "Dummy endpoint"
    (home "" nil))
  (undocumented
   (compojure.route/not-found (not-found {:not "found"})))))