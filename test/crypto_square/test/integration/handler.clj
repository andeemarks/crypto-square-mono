(ns crypto-square.test.integration.handler
  (:require [midje.sweet :refer :all]
            [clj-webdriver.taxi :refer :all]
            [crypto-square.services.riemann :refer [send-event]]
            [crypto-square.test.config :refer :all]))

(defn- test-encryption-happy-path []
  (to test-base-url)

  (input-text "#plaintext" "\"Macromonitoring 4 Microservices\"")

  (submit "#encrypt")

  (value "#ciphertext") => "moicvannricigocrt4seoomesmrir")

(defmacro forever [& body] 
  `(while true ~@body))


(against-background [(send-event anything anything anything) => ..riemann..]

(with-state-changes [(before :facts (browser-up))
                     (after  :facts (browser-down))]

  (fact "Encryption works!"
    (test-encryption-happy-path))

  (fact "Synthetic transaction generator"
    (forever 
      (test-encryption-happy-path)
      (Thread/sleep 5000)))

))