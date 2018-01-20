(defproject schema-contrib "0.1.6-SNAPSHOT"
  :description "Additional validators for Prismatic's Schema."
  :url "https://github.com/sfx/schema-contrib"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[instaparse "1.4.8"]
                 [org.clojure/clojure "1.9.0"]
                 [org.clojure/test.check "0.9.0"]
                 [prismatic/schema "1.1.7"]
                 [org.clojure/clojurescript "1.9.946"]]
  :plugins [[lein-doo "0.1.8"]
            [lein-cljsbuild "1.1.7" :exclusions [[org.clojure/clojure]]]]
  :doo {:build "test"}
  :cljsbuild
  {:builds [{:id           "test"
             :source-paths ["src" "test"]
             :compiler     {:output-to     "tmp/testable.js"
                            :main          schema-contrib.test-runner
                            :optimizations :none}}]}
  :resource-paths ["resources"])
