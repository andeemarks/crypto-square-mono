(ns square-sizer.models.core)
 
(defn square-size [text]
  (int (Math/ceil (Math/sqrt (count text)))))
