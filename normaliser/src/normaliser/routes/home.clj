(ns normaliser.routes.home
  (:require [compojure.core :refer :all]
  			[normaliser.models.core :as model]
  			[normaliser.services.riemann :as riemann]
            [normaliser.views.layout :as layout]))

(defn home [plaintext corr-id]
  (layout/json-response {:normalised-text (model/normalise plaintext corr-id)}))

(defn health-check []
  (let [riemann-health (riemann/healthcheck)]
  {:body 
    {:healthy? (:healthy? riemann-health) 
      :services {
        :riemann riemann-health}}}))

(defroutes home-routes
  (GET  "/health"  request (health-check))
  (GET  "/:plaintext" [plaintext :as request] (home plaintext (get-in request [:headers "x-correlation-id"])))
  (GET  "/" [] (home "" "")))
