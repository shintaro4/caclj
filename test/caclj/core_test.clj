(ns caclj.core-test
  (:require [clojure.test :refer :all]
            [caclj.core :refer :all]))


(deftest test-init-rule
  (is (= (init-rule 0)
         [false false false false false false false false]))
  (is (= (init-rule 1)
         [true false false false false false false false]))
  (is (= (init-rule 30)
         [false true true true true false false false]))
  (is (= (init-rule 255)
         [true true true true true true true true]))
  (is (thrown? java.lang.AssertionError (init-rule -1)))
  (is (thrown? java.lang.AssertionError (init-rule 256))))


(deftest test-next-gen
  (is (= (next-gen (init-rule 30) [true])
         [true true true])))
