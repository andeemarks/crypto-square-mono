(ns crypto-square-be.routes.home
  (:require 
  	[compojure.core :refer :all]
  	[crypto-square-be.views.layout :as layout]
  	[crypto-square-be.models.core :as model]
  	[cheshire.core :as json]))

(defn home [plaintext]
	(prn plaintext)
  (layout/json-response (model/ciphertext plaintext)))

(defroutes home-routes
  (POST "/" request (home (get-in request [:body "plaintext"])))
  (GET  "/:plaintext" [plaintext] (home plaintext))
  )
