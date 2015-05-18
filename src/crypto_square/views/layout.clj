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
  (common 
  	[:body 
			(form-to [:post "/"] 
	  		(text-area {:rows 6 :cols 40} :plaintext) 
				"<br>"
	  		(submit-button "Generate square"))]))

(defn show-square [ciphertext] 
  (common 
  	[:body
			(html
				(label :ciphertext-title "The cyphertext is")
				"<br>"
				(label {:id :ciphertext} :ciphertext ciphertext)
				"<br>"
				(link-to "/" "Again?"))]))