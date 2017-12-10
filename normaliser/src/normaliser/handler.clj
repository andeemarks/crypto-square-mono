(ns normaliser.handler
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [ring.middleware.logger :refer [wrap-with-logger]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [normaliser.routes.home :refer [home-routes]]))

(defn init []
  (println "normaliser is starting"))

(defn destroy []
  (println "normaliser is shutting down"))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (routes home-routes app-routes)
      (handler/site)
      (wrap-json-body)
      (wrap-with-logger)
      (wrap-json-response)))
