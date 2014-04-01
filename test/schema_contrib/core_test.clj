(ns schema-contrib.core-test
  (:import (clojure.lang ExceptionInfo)
           (java.util Locale))
  (:require [clojure.string :as string]
            [clojure.test :refer :all]
            [clojure.test.check.generators :as gen]
            [schema.core :as schema]
            [schema-contrib.core :refer :all]))

;; Country

(def country-gen
  (gen/elements (Locale/getISOCountries)))

(deftest country-test
  (testing "Generated countries pass validation."
    (->> (gen/sample country-gen 100)
         (map #(is (= % (schema/validate Country %))))
         (doall)))
  (is (thrown? ExceptionInfo (schema/validate Country "asdf")))
  (is (thrown? ExceptionInfo (schema/validate Country "")))
  (is (thrown? ExceptionInfo (schema/validate Country :en))))

;; Country-Keyword

(def country-keyword-gen
  (->> (Locale/getISOCountries)
       (map keyword)
       gen/elements))

(deftest country-keyword-test
  (testing "Generated country keywords pass validation."
    (->> (gen/sample country-keyword-gen 100)
         (map #(is (= % (schema/validate Country-Keyword %))))
         (doall)))
  (is (thrown? ExceptionInfo (schema/validate Country-Keyword :asdf)))
  (is (thrown? ExceptionInfo (schema/validate Country-Keyword "en"))))

;; Email

; http://git.io/-cB3Fg
(def email-gen
  (gen/fmap (fn [[name domain tld]]
              (if-not (string/blank? domain) ;; Support TLDs with no domain.
                (str name "@" domain "." tld)
                (str name "@" tld)))
            (gen/tuple
              (gen/not-empty gen/string-alpha-numeric)
              gen/string-alpha-numeric
              (gen/elements ["biz" "com" "io" "net" "org"]))))

(defn- valid-email
  [email]
  (= email (schema/validate Email email)))

(deftest email-test
  (testing "Generated email addresses pass validation."
    (->> (gen/sample email-gen 100)
         (map #(is (valid-email %)))
         (doall)))
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
    (is (thrown? ExceptionInfo (schema/validate Email "Abc.example.com")))
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

(def language-gen
  (gen/elements (Locale/getISOLanguages)))

(deftest language-test
  (testing "Generated languages pass validation."
    (->> (gen/sample language-gen 100)
         (map #(is (= % (schema/validate Language %))))
         (doall)))
  (is (thrown? ExceptionInfo (schema/validate Language "asdf")))
  (is (thrown? ExceptionInfo (schema/validate Language "")))
  (is (thrown? ExceptionInfo (schema/validate Language :en))))

;; Language-Keyword

(def language-keyword-gen
  (->> (Locale/getISOLanguages)
       (map keyword)
       gen/elements))

(deftest language-keyword-test
  (testing "Generated languages pass validation."
    (->> (gen/sample language-keyword-gen 100)
         (map #(is (= % (schema/validate Language-Keyword %))))
         (doall)))
  (is (thrown? ExceptionInfo (schema/validate Language-Keyword :asdf)))
  (is (thrown? ExceptionInfo (schema/validate Language-Keyword "en"))))

;; Path

(def path-gen
  (gen/fmap (fn [[path1 path2 path3 path4]]
              (cond (string/blank? path1) path1
                    (string/blank? path2) path1
                    (string/blank? path3) (str path1 "/" path2)
                    (string/blank? path4) (str path1 "/" path2 "/" path3)
                    :else (str path1 "/" path2 "/" path3 "/" path4)))
            (gen/tuple
              gen/string-alpha-numeric
              gen/string-alpha-numeric
              gen/string-alpha-numeric
              gen/string-alpha-numeric)))

(deftest path-test
  (testing "Generated paths pass validation."
    (->> (gen/sample path-gen 100)
         (map #(is (= % (schema/validate Path %))))
         (doall))))

;; URI

(def uri-gen
  (gen/fmap (fn [[protocol subdomain domain tld path]]
              (str protocol "://"
                   (when-not (string/blank? subdomain) (str subdomain "."))
                   domain "." tld "/" path))
            (gen/tuple
              (gen/elements ["ftp" "http" "https"])
              gen/string-alpha-numeric
              (gen/not-empty gen/string-alpha-numeric)
              (gen/elements ["biz" "com" "io" "net" "org"])
              path-gen)))

(deftest uri-test
  (testing "Generated URIs pass validation."
    (->> (gen/sample uri-gen 100)
         (map #(is (= % (schema/validate URI %))))
         (doall)))
  (is (thrown? ExceptionInfo (schema/validate URI "asdf"))))
