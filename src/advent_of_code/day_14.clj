(ns advent-of-code.day-14
  "AOC 2021 Day 14"
  (:require [clojure.string :refer [split split-lines]]))

(defn parse-input
  [input]
  (let [[template rules] (split input #"\n\n")] 
    {:template template
     :rules (reduce (fn [r l]
                      (let [[a b _ _ _ _ c] (seq l)] 
                        (assoc r (list a b) (list c b)))) 
                    {}
                    (split-lines rules))}))

(defn step
  [{:keys [template rules] :as args}]
  (let [head (nth template 0)
        new-template (mapcat (fn [p] (get rules p p))
                             (partition 2 1 template))]   
    (assoc args :template (conj new-template head))))

(defn polymerize 
  [input]
  (iterate step (parse-input input)))

(defn part-1
  "Day 14 Part 1"
  [input]
  (let [occurences (->> input
                        polymerize
                        (take 13)
                        last
                        :template
                        frequencies
                        vals)]
    (- (apply max occurences)
       (apply min occurences))))

(defn part-2
  "Day 14 Part 2"
  [input]
  (let [occurences (->> input
                        polymerize
                        (take 41)
                        last
                        :template
                        frequencies
                        vals)]
    (- (apply max occurences)
       (apply min occurences))))
