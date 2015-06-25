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
      [:div.panel.panel-default
        [:div.panel-body
    			(form-to {:class "dark-matter"} [:post "/encrypt"] 
            [:div.panel-heading "Plaintext"]
    		    (text-field {:size 40 } :plaintext plaintext)
            [:div.panel-heading "Ciphertext"]
            (text-field {:id "ciphertext"} :ciphertext ciphertext)
            [:div.panel-heading  (submit-button {:id "encrypt" :class "button"} "Encryptionise!")])]]]))

(defn input-form 
  ([]                      (show-form "" ""))
  ([plaintext ciphertext]  (show-form plaintext ciphertext)))

; <div class="panel panel-default">
;   <div class="panel-heading">
;     <h3 class="panel-title">Panel title</h3>
;   </div>
;   <div class="panel-body">
;     Panel content
;   </div>
; </div>