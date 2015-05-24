(ns crypto-square-be.routes.home
  (:require 
  	[compojure.core :refer :all]
    [clojure.tools.logging :as log]
 		[crypto-square-be.views.layout :as layout]
  	[crypto-square-be.models.core :as model]))

(defn home 
	[plaintext & corr-id]
  (layout/json-response {:ciphertext (model/ciphertext plaintext corr-id)} corr-id))

(defroutes home-routes
  (POST "/" request 
  	(home 
  		(get-in request [:body "plaintext"]) 
  		(get-in request [:headers "x-correlation-id"])))
  (GET  "/:plaintext" [plaintext] (home plaintext))
  (GET  "/" request (home ""))
  )
