(ns crypto-square.routes.home
  (:require [compojure.core :refer :all]
            [crypto-square.views.layout :as layout]
            [crypto-square.models.core :as model]))

(defn home []
  (layout/input-form))

(defn build-square [request]
	(let [ciphertext (model/ciphertext (:plaintext (:params request)))]
	  (layout/show-square ciphertext)))

(defroutes home-routes
  (GET "/"  [] (home))
  (POST "/" request (build-square request)))
