(ns advent-of-code.day-17
  (:require [clojure.string :refer [split split-lines includes? join]]))

(defn diagram
  [[x1 x2 y1 y2] pos]
  (let [width (+ x2 10)
        height (+ y2 3)]
    (->> (for [y (range height)]
           (->> (for [x (range width)]
                  (cond 
                    (some #{[x y]} pos) \●
                    (and (< x1 x x2)
                         (< y1 y y2))   \▒
                    :else               \·))
                (apply str))) 
         (join \newline)))) 

(defn part-1
  "Day 17 Part 1"
  [input]
  input)

(defn part-2
  "Day 17 Part 2"
  [input]
  input)
