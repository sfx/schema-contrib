(ns schema-contrib.core-test
  (:require [clojure.string :as string]
            [clojure.test.check.generators :as gen]
            [schema.core :as schema]
            [clojure.test :refer [deftest is testing]]
            [schema-contrib.core :refer [Country
                                         Country-Keyword
                                         Date
                                         Email
                                         Language-Keyword
                                         Language
                                         ISO-Date-Time
                                         Time
                                         URI
                                         URI-Reference]]
            [schema-contrib.gen :as scgen]))

(defn valid
  [s v]
  (= nil (schema/check s v)))

(defn invalid
  [s v]
  (not (valid s v)))

(deftest country-test
  (testing "Generated countries pass validation."
    (->> (gen/sample scgen/country 100)
         (map #(is (valid Country %)))
         (doall)))
  (is (invalid Country "asdf"))
  (is (invalid Country ""))
  (is (invalid Country :en)))

(deftest country-keyword-test
  (testing "Generated country keywords pass validation."
    (->> (gen/sample scgen/country-keyword 100)
         (map #(is (valid Country-Keyword %)))
         (doall)))
  (is (invalid Country-Keyword :asdf))
  (is (invalid Country-Keyword "en")))

(deftest date-test
  (is (valid Date "2014-04-01"))
  (is (valid Date "2014-W14"))
  (is (valid Date "2014-W14-2"))
  (is (valid Date "2014-091"))
  (is (invalid Date "2014-04-01T20:17:35+00:00"))
  (is (invalid Date "2014-04-01T20:17:35Z")))

(deftest email-test
  (testing "http://en.wikipedia.org/wiki/Email_address"
    (is (valid Email "niceandsimple@example.com"))
    (is (valid Email "very.common@example.com"))
    (is (valid Email "a.little.lengthy.but.fine@dept.example.com"))
    (is (valid Email "disposable.style.email.with+symbol@example.com"))
    (is (valid Email "other.email-with-dash@example.com"))
    (is (valid Email "user@[IPv6:2001:db8:1ff::a0b:dbd0]"))
    (is (valid Email "\"much.more unusual\"@example.com"))
    (is (valid Email "\"very.unusual.@.unusual.com\"@example.com"))
    (is (valid Email (str "\"very.(),:;<>[]\\\".VERY.\\\"very@\\\\ \\\"very\\\\"
                          ".unusual\"@strange.example.com")))
    (is (valid Email "postbox@com"))
    (is (valid Email "admin@mailserver1"))
    (is (valid Email "!#$%&'*+-/=?^_`{}|~@example.org"))
    (is (valid Email "\"()<>[]:,;@\\\"!#$%&'*+-/=?^_`{}| ~.a\"@example.org"))
    (is (valid Email "\" \"@example.org"))
    (is (valid Email "üñîçøðé@example.com"))
    (is (valid Email "üñîçøðé@üñîçøðé.com"))
    (is (valid Email "Pelé@example.com"))
    (is (valid Email "δοκιμή@παράδειγμα.δοκιμή"))
    (is (valid Email "甲斐@黒川.日本"))
    (is (valid Email "чебурашка@ящик-с-апельсинами.рф"))
    (is (invalid Email "Abc.example.com"))
    (is (invalid Email "A@b@c@example.com"))
    (is (invalid Email "a\"b(c)d,e:f;g<h>i[j\\k]l@example.com"))
    (is (invalid Email "just\"not\"right@example.com"))
    (is (invalid Email "this is\"not\\allowed@example.com"))
    (is (invalid Email "this\\ still\\\"not\\\\allowed@example.com"))))

(deftest language-test
  (testing "Generated languages pass validation."
    (->> (gen/sample scgen/language 100)
         (map #(is (valid Language %)))
         (doall)))
  (is (invalid Language "asdf"))
  (is (invalid Language ""))
  (is (invalid Language :en)))

(deftest language-keyword-test
  (testing "Generated languages pass validation."
    (->> (gen/sample scgen/language-keyword 100)
         (map #(is (valid Language-Keyword %)))
         (doall)))
  (is (invalid Language-Keyword :asdf))
  (is (invalid Language-Keyword "en")))

(deftest iso-date-time-test
  (is (valid ISO-Date-Time "2014-04-01T22:17:35+02:00"))
  (is (valid ISO-Date-Time "2014-04-01T20:17:35Z"))
  (is (valid ISO-Date-Time "2007-04-05T14:59"))
  (is (valid ISO-Date-Time "2007-04-05T14:30Z"))
  (is (valid ISO-Date-Time "2007-04-05T12:30-02:00"))

  (is (valid ISO-Date-Time "2014-04-01 20:17:35+00:00"))
  (is (valid ISO-Date-Time "2014-04-01 21:17:35Z"))
  (is (valid ISO-Date-Time "2007-04-05 14:30"))
  (is (valid ISO-Date-Time "2007-04-05 14:30Z"))
  (is (valid ISO-Date-Time "2007-04-05 12:30-02:00"))

  (is (not (valid ISO-Date-Time "2007-13-05T12:30-02:00")))
  (is (not (valid ISO-Date-Time "2007-12-45T12:30-02:00")))
  (is (not (valid ISO-Date-Time "2007-01-05T25:30-02:00")))
  (is (not (valid ISO-Date-Time "2007-01-05T12:61-02:00")))
  (is (not (valid ISO-Date-Time "2007-01-05T12:30-25:00")))
  (is (not (valid ISO-Date-Time "2007-01-05T12:30-02:61")))

  (is (not (valid ISO-Date-Time "2007-13-05 12:30-02:00")))
  (is (not (valid ISO-Date-Time "2007-12-45 12:30-02:00")))
  (is (not (valid ISO-Date-Time "2007-01-05 25:30-02:00")))
  (is (not (valid ISO-Date-Time "2007-01-05 12:61-02:00")))
  (is (not (valid ISO-Date-Time "2007-01-05 12:30-25:00")))
  (is (not (valid ISO-Date-Time "2007-01-05 12:30-02:61")))


  (is (invalid ISO-Date-Time "2014-04-01"))
  (is (invalid ISO-Date-Time "2014-W14"))
  (is (invalid ISO-Date-Time "2014-W14-2"))
  (is (invalid ISO-Date-Time "2014-091")))

(deftest time-test
  (is (valid Time "134730"))
  (is (valid Time "13:47:30"))
  (is (valid Time "00:00"))
  (is (valid Time "24:00"))
  (is (valid Time "14:30,5"))
  (is (valid Time "1430,5"))
  (is (valid Time "14:30.5"))
  (is (valid Time "1430.5")))

;; http://en.wikipedia.org/wiki/URI#Examples_of_URI_references
(def absolute-uris
  ["http://example.org/absolute/URI/with/absolute/path/to/resource.txt"
   "ftp://example.org/resource.txt"
   "urn:issn:1535-3613"
   "http://en.wikipedia.org/wiki/URI#Examples_of_URI_references"])

(def uri-references
  ["//example.org/scheme-relative/URI/with/absolute/path/to/resource.txt"
   "/relative/URI/with/absolute/path/to/resource.txt"
   "relative/path/to/resource.txt"
   "../../../resource.txt"
   "./resource.txt#frag01"
   "resource.txt"
   "#frag01"
   ""])

(deftest uri-test
  (doall (map #(is (valid URI %)) absolute-uris))
  (doall (map #(is (invalid URI %)) uri-references)))

(deftest uri-reference-test
  (doall (map #(is (valid URI-Reference %)) absolute-uris))
  (doall (map #(is (valid URI-Reference %)) uri-references)))
