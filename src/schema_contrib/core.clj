(ns schema-contrib.core
  (:import [java.util Locale])
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [instaparse.core :as instaparse]
            [schema.core :as schema]))

(ns-unmap *ns* 'java.nio.file.Path)

;; ISO 3166-1 alpha-2 country codes
(def ^{:private true} countries
  (apply hash-set (Locale/getISOCountries)))

(defn- countries-contains?
  [c]
  (contains? countries (string/upper-case c)))

(def Country
  (schema/pred countries-contains?))

(def ^{:private true} countries-keywords
  (->> (Locale/getISOCountries)
       (map keyword)
       (apply hash-set)))

(defn- countries-keywords-contains?
  [ck]
  (contains?
    countries-keywords
    (-> ck
        name
        string/upper-case
        keyword)))

(def Country-Keyword
  (schema/pred countries-keywords-contains?))

(def ^{:private true} languages
  (apply hash-set (Locale/getISOLanguages)))

(defn- languages-contains?
  [l]
  (contains? languages (string/lower-case l)))

(def Language
  (schema/pred languages-contains?))

(def ^{:private true} languages-keywords
  (->> (Locale/getISOLanguages)
       (map keyword)
       (apply hash-set)))

(defn- languages-keywords-contains?
  [lk]
  (contains?
    languages-keywords
    (-> lk
        name
        string/lower-case
        keyword)))

(def Language-Keyword
  (schema/pred languages-keywords-contains?))

(def ^{:private true} uri-parser
  (instaparse/parser
    (io/resource "uri.abnf")
    :input-format :abnf))

(defn- path?
  [p]
  (let [parse-result (uri-parser p :start :path)]
    (not (instaparse/failure? parse-result))))

(def Path
  (schema/pred path?))

(defn- uri?
  [u]
  (-> u
      uri-parser
      instaparse/failure?
      not))

(def URI
  (schema/pred uri?))
