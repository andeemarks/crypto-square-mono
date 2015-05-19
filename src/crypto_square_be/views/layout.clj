(ns crypto-square-be.views.layout
  (:require [cheshire.core :as json]))

(defn json-response [data & [status]]
  {:status  (or status 200)
   :headers {"Content-Type" "application/hal+json; charset=utf-8"}
   :body    (json/generate-string data)})
