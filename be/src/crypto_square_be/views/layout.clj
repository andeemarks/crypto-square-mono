(ns crypto-square-be.views.layout)

(defn json-response [data correlation-id & [status]]
  {:status  (or status 200)
   :headers {"Content-Type" "application/json; charset=utf-8" "x-correlation-id" correlation-id}
   :body    data})
