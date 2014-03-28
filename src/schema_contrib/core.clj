(ns schema-contrib.core
  (:import [java.util Locale])
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [instaparse.core :as instaparse]
            [schema.core :as schema]))

;; TODO: Update to Clojure 1.6.0.
;; TODO: Case sensitive instaparsing? See instaparse tests.

;; ISO 3166-1 alpha-2 country codes
(def ^{:private true} countries
  (->> (Locale/getISOCountries)
       (map string/lower-case)
       (apply hash-set)))

(defn- countries-contains?
  [c]
  (contains? countries c))

(def Country
  (schema/pred countries-contains?))

(def ^{:private true} countries-keywords
  (->> (Locale/getISOCountries)
       (map string/lower-case)
       (map keyword)
       (apply hash-set)))

(defn- countries-keywords-contains?
  [ck]
  (contains? countries-keywords ck))

(def Country-Keyword
  (schema/pred countries-keywords-contains?))

(def email-address-parser
  (instaparse/parser
    (io/resource "email_address.abnf")
    :input-format :abnf))

(defn email-address?
  [e]
  (let [parse-result (email-address-parser e)]
    (if-not (instaparse/failure? parse-result)
      true
      false)))

(def Email-Address
  (schema/pred email-address?))

(def ^{:private true} languages
  (->> (Locale/getISOLanguages)
       (map string/lower-case)
       (apply hash-set)))

(defn- languages-contains?
  [l]
  (contains? languages l))

(def Language
  (schema/pred languages-contains?))

(def ^{:private true} languages-keywords
  (->> (Locale/getISOLanguages)
       (map string/lower-case)
       (map keyword)
       (apply hash-set)))

(defn- languages-keywords-contains?
  [lk]
  (contains? languages-keywords lk))

(def Language-Keyword
  (schema/pred languages-keywords-contains?))

(def uri-parser
  (instaparse/parser
    (io/resource "uri.abnf")
    :input-format :abnf))

(defn uri?
  [u]
  (let [parse-result (uri-parser u)]
    (if-not (instaparse/failure? parse-result)
      true
      false)))

(def URI
  (schema/pred uri?))
