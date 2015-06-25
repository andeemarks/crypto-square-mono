(ns crypto-square.views.layout
  (:require 
  	[hiccup.page :refer [html5 include-css]]
  	[hiccup.core :refer :all]
    [hiccup.bootstrap.page :refer :all]
  	[hiccup.element :refer [link-to]]
		[hiccup.form :refer :all]))

(defn common [& body]
  (html5
    [:head
      [:title "Welcome to crypto-square"]
      (include-bootstrap)]
    [:div.page-header [:h1 "crypto-square"]]
    [:body body]))

(defn- show-form [plaintext ciphertext]
  (common 
  	[:body 
      [:div.alert.alert-info "Enter your plaintext and click Encryptionise to see the ciphertext"]
			(form-to {:class "form-horizontal"} [:post "/encrypt"] 
        [:div.form-group
          [:label.col-sm-2.control-label {:for "plaintext"} "Plaintext"]
          [:div.col-sm-10
            (text-field {:size 40 } :plaintext plaintext)]]
        [:div.form-group
          [:label.col-sm-2.control-label {:for "ciphertext"} "Ciphertext"]
          [:div.col-sm-10
  		      (text-field {:size 40 } :plaintext ciphertext)]]
        [:div.form-group
          [:div.col-sm-offset-2.col-sm-10 (submit-button {:id "encrypt" :class "btn btn-primary"} "Encryptionise!")]]
        )
      ]
    )
  )

(defn input-form 
  ([]                      (show-form "" ""))
  ([plaintext ciphertext]  (show-form plaintext ciphertext)))
