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
  (let [normaliser-health (normaliser/healthcheck)
        column-handler-health (column-handler/healthcheck)
        square-sizer-health (square-sizer/healthcheck)
        riemann-health (riemann/healthcheck)]
    {:body 
      {:healthy? (and (:healthy? normaliser-health) (:healthy? riemann-health) (:healthy? column-handler-health) (:healthy? square-sizer-health)) 
        :services {
          :riemann riemann-health 
          :column-handler column-handler-health
          :square-sizer square-sizer-health
          :normaliser normaliser-health}}}))

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
