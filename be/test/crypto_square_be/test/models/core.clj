(ns crypto-square-be.test.models.core
  (:require
   [clojure.test :refer :all]
   [crypto-square-be.services.normaliser :refer [normalise-plaintext]]
   [crypto-square-be.services.square-sizer :refer [square-size]]
   [crypto-square-be.services.column-handler :refer [split-into-columns]]
   [crypto-square-be.models.core :as model]))

(deftest model-core
  (testing "ciphertext generation involves helper services"
    (with-redefs-fn {#'normalise-plaintext (fn [text] "abcd")
                     #'square-size (fn [text] "2")
                     #'split-into-columns (fn [text size] ["ab" "cd"])}

      #(is (= "abcd" (model/ciphertext "abcd")))))

  (testing "nil plaintext shortcircuits service chain"
    (is (= "" (model/ciphertext "")))))