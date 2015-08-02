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

(health/defhealthcheck "riemann-available?" (fn [] (if (not (riemann/available?))
                                            (health/unhealthy "riemann is unavailable!")
                                            (health/healthy "riemann is available!"))))

(defn health-check []
	(let [backend-health (be/healthcheck)
        riemann-health (health/check riemann-available?)]
		{:body 
      {:healthy? (and (:healthy? backend-health) (.isHealthy riemann-health)) 
        :services {
          :riemann {:health (.isHealthy riemann-health) :message (.getMessage riemann-health)} 
          :backend backend-health}}}))

(defroutes home-routes
  (GET  "/"  [] (home))
  (GET  "/metrics" request (prometheus/metrics request))
  (GET  "/health"  request (health-check))
  (POST "/encrypt" request (build-square (:params request))))
