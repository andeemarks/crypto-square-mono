(ns column-handler.routes.home
  (:require [compojure.core :refer :all]
      			[column-handler.models.core :as model]
            [column-handler.views.layout :as layout]))


(defn home [plaintext segment-size corr-id]
  (try
    (layout/json-response {:column-text (model/columnise plaintext segment-size corr-id)})
    (catch IllegalArgumentException e
      {:status  400
       :body {:error (.getMessage e)}
       :headers {"Content-Type" "application/json; charset=utf-8"}})))

(defroutes home-routes
  (GET  "/:plaintext/:segment-size" [plaintext segment-size :as request] 
  	(home 
  		plaintext 
  		segment-size 
  		(get-in request [:headers "x-correlation-id"]))))
