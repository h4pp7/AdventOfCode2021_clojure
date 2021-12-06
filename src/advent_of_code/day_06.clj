(ns advent-of-code.day-06
  "AOC 2021 Day 6")

(defn initial-state
  [data]
  (mapv #(get (frequencies data) % 0) (range 0 9)))

(defn rotate-left
  [v n]
  (vec (concat (subvec v n) (subvec v 0 n))))

(defn step
  [fish-list]
  (let [new-fish (first fish-list)]
    (update (rotate-left fish-list 1) 6 + new-fish)))

(defn fish-count
  [n population] 
  (reduce + (nth (iterate step population) n)))

(defn calculate-fish
  [input n]
  (->> input
     (re-seq #"\d+")
     (map #(clojure.edn/read-string %))
     initial-state
     (fish-count n)))

(defn part-1
  "Day 06 Part 1"
  [input]
  (calculate-fish input 80))
  
(defn part-2
  "Day 06 Part 2"
  [input]
  (calculate-fish input 256))
