(ns column-handler.services.riemann
  (:require [clojure.tools.logging :as log]
  	    	[environ.core :refer [env]]
			[riemann.client :as riemann]))

(defn send-event [elapsed-time corr-id]
  (try
    (let [c (riemann/tcp-client {:host (env :riemann-url)})]
      (riemann/send-event c
        {:service "column-handler" :metric (/ elapsed-time 1000000) :state "ok" :description corr-id}))
    (catch java.io.IOException ex 
      (log/warn "Cannot find Riemann!"))))
