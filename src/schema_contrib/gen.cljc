(ns schema-contrib.gen
  (:require [clojure.string :as string]
            [clojure.test.check.generators :as gen]
            [schema-contrib.data :as data]))

(def country
  (gen/elements data/countries))

(def country-keyword
  (->> data/countries
       (map keyword)
       gen/elements))

(def language
  (gen/elements data/languages))

(def language-keyword
  (->> data/languages
       (map keyword)
       gen/elements))
