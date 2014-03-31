(ns schema-contrib.core
  (:require [clojure.java.io :as io]
            [instaparse.core :as instaparse]
            [schema.core :as schema]))

(def ^{:private true} email-address-parser
  (instaparse/parser
    (io/resource "email_address.abnf")
    :input-format :abnf))

(defn- email-address?
  [e]
  (let [parse-result (email-address-parser e)]
    (if-not (instaparse/failure? parse-result)
      true
      false)))

(def Email-Address
  (schema/pred email-address?))
