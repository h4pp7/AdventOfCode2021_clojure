(ns advent-of-code.day-12
  "AOC 2021 Day 12"
  (:require [clojure.string :refer [split-lines upper-case]]))

(defn small?
  [node]
  (not= (upper-case node) node)) 

(defn count-paths
  [current visited graph]
  (cond
    (= current "end") 1
    (and (small? current)
         (some #{current} visited)) 0
    :else (for [n (get graph current)]
            (count-paths n (conj visited current) graph)))) 
 
(defn parse-map
  [input]
  (reduce (fn [m l]
            (let [[k v] (re-seq #"\w+" l)]
              (-> m
                  (update k #(conj % v))
                  (update v #(conj % k)))))
          {}
          (split-lines input)))

(defn part-1
  "Day 12 Part 1"
  [input]
  (let [graph (parse-map input)]
    (apply + (flatten (count-paths "start" #{} graph)))))

(defn part-2
  "Day 12 Part 2"
  [input]
  input)
