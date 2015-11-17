(ns column-handler.services.riemann
  (:require [clojure.tools.logging :as log]
  	    	[environ.core :refer [env]]
			[riemann.client :as riemann]))

(defn send-event [elapsed-time corr-id]
  (try
    (let [c (riemann/tcp-client {:host (env :riemann-url)})]
      (riemann/send-event c
        {:service "column-handler" :metric (/ elapsed-time 1000000) :state "ok" :description corr-id}))
    (catch java.lang.Exception ex
      (log/warn (str "Error connecting to Riemann using config:" (env :riemann-host))))
    (catch java.io.IOException ex 
      (log/warn (str "Cannot find Riemann at: " (env :riemann-host))))))