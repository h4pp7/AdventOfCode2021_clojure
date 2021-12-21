(ns advent-of-code.day-21
  (:require [advent-of-code.util :refer [mod-1]]))

(defn parse-input [input]
  (let [[_ p-1 _ p-2] (re-seq #"\d" input)]
    (map clojure.edn/read-string [p-1 p-2])))

(defn deterministic-dice [pos-1 score-1 pos-2 score-2 i]
  (if (>= score-2 1000)
    (* score-1 i)
    (let [new-pos-1 (mod-1 (+ pos-1 (+ 6 (* 3 i))) 10)]
      (recur pos-2 score-2 new-pos-1 (+ score-1 new-pos-1) (+ i 3)))))

(defn part-1
  "Day 21 Part 1"
  [input]
  (let [[pos-1 pos-2] (parse-input input)]
    (deterministic-dice pos-1 0 pos-2 0 0)))

(defn part-2
  "Day 21 Part 2"
  [input]
  input)
