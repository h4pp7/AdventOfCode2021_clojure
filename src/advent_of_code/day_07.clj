(ns advent-of-code.day-07
  "AOC 2021 Day 7"
  (:require [advent-of-code.util :refer [abs]]))

(defn median
  [numbers]
  (let [numbers (sort numbers)
        cnt (count numbers)
        mid (quot cnt 2)]
    (if (odd? cnt)
      (nth numbers mid)
      (/ (+ (nth numbers mid) (nth numbers (dec mid))) 2))))

(defn part-1
  "Day 07 Part 1"
  [input]
  (let [positions  (->> input 
                        (re-seq #"\d+")
                        (map #(clojure.edn/read-string %)))
        target     (median positions)]
    (reduce (fn [sum pos]
              (+ sum (abs (- pos target)))) 
            0
            positions)))

(defn part-2
  "Day 07 Part 2"
  [input]
  (let [positions  (->> input 
                        (re-seq #"\d+")
                        (map #(clojure.edn/read-string %)))
        target     (quot (reduce + positions) (count positions))
        cost-1 (reduce (fn [sum pos]
                         (let [distance (abs (- pos target))
                               cost     (/ (* distance (inc distance)) 2)]
                           (+ sum cost)))
                       0
                       positions)
        cost-2 (reduce (fn [sum pos]
                         (let [distance (abs (- pos (inc target)))
                               cost     (/ (* distance (inc distance)) 2)]
                           (+ sum cost)))
                       0
                       positions)]
    (min cost-1 cost-2)))
