(ns advent-of-code.day-08
  "AOC 2021 Day 8"
  (:require [clojure.string :refer [split split-lines trim]]))

(defn sum
  [word freq-map]
  (apply + (for [c word]
             (get freq-map c))))

(defn make-lookup 
  [configuration]
  (let [freq-map (frequencies configuration)]
    (into {} (map (fn [w n] [(sum w freq-map) n]) 
                  (split configuration #"\ ")
                  (range 10)))))

(defn decode-line
  [line lookup]
  (let [[patterns outputs] (split line #"\|")
        freq-map (frequencies patterns)]
    (reduce (fn [acc [f o]]
              (+ acc
                 (* f (lookup (sum o freq-map)))))
            0
            (partition 2 (interleave [1000 100 10 1]
                                     (split (trim outputs) #"\ "))))))

(defn part-1
  "Day 08 Part 1"
  [input]
  (->> (split-lines input) 
       (mapcat (fn 
                 [l] 
                 (let [[_ o] (split l #"\|")] 
                   (re-seq #"\w+" o))))
       (map #(count %)) 
       (remove #(or (= % 5) (= % 6)))
       count))

(defn part-2
  "Day 08 Part 2"
  [input]
  (let [lookup (make-lookup "abcefg cf acdeg acdfg bcdf abdfg abdefg acf abcdefg abcdfg")]
    (reduce (fn [acc l] (+ acc (decode-line l lookup))) 
            0
            (split-lines input))))
