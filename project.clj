(defproject schema-contrib "0.1.5"
  :description "Additional validators for Prismatic's Schema."
  :url "https://github.com/sfx/schema-contrib"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[instaparse "1.3.5"]
                 [org.clojure/clojure "1.6.0"]
                 [org.clojure/test.check "0.7.0"]
                 [prismatic/schema "0.3.7"]]
  :resource-paths ["resources"])
