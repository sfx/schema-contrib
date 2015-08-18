(defproject schema-contrib "0.1.5"
  :description "Additional validators for Prismatic's Schema."
  :url "https://github.com/sfx/schema-contrib"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[instaparse "1.4.1"]
                 [org.clojure/clojure "1.7.0"]
                 [org.clojure/test.check "0.8.0"]
                 [prismatic/schema "0.4.3"]]
  :resource-paths ["resources"])
