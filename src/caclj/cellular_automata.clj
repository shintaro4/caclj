(ns caclj.cellular-automata
  (:require [clojure.string :as string]
            [clojure.spec.alpha :as s]))

(def RULE-SEQUENCE-SIZE 8)

(def cell? #{\1 \0})
(s/def :caclj/rule-map-key (s/coll-of cell? :kind vector? :count 3))
(s/def :caclj/rule-map (s/every-kv :caclj/rule-map-key cell? :count RULE-SEQUENCE-SIZE))
(defn build-rule-map
  "Returns the rule map of the number."
  [number]
  (assert (<= 0 number 255) "invalid number")
  (let [_keyfn #(vec (take-last 3 (concat (repeat 3 \0) (Integer/toBinaryString %))))
        keys (map _keyfn (reverse (range RULE-SEQUENCE-SIZE)))
        _bin-str (Integer/toBinaryString number)
        _pad (repeat (- RULE-SEQUENCE-SIZE (count _bin-str)) \0)
        values (concat _pad _bin-str)]
      (zipmap keys values)))

(s/fdef build-rule-map
        :args (s/and (s/cat :number int?) #(<= 0 (:number %) 255))
        :ret :caclj/rule-map)


(s/def :caclj/cells (s/coll-of cell? :kind vector? :min-count 2))
(defn first-generation
  "returns the cells of the first generation."
  [times]
  (assert (<= 1 times) "invalid times")
  (let [t (* times 2)]
    (assoc (vec (repeat t \0)) (quot (dec t) 2) \1)))

(s/fdef first-generation
  :args (s/and (s/cat :times int?) #(<= 1 (:times %)))
  :ret :caclj/cells)


(defn evolve
  "Returns the next generation"
  [rule-map cells]
  (mapv rule-map (partition 3 1 (repeat \0) (cons \0 cells))))

(s/fdef evolve
  :args (s/cat :rule-map :caclj/rule-map :cells :caclj/cells)
  :ret :caclj/cells)


(defn print-cells
  "Prints cells nicely."
  [cells]
  (let [t (count cells)]
    (loop [i 0]
      (println (apply str (nth cells i)))
      (when (< (inc i) t) (recur (inc i))))))

(s/fdef print-cells
        :args (s/cat :cells :caclj/cells)
        :ret nil)
