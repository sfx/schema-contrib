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
  (schema/pred countries-contains? 'Country))

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
  (schema/pred countries-keywords-contains? 'Country-Keyword))

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
    (io/resource "email.abnf")
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
  (apply hash-set (Locale/getISOLanguages)))

(defn languages-contains?
  [l]
  (contains? languages (string/lower-case l)))

(def Language
  (schema/pred languages-contains? 'Language))

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
  (schema/pred languages-keywords-contains? 'Language-Keyword))

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
  (schema/pred uri? 'URI))

(defn uri-reference?
  [u]
  (-> u
      (uri-parser :start :URI-reference)
      instaparse/failure?
      not))

(def URI-Reference
  (schema/pred uri-reference? 'URI-Reference))

(defn non-empty-string? [s]
  (and (string? s)
       (not (empty? s))))

(def NonEmptyStr
  "Any strings other than the empty string, \"\"."
  (schema/pred non-empty-string? 'non-empty-string?))

(defn non-negative-integer?
  [i]
  (and (integer? i)
       (<= 0 i)))

(def NonNegInt
  "Any integer z in Z where z >= 0."
  (schema/pred non-negative-integer? 'non-negative-integer?))

(defn positive-integer?
  [i]
  (and (integer? i)
       (pos? i)))

(def PosInt
  "Any integer z in Z where z > 0."
  (schema/pred positive-integer? 'positive-integer?))

(defn posint-string? [s]
  (and (string? s)
       (try (pos? (Integer/parseInt s))
            (catch Exception _ false))))

(def PosIntStr
  "Any integer z in Z where z > 0, as a string."
  (schema/pred posint-string? 'posint-string?))

(def ^:private alpha-numeric-regex #"[a-zA-Z0-9]+")

(defn string-matches-regex? [regex s]
  (boolean (and (string? s)
                (re-matches regex s))))

(defn alpha-numeric-string? [s]
  (string-matches-regex? alpha-numeric-regex s))

(def AlphaNumericStr
  "Any string containing only letters or digits."
  (schema/pred alpha-numeric-string? 'alpha-numeric-string?))
