(ns square-sizer.routes.home
  (:require [compojure.core :refer :all]
  			[square-sizer.models.core :as model]
            [square-sizer.views.layout :as layout]))

(defn home [plaintext]
  (layout/json-response {:size (model/square-size plaintext "")}))

(defroutes home-routes
  (GET  "/:plaintext" [plaintext] (home plaintext))
  (GET  "/"           []          (home ""))
  )
