(ns advent-of-code.day-18
  "AOC 2021 Day 18"
  (:require [clojure.zip :as zip]))

(defn find-loc [loc pred]
  (cond  
    (zip/end? loc) nil
    (pred loc)     loc
    :else          (recur (zip/next loc) pred)))

(defn splitter? [loc]
  (let [node (zip/node loc)]
    (and (number? node) (>= (node 10)))))

(defn exploder? [loc]
  (and (coll? (zip/node loc)) (= 4 (count (zip/path loc))))

(defn part-1
  "Day 18 Part 1"
  [input]
  input)

(defn part-2
  "Day 18 Part 2"
  [input]
  input)
