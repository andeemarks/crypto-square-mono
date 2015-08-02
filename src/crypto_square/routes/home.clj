(ns crypto-square.routes.home
  (:require [compojure.core :refer :all]
  					[metrics.health.core :as health]
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

(health/defhealthcheck "backend-available?" (fn [] (let [status (be/available?)]
                                         (if (== status 200)
                                            (health/healthy "backend is available!")
                                            (health/unhealthy "backend is unavailable!")))))


(health/defhealthcheck "riemann-available?" (fn [] (let [status (riemann/available?)]
                                         (if (not status)
                                            (health/unhealthy "riemann is unavailable!")
                                            (health/healthy "riemann is available!")))))

(defn health-check []
	(let [results (health/check riemann-available?)]
		{:body {:healthy? (.isHealthy results) :message (.getMessage results)}}))

(defroutes home-routes
  (GET  "/"  [] (home))
  (GET  "/metrics" request (prometheus/metrics request))
  (GET  "/health"  request (health-check))
  (POST "/encrypt" request (build-square (:params request))))
