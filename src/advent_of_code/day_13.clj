(ns advent-of-code.day-13
  "AOC 2021 Day 13"
  (:require [advent-of-code.util :refer [split-p]]
            [clojure.string :refer [split split-lines includes? join]]))

(defn parse-input [input]
  (let [[dots instructions] (split-p input)
        dots (->> dots
                  split-lines
                  (mapv (fn [l] (mapv #(clojure.edn/read-string %) 
                                     (split l #","))))) 
        instructions (mapv (fn [l]
                            (if (includes? l "x")
                              [0 (clojure.edn/read-string (re-find #"\d+" l))]
                              [1 (clojure.edn/read-string (re-find #"\d+" l))]))
                            
                          (split-lines instructions))]
    [dots instructions]))
  
(defn reflect [dots [axis fold-point]]
  (distinct (for [d dots]
              (if (> (get d axis) fold-point)
                (update d axis #(- % (* 2 (- % fold-point))))
                d))))

(defn draw-paper [dots]
  (let [width (inc (apply max (map second dots)))
        height (inc (apply max (map first dots)))]
    (join \newline (for [y (range width)]
                     (apply str (for [x (range height)]
                                  (if (some #{[x y]} dots) \# \space)))))))

(defn part-1
  "Day 13 Part 1"
  [input]
  (let [[dots instructions] (parse-input input)]
    (count (reflect dots (first instructions)))))

(defn part-2
  "Day 13 Part 2"
  [input]
  (let [[dots instructions] (parse-input input)]
    (print (draw-paper (reduce #(reflect %1 %2) dots instructions)))))
