(ns crypto-square.routes.home
  (:require [compojure.core :refer :all]
            [crypto-square.views.layout :as layout]
            [crypto-square.services.backend :as be]
            [crypto-square.models.core :as model]))

(defn home []
  (layout/input-form))

(defn build-square [params]
	(let [plaintext (:plaintext params)
				ciphertext (model/ciphertext plaintext)]
	  (layout/input-form plaintext ciphertext)))

(defn health-check []
	(let [backend-health (be/healthcheck)]
		{:body 
      {:healthy? (:healthy? backend-health) 
        :services {
          :backend backend-health}}}))

(defroutes home-routes
  (GET  "/"  [] (home))
  (GET  "/health"  request (health-check))
  (POST "/encrypt" request (build-square (:params request))))
