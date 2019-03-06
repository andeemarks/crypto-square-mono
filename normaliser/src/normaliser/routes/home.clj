(ns normaliser.routes.home
  (:require [compojure.core :refer :all]
            [normaliser.models.core :as model]
            [normaliser.views.layout :as layout]))

(defn home [plaintext corr-id]
  (layout/json-response {:normalised-text (model/normalise plaintext corr-id)}))

(defroutes home-routes
  (GET  "/:plaintext" [plaintext :as request] (home plaintext (get-in request [:headers "x-correlation-id"])))
  (GET  "/" [] (home "" "")))
