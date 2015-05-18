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

(defn input-form [] 
	(form-to [:post "/"] 
	  (text-area {:rows 6 :cols 40} :plaintext) 
	  (submit-button "Generate square")))

(defn show-square [ciphertext] 
	(html
		(label :ciphertext-title "The cyphertext is")
		(label :ciphertext ciphertext)
		(link-to "/" "Again?")))