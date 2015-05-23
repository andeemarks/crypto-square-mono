(ns normaliser.routes.home
  (:require [compojure.core :refer :all]
  			[normaliser.models.core :as model]
            [normaliser.views.layout :as layout]))

(defn home [plaintext]
  (layout/json-response {:normalised-text (model/normalise plaintext)}))

(defroutes home-routes
  (GET  "/:plaintext" [plaintext] (home plaintext))
  (GET  "/" [] (home "")))
