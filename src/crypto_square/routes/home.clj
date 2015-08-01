(ns crypto-square.routes.home
  (:require [compojure.core :refer :all]
  					[metrics.health.core :as health]
            [crypto-square.views.layout :as layout]
            [prometheus.core :as prometheus]
            [clojure.reflect :as r]
            [crypto-square.models.core :as model]))

(defn home []
  (layout/input-form))

(defn build-square [params]
	(let [plaintext (:plaintext params)
				ciphertext (model/ciphertext plaintext)]
	  (layout/input-form plaintext ciphertext)))

(health/defhealthcheck "healthy" (fn [] (let [now (.getSeconds (java.util.Date.))]
                                         (if (< now 30)
                                            (health/healthy "%d is less than 30!" now)
                                            (health/unhealthy "%d is more than 30!" now)))))

(defn health-check []
	(let [results (health/check healthy)]
		{:body {:healthy? (.isHealthy results) :message (.getMessage results)}}))

(defroutes home-routes
  (GET  "/"  [] (home))
  (GET  "/metrics" request (prometheus/metrics request))
  (GET  "/health"  request (health-check))
  (POST "/encrypt" request (build-square (:params request))))
