(ns crypto-square.routes.home
  (:require [compojure.core :refer :all]
            [crypto-square.views.layout :as layout]
            [prometheus.core :as prometheus]
            [crypto-square.services.backend :as be]
            [crypto-square.services.riemann :as riemann]
            [crypto-square.models.core :as model]))

(defn home []
  (layout/input-form))

(defn build-square [params]
	(let [plaintext (:plaintext params)
				ciphertext (model/ciphertext plaintext)]
	  (layout/input-form plaintext ciphertext)))

(defn health-check []
	(let [backend-health (be/healthcheck)
        riemann-health (riemann/healthcheck)]
		{:body 
      {:healthy? (and (:healthy? backend-health) (:healthy? riemann-health)) 
        :services {
          :riemann riemann-health 
          :backend backend-health}}}))

(defroutes home-routes
  (GET  "/"  [] (home))
  (GET  "/metrics" request (prometheus/metrics request))
  (GET  "/health"  request (health-check))
  (POST "/encrypt" request (build-square (:params request))))
