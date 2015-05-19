(ns crypto-square-be.handler
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [crypto-square-be.routes.home :refer [home-routes]]))

(defn init []
  (println "crypto-square-be is starting"))

(defn destroy []
  (println "crypto-square-be is shutting down"))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (routes home-routes app-routes)
      (handler/site)
      (wrap-json-body)
      (wrap-json-response)))
