(ns crypto-square-be.test.handler
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [crypto-square-be.models.core :as model]
            [cheshire.core :as json]
            [clj-fakes.core :as f]
            [clj-fakes.context :as fc]
            [crypto-square-be.handler :as handler]))

(defn- encrypt [plaintext]
  (handler/app
   (mock/request :get
                 (str "/" plaintext))))

(deftest handler
  (testing "Happy GETs return 200"
    (with-redefs-fn {#'model/ciphertext (fn [text] "acdb")}
      #(is (= 200 (:status (encrypt "abcd")))))))