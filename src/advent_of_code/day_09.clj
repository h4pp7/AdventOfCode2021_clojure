(ns advent-of-code.day-09
  "AOC 2021 Day 9")

(defn manhattan-neighbors
  [[x y] coll]
  (map #(get-in coll % 9) [[(- x 1) y] [(+ x 1) y] 
                           [x (- y 1)] [x (+ y 1)]]))

(defn lowest?
  [neighbors number]
  (every? #(< number %) neighbors))
    
(defn part-1
  "Day 09 Part 1"
  [input]
  (let [heightmap (into [] (for [line (clojure.string/split-lines input)] 
                             (into [] (for [d (re-seq #"\d" line)]
                                        (clojure.edn/read-string d)))))]
    (reduce (fn [sum idx]
              (let [n (get-in heightmap idx)]
                (if (lowest? (manhattan-neighbors idx heightmap) n)
                  (+ sum (inc n))
                  sum)))
            0
            (for [x (range (count heightmap))
                  y (range (count (first heightmap)))] 
              [x y]))))

(defn part-2
  "Day 09 Part 2"
  [input]
  input)
