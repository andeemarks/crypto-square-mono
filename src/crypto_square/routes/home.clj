(ns crypto-square.routes.home
  (:require [compojure.core :refer :all]
            [crypto-square.views.layout :as layout]
            [prometheus.core :as prometheus]
            [crypto-square.models.core :as model]))

(defn home []
  (layout/input-form))

(defn build-square [params]
	(let [plaintext (:plaintext params)
				ciphertext (model/ciphertext plaintext)]
	  (layout/input-form plaintext ciphertext)))

(defroutes home-routes
  (GET "/"  [] (home))
  (GET "/metrics"  request (prometheus/metrics request))
  (POST "/encrypt" request (build-square (:params request))))
