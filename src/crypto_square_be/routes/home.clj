(ns crypto-square-be.routes.home
  (:require 
  	[compojure.core :refer :all]
  	[crypto-square-be.views.layout :as layout]
  	[crypto-square-be.models.core :as model]))

(defn home [plaintext]
  (layout/json-response (model/ciphertext plaintext)))

(defroutes home-routes
  (POST "/" request (home (:plaintext (:params request))))
  (GET  "/:plaintext" [plaintext] (home plaintext))
  )
