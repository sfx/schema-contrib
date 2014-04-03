(ns schema-contrib.core-test
  (:import (clojure.lang ExceptionInfo))
  (:require [clojure.string :as string]
            [clojure.test :refer :all]
            [clojure.test.check.generators :as gen]
            [schema.core :as schema]
            [schema-contrib.core :refer :all]
            [schema-contrib.gen :refer :all]))

;; Country

(deftest country-test
  (testing "Generated countries pass validation."
    (->> (gen/sample country-gen 100)
         (map #(is (= % (schema/validate Country %))))
         (doall)))
  (is (thrown? ExceptionInfo (schema/validate Country "asdf")))
  (is (thrown? ExceptionInfo (schema/validate Country "")))
  (is (thrown? ExceptionInfo (schema/validate Country :en))))

;; Country-Keyword

(deftest country-keyword-test
  (testing "Generated country keywords pass validation."
    (->> (gen/sample country-keyword-gen 100)
         (map #(is (= % (schema/validate Country-Keyword %))))
         (doall)))
  (is (thrown? ExceptionInfo (schema/validate Country-Keyword :asdf)))
  (is (thrown? ExceptionInfo (schema/validate Country-Keyword "en"))))

;; Email

(defn invalid-email)

(defn valid-email
  [email]
  (= email (schema/validate Email email)))

(deftest email-test
  (testing "Wikipedia email addresses validate as advertised."
    ;; http://en.wikipedia.org/wiki/Email_address#Examples
    (is (valid-email "niceandsimple@example.com"))
    (is (valid-email "very.common@example.com"))
    (is (valid-email "a.little.lengthy.but.fine@dept.example.com"))
    (is (valid-email "disposable.style.email.with+symbol@example.com"))
    (is (valid-email "other.email-with-dash@example.com"))
    (is (valid-email "user@[IPv6:2001:db8:1ff::a0b:dbd0]"))
    (is (valid-email "\"much.more unusual\"@example.com"))
    (is (valid-email "\"very.unusual.@.unusual.com\"@example.com"))
    (is (valid-email (str "\"very.(),:;<>[]\\\".VERY.\\\"very@\\\\ \\\"very\\\\"
                          ".unusual\"@strange.example.com")))
    (is (valid-email "postbox@com"))
    (is (valid-email "admin@mailserver1"))
    (is (valid-email "!#$%&'*+-/=?^_`{}|~@example.org"))
    (is (valid-email "\"()<>[]:,;@\\\"!#$%&'*+-/=?^_`{}| ~.a\"@example.org"))
    (is (valid-email "\" \"@example.org"))
    ;(is (valid "üñîçøðé@example.com"))
    ;(is (valid "üñîçøðé@üñîçøðé.com"))
    (is (invalid-email ExceptionInfo (schema/validate Email "Abc.example.com")))
    (is (thrown? ExceptionInfo (schema/validate Email "A@b@c@example.com")))
    (is (thrown?
          ExceptionInfo
          (schema/validate Email "a\"b(c)d,e:f;g<h>i[j\\k]l@example.com")))
    (is (thrown?
          ExceptionInfo
          (schema/validate Email "just\"not\"right@example.com")))
    (is (thrown?
          ExceptionInfo
          (schema/validate Email "this is\"not\\allowed@example.com")))
    (is (thrown?
          ExceptionInfo
          (schema/validate Email "this\\ still\\\"not\\\\allowed@example.com")))
    ;(is (thrown? ExceptionInfo (schema/validate Email "email@brazil.b")))
    ))

;; Language

(deftest language-test
  (testing "Generated languages pass validation."
    (->> (gen/sample language-gen 100)
         (map #(is (= % (schema/validate Language %))))
         (doall)))
  (is (thrown? ExceptionInfo (schema/validate Language "asdf")))
  (is (thrown? ExceptionInfo (schema/validate Language "")))
  (is (thrown? ExceptionInfo (schema/validate Language :en))))

;; Language-Keyword

(deftest language-keyword-test
  (testing "Generated languages pass validation."
    (->> (gen/sample language-keyword-gen 100)
         (map #(is (= % (schema/validate Language-Keyword %))))
         (doall)))
  (is (thrown? ExceptionInfo (schema/validate Language-Keyword :asdf)))
  (is (thrown? ExceptionInfo (schema/validate Language-Keyword "en"))))

;; Path

(deftest path-test
  (testing "Generated paths pass validation."
    (->> (gen/sample path-gen 100)
         (map #(is (= % (schema/validate Path %))))
         (doall))))

;; URI

(deftest uri-test
  (testing "Generated URIs pass validation."
    (->> (gen/sample uri-gen 100)
         (map #(is (= % (schema/validate URI %))))
         (doall)))
  (is (thrown? ExceptionInfo (schema/validate URI "asdf"))))
