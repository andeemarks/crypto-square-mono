(ns square-sizer.views.layout
  (:require [hiccup.page :refer [html5 include-css]]))

(defn common [& body]
  (html5
    [:head
     [:title "Welcome to square-sizer"]
     (include-css "/css/screen.css")]
    [:body body]))
