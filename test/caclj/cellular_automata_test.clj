(ns caclj.cellular-automata-test
  (:require [clojure.test :refer [deftest is]]
            [clojure.spec.test.alpha :as stest]
            [caclj.cellular-automata :refer [build-rule-map first-generation evolve]]))

(def rule-0
  {[\1 \1 \1] \0
   [\1 \1 \0] \0
   [\1 \0 \1] \0
   [\1 \0 \0] \0
   [\0 \1 \1] \0
   [\0 \1 \0] \0
   [\0 \0 \1] \0
   [\0 \0 \0] \0})

(def rule-1
  {[\1 \1 \1] \0
   [\1 \1 \0] \0
   [\1 \0 \1] \0
   [\1 \0 \0] \0
   [\0 \1 \1] \0
   [\0 \1 \0] \0
   [\0 \0 \1] \0
   [\0 \0 \0] \1})

(def rule-30
  {[\1 \1 \1] \0
   [\1 \1 \0] \0
   [\1 \0 \1] \0
   [\1 \0 \0] \1
   [\0 \1 \1] \1
   [\0 \1 \0] \1
   [\0 \0 \1] \1
   [\0 \0 \0] \0})

(def rule-255
  {[\1 \1 \1] \1
   [\1 \1 \0] \1
   [\1 \0 \1] \1
   [\1 \0 \0] \1
   [\0 \1 \1] \1
   [\0 \1 \0] \1
   [\0 \0 \1] \1
   [\0 \0 \0] \1})

(deftest build-rule-map-test
  (is (thrown? AssertionError (build-rule-map -1)))
  (is (thrown? AssertionError (build-rule-map 256)))
  (is (= (build-rule-map 0) rule-0))
  (is (= (build-rule-map 1) rule-1))
  (is (= (build-rule-map 30) rule-30))
  (is (= (build-rule-map 255) rule-255)))

(deftest first-generation-test
  (is (thrown? AssertionError (first-generation 0)))
  (is (= (first-generation 1)
         [\1 \0]))
  (is (= (first-generation 2)
         [\0 \1 \0 \0]))
  (is (= (first-generation 3)
         [\0 \0 \1 \0 \0 \0]))
  (is (= (first-generation 16)
         [\0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \1 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0 \0])))


(deftest evolve-test
  (is (= (evolve rule-30 [\1 \0])
         [\1 \1]))
  (is (= (evolve rule-30 [\0 \1 \0 \0])
         [\1 \1 \1 \0])))


(deftest build-rule-map-stest
  (is (true?
       (-> (stest/check `build-rule-map {:clojure.spec.test.check/opts {:num-tests 5}})
           first
           :clojure.spec.test.check/ret
           :result))))

(deftest first-generation-stest
  (is (true?
       (-> (stest/check `first-generation {:clojure.spec.test.check/opts {:num-tests 5}})
           first
           :clojure.spec.test.check/ret
           :result))))

(deftest evolve-stest
  (is (true?
       (-> (stest/check `evolve {:clojure.spec.test.check/opts {:num-tests 5}})
           first
           :clojure.spec.test.check/ret
           :result))))

(deftest print-cells-stest
  (is (nil?
       (-> (stest/check `print-cells {:clojure.spec.test.check/opts {:num-tests 5}})
           first
           :clojure.spec.test.check/ret
           :result))))
