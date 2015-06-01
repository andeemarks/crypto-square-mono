(ns crypto-square.routes.home
  (:require [compojure.core :refer :all]
            [crypto-square.views.layout :as layout]
            [crypto-square.models.core :as model]))

(defn home []
  (layout/input-form))

(defn build-square [params]
	(let [plaintext (:plaintext params)
				ciphertext (model/ciphertext plaintext)]
	  (layout/input-form plaintext ciphertext)))

(defroutes home-routes
  (GET "/"  [] (home))
  (POST "/encrypt" request (build-square (:params request))))
