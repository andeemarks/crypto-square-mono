(ns column-handler.handler
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [ring.middleware.logger :refer [wrap-with-logger]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [column-handler.routes.home :refer [home-routes]]))

(defn init []
  (println "column-handler is starting"))

(defn destroy []
  (println "column-handler is shutting down"))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (routes home-routes app-routes)
      (handler/site)
      (wrap-with-logger)
      (wrap-json-body)
      (wrap-json-response)))
