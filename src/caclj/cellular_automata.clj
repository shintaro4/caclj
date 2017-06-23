(ns caclj.cellular-automata
  (:require [clojure.string :as string]
            [clojure.spec.alpha :as s]))

(def RULE-SEQUENCE-SIZE 8)
(def INITIAL-CELLS [true])

(s/def :caclj/rule (s/coll-of boolean? :kind vector? :count RULE-SEQUENCE-SIZE))
(s/def :caclj/cells (s/coll-of boolean? :kind vector? :min-count 1))

(defn init-rule
  "Returns a boolean sequence that expresses the rule number."
  [number]
  (let [bin-str (Integer/toBinaryString number)
        pad (repeat (- RULE-SEQUENCE-SIZE (count bin-str)) \0)]
    (mapv #(if (= \1 %) true false) (reverse (concat pad bin-str)))))

(s/fdef init-rule
        :args (s/and (s/cat :number int?) #(<= 0 (:number %) 255))
        :ret :caclj/rule)

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

(s/fdef next-gen
        :args (s/cat :rule :caclj/rule :cells :caclj/cells)
        :ret :caclj/cells)

(defn print-cells
  "Prints cells nicely."
  [cells t]
  (let [pad (string/join "" (repeat t " "))
        s (string/join "" (map #(if (true? %) "o" " ") cells))]
    (println (str pad s))))

(s/fdef print-cells
        :args (s/and (s/cat :cells :caclj/cells :t int?) #(< 0 (:t %)))
        :ret nil)
