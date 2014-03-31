(ns schema-contrib.core-test
  (:import (clojure.lang ExceptionInfo))
  (:require [clojure.test :refer :all]
            [schema.core :as schema]
            [schema-contrib.core :refer :all]))

(deftest country-test
  (is (= "ca" (schema/validate Country "ca")))
  (is (= "de" (schema/validate Country "de")))
  (is (= "us" (schema/validate Country "us")))
  (is (thrown? ExceptionInfo (schema/validate Country "asdf"))))

(deftest country-keyword-test
  (is (= :ca (schema/validate Country-Keyword :ca)))
  (is (= :de (schema/validate Country-Keyword :de)))
  (is (= :us (schema/validate Country-Keyword :us)))
  (is (thrown? ExceptionInfo (schema/validate Country-Keyword :asdf))))

(deftest email-address-test
  (is (= "bowmanb@gmail.com"
         (schema/validate Email-Address "bowmanb@gmail.com")))
  (is (thrown? ExceptionInfo (schema/validate Email-Address "asdf"))))

(deftest language-test
  (is (= "en" (schema/validate Language "en")))
  (is (= "es" (schema/validate Language "es")))
  (is (= "fr" (schema/validate Language "fr")))
  (is (thrown? ExceptionInfo (schema/validate Language "asdf"))))

(deftest language-keyword-test
  (is (= :en (schema/validate Language-Keyword :en)))
  (is (= :es (schema/validate Language-Keyword :es)))
  (is (= :fr (schema/validate Language-Keyword :fr)))
  (is (thrown? ExceptionInfo (schema/validate Language-Keyword :asdf))))

(deftest uri-test
  (is (= "http://www.sfxii.com"
         (schema/validate URI "http://www.sfxii.com")))
  (is (thrown? ExceptionInfo (schema/validate URI "asdf"))))

(deftest path-test
  (is (= "/this/is/a/path" (schema/validate Path "/this/is/a/path"))))
