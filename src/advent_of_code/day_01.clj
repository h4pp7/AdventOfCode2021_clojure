(ns advent-of-code.day-01
  "AOC 2021 Day 1"
  (:require [clojure.string :refer [split-lines]]))

(defn parse-input
  [input]
  (->> input
       split-lines
       (map clojure.edn/read-string)))

(defn windows
  [size measurements]
  (partition size 1 measurements))  

(defn window-sum
  [window]
  (reduce + window))

(defn increased?
  [steps]
  (apply < steps))

(defn count-increases
  [steps]
  (->> steps
       (windows 2)
       (filter increased?)
       count))

(defn part-1
  "Day 01 Part 1"
  [input]
  (->> input
       parse-input
       count-increases))

(defn part-2
  "Day 01 Part 2"
  [input]
  (->> input
       parse-input
       (windows 3)
       (map window-sum)
       count-increases))
