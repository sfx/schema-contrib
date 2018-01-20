(ns schema-contrib.data
  #?(:clj
           (:require [schema-contrib.resources :as load])
     :cljs (:require-macros [schema-contrib.resources :as load])))

(def languages (load/iso-languages))
(def countries (load/iso-countries))