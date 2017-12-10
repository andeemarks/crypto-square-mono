(ns crypto-square.views.layout
  (:require 
  	[hiccup.page :refer [html5 include-css]]
  	[hiccup.core :refer :all]
  	[hiccup.element :refer [link-to]]
		[hiccup.form :refer :all]))

(defn common [& body]
  (html5
    [:head
     [:title "Welcome to crypto-square"]
     (include-css "/css/screen.css")]
    [:h1 "Crypto Square"] 
    [:body body]))

(defn- show-form [plaintext ciphertext]
  (common 
  	[:body 
			(form-to {:class "dark-matter"} [:post "/encrypt"] 
        "<label>"
          "<span>Plaintext</span>"
  		    (text-field {:size 40 } :plaintext plaintext)
        "</label>"
        "<label>"
          "<span>Ciphertext</span>"
          (text-field {:id "ciphertext"} :ciphertext ciphertext)
        "</label>"
        "<label>"
          "<span>&nbsp;</span>"
	  		 (submit-button {:id "encrypt" :class "button"} "Encryptionise!")
        "</label>")]))

(defn input-form 
  ([]                      (show-form "" ""))
  ([plaintext ciphertext]  (show-form plaintext ciphertext)))
