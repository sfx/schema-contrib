(defproject io.bytebeam/schema-contrib "0.2.0"
  :description "Additional validators for Prismatic's Schema."
  :url "https://github.com/sfx/schema-contrib"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[instaparse "1.4.10"]
                 [org.clojure/clojure "1.10.3"]
                 [org.clojure/test.check "1.1.0"]
                 [prismatic/schema "1.1.12"]]
  :resource-paths ["resources"])
