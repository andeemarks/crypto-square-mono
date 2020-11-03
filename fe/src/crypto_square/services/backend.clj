(ns crypto-square.services.backend
  (:require
   [clj-http.client :as client]
   [environ.core :refer [env]]
   [metrics.health.core :as health]
   [cheshire.core :as json]))

(defn- ciphertext-request [plaintext corr-id]
  (client/post
   (env :backend-url)
   {:body (json/generate-string {:plaintext plaintext})
    :content-type :json
    :headers {"X-Correlation-Id" corr-id}
    :accept :json}))

(defn- healthcheck-page []
  (str (env :backend-url) "/health"))

(defn available? []
  (try ((client/get (healthcheck-page) {:throw-exceptions false}) :status)
       (catch Exception e -1)))

(health/defhealthcheck "backend-available?" (fn [] (if (== (available?) 200)
                                                     (health/healthy "backend is available!")
                                                     (health/unhealthy "backend is unavailable!"))))

(defn healthcheck []
  (let [health (health/check backend-available?)]
    {:healthy? (.isHealthy health) :message (.getMessage health)}))

(defn encrypt [plaintext corr-id]
  (get
   (json/parse-string
    (:body
     (ciphertext-request plaintext corr-id)))
   "ciphertext"))
