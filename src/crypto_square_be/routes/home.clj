(ns crypto-square-be.routes.home
  (:require 
  	[compojure.core :refer :all]
    [clojure.tools.logging :as log]
    [prometheus.core :as prometheus]
    [crypto-square-be.services.normaliser :as normaliser]
    [crypto-square-be.services.riemann :as riemann]
    [crypto-square-be.services.square-sizer :as square-sizer]
    [crypto-square-be.services.column-handler :as column-handler]
 		[crypto-square-be.views.layout :as layout]
  	[crypto-square-be.models.core :as model]))

(defn home 
	[plaintext corr-id]
  (layout/json-response {:ciphertext (model/ciphertext plaintext corr-id)} corr-id))

(defn health-check []
  (let [services-health 
    {:riemann (riemann/healthcheck) 
      :column-handler (column-handler/healthcheck) 
      :square-sizer (square-sizer/healthcheck) 
      :normaliser (normaliser/healthcheck)}]
    {:body 
      {:healthy? (not (some #(false? (:healthy? %1)) (vals services-health)))
        :services services-health}}))

(defroutes home-routes
  (POST "/" request 
  	(home 
  		(get-in request [:body "plaintext"]) 
  		(get-in request [:headers "x-correlation-id"])))
  (GET "/metrics"  request (prometheus/metrics request))
  (GET  "/health"  request (health-check))
  (GET  "/:plaintext" [plaintext] (home plaintext nil))
  (GET  "/" request (home "" nil))
  )
