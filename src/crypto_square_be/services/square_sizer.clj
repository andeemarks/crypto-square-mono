(ns crypto-square-be.services.square-sizer
  (:require [clj-http.client :as client]
	  	      [environ.core :refer [env]]
            [langohr.core      :as rmq]
            [langohr.channel   :as lch]
            [langohr.queue     :as lq]
            [langohr.consumers :as lc]
            [langohr.basic     :as lb]
            [cheshire.core :as json]))
 
(def ^{:const true}
  default-exchange-name "")

(defn- message-handler
  [ch {:keys [content-type delivery-tag type] :as meta} ^bytes payload]
  (println (format "[consumer] Received a message: %s, delivery tag: %d, content type: %s, type: %s"
                   (String. payload "UTF-8") delivery-tag content-type type)))

(defn- publish-request
  [plaintext corr-id]
  (let [conn  (rmq/connect)
        ch    (lch/open conn)
        qname (env :square-sizer-queue)]
    (println (format "[main] Connected. Channel id: %d" (.getChannelNumber ch)))
    (lq/declare ch qname {:exclusive false :auto-delete true})
    ; (lc/subscribe ch qname message-handler {:auto-ack true})
    (lb/publish ch default-exchange-name qname plaintext {:content-type "text/plain" :corr-id corr-id :type "request"})
    (rmq/close ch)
    (rmq/close conn)))

(defn- square-size-request [plaintext corr-id]
  (client/get 
    (str (env :square-sizer-url) "/" plaintext)
    {:accept :json
     :headers {"X-Correlation-Id" corr-id}}))
 
(defn square-size [text corr-id]
  (let [response (square-size-request text corr-id)
        json-body (json/parse-string (:body response))]
    (publish-request text corr-id)
    (get json-body "size")))
