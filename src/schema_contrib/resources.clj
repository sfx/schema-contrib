(ns schema-contrib.resources
  (:require [clojure.java.io :as io])
  (:import (java.util Locale)))

(defmacro load-resource [path]
  (let [content (slurp (io/resource path))]
    content))

(defmacro iso-countries []
  (into [] (Locale/getISOCountries)))

(defmacro iso-languages []
  (into [] (Locale/getISOLanguages)))
