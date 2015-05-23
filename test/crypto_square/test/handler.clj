(ns crypto-square.test.handler
  (:require [clojure.test :refer :all]
            [clj-webdriver.taxi :refer :all]
            [crypto-square.test.config :refer :all]))

(use-fixtures :once with-server with-browser)

(deftest homepage-greeting
  (to test-base-url)
  (is (= (text "body") "Crypto Square")))

(deftest encryption
  (to test-base-url)
  (input-text "textarea#plaintext" "macromonitoring for microservices")
  (submit "input#encrypt"))