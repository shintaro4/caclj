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

(deftest next-gen-test
  (is (= (next-gen (init-rule 30) [true])
         [true true true])))

(deftest init-rule-stest
  (is (true?
       (-> (stest/check `init-rule {:clojure.spec.test.check/opts {:num-tests 50}})
           first
           :clojure.spec.test.check/ret
           :result))))

(deftest next-gen-stest
  (is (true?
       (-> (stest/check `next-gen {:clojure.spec.test.check/opts {:num-tests 10}})
           first
           :clojure.spec.test.check/ret
           :result))))

(deftest print-cells-stest
  (is (true?
       (-> (stest/check `print-cells {:clojure.spec.test.check/opts {:num-tests 5}})
           first
           :clojure.spec.test.check/ret
           :result))))
