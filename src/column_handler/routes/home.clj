(ns column-handler.routes.home
  (:require [compojure.core :refer :all]
      			[column-handler.models.core :as model]
            [column-handler.services.riemann :as riemann]
            [column-handler.views.layout :as layout]))


(defn home [plaintext segment-size corr-id]
  (layout/json-response {:column-text (model/columnise plaintext segment-size corr-id)}))

(defn health-check []
  (let [riemann-health (riemann/healthcheck)]
  {:body 
    {:healthy? (:healthy? riemann-health) 
      :services {
        :riemann riemann-health}}}))

(defroutes home-routes
  (GET  "/health"  request (health-check))
  (GET  "/:plaintext/:segment-size" [plaintext segment-size :as request] 
  	(home 
  		plaintext 
  		segment-size 
  		(get-in request [:headers "x-correlation-id"])))
  (GET  "/" [] (home "" "" "")))
