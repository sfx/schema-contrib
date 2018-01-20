(ns schema-contrib.test-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [schema-contrib.core-test]))

(doo-tests 'schema-contrib.core-test)
