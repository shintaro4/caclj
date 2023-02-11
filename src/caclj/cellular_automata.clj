(ns caclj.cellular-automata
  (:require [clojure.spec.alpha :as s]))

(def RULE-SEQUENCE-SIZE 8)
(def CELL-CHARACTER-MAP {\0 \  \1 \o})

(def cell? #{\1 \0})

(s/def :caclj/rule-map-key (s/coll-of cell? :kind vector? :count 3))
(s/def :caclj/rule-map (s/map-of :caclj/rule-map-key cell? :count RULE-SEQUENCE-SIZE))
(s/def :caclj/cells (s/coll-of cell? :kind vector? :min-count 2))
(s/def :caclj/char-map (s/map-of cell? char? :count 2))

(defn build-rule-map
  "Returns the rule map of the number."
  [number]
  (assert (<= 0 number 255) "invalid number")
  (let [_keyfn   #(vec (take-last 3 (concat (repeat 3 \0) (Integer/toBinaryString %))))
        keys     (map _keyfn (reverse (range RULE-SEQUENCE-SIZE)))
        _bin-str (Integer/toBinaryString number)
        _pad     (repeat (- RULE-SEQUENCE-SIZE (count _bin-str)) \0)
        values   (concat _pad _bin-str)]
    (zipmap keys values)))

(s/fdef build-rule-map
  :args (s/and (s/cat :number int?) #(<= 0 (:number %) 255))
  :ret :caclj/rule-map)

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

(defn convert-characters
  "Convert cell characters"
  [char-map cells]
  (apply str (map char-map cells)))

(s/fdef convert-characters
  :args (s/cat :char-map :caclj/char-map :cells :caclj/cells)
  :ret string?)
