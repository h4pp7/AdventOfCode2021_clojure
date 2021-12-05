(ns advent-of-code.day-05
  "AOC 2021 Day 5"
  (:require [clojure.string :refer [split split-lines]]))

(defn parse-numbers
  [line]
  (map #(clojure.edn/read-string %) (re-seq #"\d+" line)))

(defn diagonal?
  [line]
  (let [[x1 y1 x2 y2] (parse-numbers line)]
    (not (or (= x1 x2) (= y1 y2)))))

(defn direction
  [a b]
  (cond (< a b)  1
        (> a b) -1
        :else    0))

(defn parse-line
  [line]
  (let [[x1 y1 x2 y2] (parse-numbers line)
        x-dir (direction x1 x2)
        y-dir (direction y1 y2)
        nxt (fn [[x y]] [(+ x x-dir) (+ y y-dir)])] 
    (conj (->> (iterate nxt [x1 y1]) 
               (take-while #(not= % [x2 y2])))
          [x2 y2])))

(defn solve
  [lines]
  (->> lines 
       (mapcat parse-line)
       frequencies
       (remove (fn [[idx freq]] (< freq 2))) 
       count))

(defn part-1
  "Day 05 Part 1"
  [input]
  (->> input
       split-lines
       (remove diagonal?)
       solve))

(defn part-2
  "Day 05 Part 2"
  [input]
  (->> input
       split-lines
       solve))
