(ns crypto-square.routes.home
  (:require [compojure.core :refer :all]
  					[metrics.health.core :as health]
            [crypto-square.views.layout :as layout]
            [prometheus.core :as prometheus]
            [clojure.reflect :as r]
            [clj-http.client :as client]
            [environ.core :refer [env]]
            [crypto-square.models.core :as model]))

(defn home []
  (layout/input-form))

(defn build-square [params]
	(let [plaintext (:plaintext params)
				ciphertext (model/ciphertext plaintext)]
	  (layout/input-form plaintext ciphertext)))

(defn http-get [address]
  (try ((client/get address {:throw-exceptions false}):status)
  (catch Exception e -1)))

(health/defhealthcheck "healthy" (fn [] (let [status (http-get (env :backend-url))]
                                         (if (== status 200)
                                            (health/healthy "%s is available!" (env :backend-url))
                                            (health/unhealthy "%s is unavailable!" (env :backend-url))))))

(defn health-check []
	(let [results (health/check healthy)]
		{:body {:healthy? (.isHealthy results) :message (.getMessage results)}}))

(defroutes home-routes
  (GET  "/"  [] (home))
  (GET  "/metrics" request (prometheus/metrics request))
  (GET  "/health"  request (health-check))
  (POST "/encrypt" request (build-square (:params request))))
