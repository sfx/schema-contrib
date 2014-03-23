(ns schema-contrib.core
  (:import [java.util Locale])
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [instaparse.core :as instaparse]
            [schema.core :as schema]))

;; Country (ISO 3166-1 alpha-2 country codes)

(def countries
  (apply hash-set (Locale/getISOCountries)))

(defn countries-contains?
  [c]
  (contains? countries (string/upper-case c)))

(def Country
  (schema/pred countries-contains?))

(def countries-keywords
  (->> (Locale/getISOCountries)
       (map keyword)
       (apply hash-set)))

(defn countries-keywords-contains?
  [ck]
  (contains?
    countries-keywords
    (-> ck
        name
        string/upper-case
        keyword)))

(def Country-Keyword
  (schema/pred countries-keywords-contains?))

;; Date (ISO 8601)

(def date-parser
  (instaparse/parser
    (io/resource "date.abnf")
    :input-format :abnf))

(defn date?
  [d]
  (-> d
      (date-parser :start :date)
      instaparse/failure?
      not))

(def Date
  (schema/pred date?))

(defn iso-date-time?
  [i]
  (-> i
      (date-parser :start :iso-date-time)
      instaparse/failure?
      not))

(def ISO-Date-Time
  (schema/pred iso-date-time?))

(defn time?
  [t]
  (-> t
      (date-parser :start :time)
      instaparse/failure?
      not))

(def Time
  (schema/pred time?))

;; Email

(def email-parser
  (instaparse/parser
    (io/resource "email.abnf")
    :input-format :abnf))

(defn email?
  [e]
  (-> e
      email-parser
      instaparse/failure?
      not))

(def Email
  (schema/pred email?))

;; Language

(def languages
  (apply hash-set (Locale/getISOLanguages)))

(defn languages-contains?
  [l]
  (contains? languages (string/lower-case l)))

(def Language
  (schema/pred languages-contains?))

(def languages-keywords
  (->> (Locale/getISOLanguages)
       (map keyword)
       (apply hash-set)))

(defn languages-keywords-contains?
  [lk]
  (if (keyword? lk)
    (contains?
      languages-keywords
      (-> lk
          name
          string/lower-case
          keyword))
    false))

(def Language-Keyword
  (schema/pred languages-keywords-contains?))

;; URI

(def uri-parser
  (instaparse/parser
    (io/resource "uri.abnf")
    :input-format :abnf))

(defn uri?
  [u]
  (-> u
      uri-parser
      instaparse/failure?
      not))

(def URI
  (schema/pred uri?))

(defn uri-reference?
  [u]
  (-> u
      (uri-parser :start :URI-reference)
      instaparse/failure?
      not))

(def URI-Reference
  (schema/pred uri-reference?))
