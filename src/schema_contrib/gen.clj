(ns schema-contrib.gen
  (:import (java.util Locale))
  (:require [clojure.string :as string]
            [clojure.test.check.generators :as gen]))

(def country
  (gen/elements (Locale/getISOCountries)))

(def country-keyword
  (->> (Locale/getISOCountries)
       (map keyword)
       gen/elements))

(def language
  (gen/elements (Locale/getISOLanguages)))

(def language-keyword
  (->> (Locale/getISOLanguages)
       (map keyword)
       gen/elements))
