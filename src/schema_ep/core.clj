(ns schema-ep.core
  (:import [java.util Locale])
  (:require [clojure.string :as string]
            [schema.core :as schema]))

;; Does using enum instead of partial provide better errors?
;; Must have cross-platform tests.
;; Handle case.

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
  (map keyword countries))

(defn- countries-keywords-contains?
  [ck]
  (contains? countries-keywords ck))

(def Country-Keyword
  (schema/pred countries-keywords-contains?))

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
  (map keyword languages))

(defn- languages-keywords-contains?
  [lk]
  (contains? languages-keywords lk))

(def Language-Keyword
  (schema/pred languages-keywords-contains?))

(def URI)

(def URL)

;; TODO: Area code?
;; TODO: Locale as a whole?
;; TODO: Phone number?
;; TODO: State?
;; TODO: Zip code?
