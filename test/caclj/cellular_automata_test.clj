(ns caclj.cellular-automata-test
  (:require [clojure.test :refer :all]
            [clojure.spec.test.alpha :as stest]
            [caclj.cellular-automata :refer :all]))

(deftest init-rule-test
  (is (= (init-rule 0)
         [false false false false false false false false]))
  (is (= (init-rule 1)
         [true false false false false false false false]))
  (is (= (init-rule 30)
         [false true true true true false false false]))
  (is (= (init-rule 255)
         [true true true true true true true true]))

  (is (thrown? AssertionError (init-rule -1)))
  (is (thrown? AssertionError (init-rule 256))))

(deftest init-rule-stest
   (is (true?
        (-> (stest/check `init-rule)
            first
            :clojure.spec.test.check/ret
            :result))))

(deftest next-gen-test
  (is (= (next-gen (init-rule 30) [true])
         [true true true])))
