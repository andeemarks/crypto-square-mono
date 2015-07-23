(ns square-sizer.routes.home
  (:require [compojure.core :refer :all]
    		[prometheus.core :as prometheus]
  			[square-sizer.models.core :as model]
            [square-sizer.views.layout :as layout]))

(defn home [plaintext corr-id]
  (layout/json-response {:size (model/square-size plaintext corr-id)}))

(defroutes home-routes
  (GET "/metrics"  request (prometheus/metrics request))
  (GET  "/:plaintext" [plaintext :as request] (home plaintext (get-in request [:headers "x-correlation-id"])))
  (GET  "/"           []                      (home "" ""))
  )
