(ns caclj.cellular-automata
  (:require [clojure.string :as string]
            [clojure.tools.cli :refer [parse-opts]]))


(def RULE-BIT-MASK 255)
(def RULE-SEQUENCE-SIZE 8)
(def INITIAL-CELLS [true])


(defn init-rule
  "Returns a boolean sequence that expresses the rule number."
  [number]
  (assert (<= 0 number RULE-BIT-MASK))
  (let [bin-str (Integer/toBinaryString (bit-and number RULE-BIT-MASK))
        pad (repeat (- RULE-SEQUENCE-SIZE (count bin-str)) \0)]
    (mapv #(if (= \1 %) true false) (reverse (concat pad bin-str)))))


(defn next-gen
  "Returns the next generations."
  [rule cells]
  (let [len (count cells)
        fun #(let [_p (dec %)
                   _n (inc %)
                   l (if (and (<= 0 _p) (< _p len) (get cells (mod _p len))) 4 0)
                   c (if (and (<= 0 %) (< % len) (get cells %)) 2 0)
                   r (if (and (<= 0 _n) (< _n len) (get cells (mod _n len))) 1 0)]
               (get rule (+ l c r)))]
    (mapv fun (range -1 (+ len 1)))))


(defn print-cells
  "Prints cells nicely."
  [cells t]
  (let [pad (string/join "" (repeat t " "))
        s (string/join "" (map #(if (true? %) "o" " ") cells))]
    (println (str pad s))))
