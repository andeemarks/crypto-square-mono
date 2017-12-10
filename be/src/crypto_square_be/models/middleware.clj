(ns crypto-square-be.models.middleware
  (:use compojure.core)
  (:require [compojure.route :as route]
            [ring.adapter.jetty :as jetty]))
 
(defn handle-correlation-ids [app]
  (fn [req]
    (when-let [corr-id (get (:headers req) "x-correlation-id")]
      (println corr-id))
    (app req)))
