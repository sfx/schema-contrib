(ns schema-contrib.core
  (:require [clojure.string :as string]
            [instaparse.core :as instaparse]
            [schema.core :as schema]
            [schema-contrib.data :as data])
  #?(:cljs (:require-macros [schema-contrib.resources :as load])
     :clj
           (:require [schema-contrib.resources :as load])))

;; Country (ISO 3166-1 alpha-2 country codes)

(def countries
  (apply hash-set data/countries))

(defn countries-contains?
  [c]
  (contains? countries (string/upper-case c)))

(def Country
  (schema/pred countries-contains? 'Country))

(def countries-keywords
  (->> data/countries
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
  (schema/pred countries-keywords-contains? 'Country-Keyword))

;; Date (ISO 8601)

(def date-parser
  (instaparse/parser
    (load/load-resource "date.abnf")
    :input-format :abnf))

(defn date?
  [d]
  (-> d
      (date-parser :start :date)
      instaparse/failure?
      not))

(def Date
  (schema/pred date? 'Date))

(defn iso-date-time?
  [i]
  (-> i
      (date-parser :start :iso-date-time)
      instaparse/failure?
      not))

(def ISO-Date-Time
  (schema/pred iso-date-time? 'ISO-Date-Time))

(defn time?
  [t]
  (-> t
      (date-parser :start :time)
      instaparse/failure?
      not))

(def Time
  (schema/pred time? 'Time))

;; Email

(def email-parser
  (instaparse/parser
    (load/load-resource "email.abnf")
    :input-format :abnf))

(defn email?
  [e]
  (-> e
      email-parser
      instaparse/failure?
      not))

(def Email
  (schema/pred email? 'Email))

;; Language

(def languages
  (apply hash-set data/languages))

(defn languages-contains?
  [l]
  (contains? languages (string/lower-case l)))

(def Language
  (schema/pred languages-contains? 'Language))

(def languages-keywords
  (->> data/languages
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
  (schema/pred languages-keywords-contains? 'Language-Keyword))

;; URI

(def uri-parser
  (instaparse/parser
    (load/load-resource "uri.abnf")
    :input-format :abnf))

(defn uri?
  [u]
  (-> u
      uri-parser
      instaparse/failure?
      not))

(def URI
  (schema/pred uri? 'URI))

(defn uri-reference?
  [u]
  (-> u
      (uri-parser :start :URI-reference)
      instaparse/failure?
      not))

(def URI-Reference
  (schema/pred uri-reference? 'URI-Reference))
