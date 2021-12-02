(ns advent-of-code.day-02
  "AOC 2021 Day 2"
  (:require [clojure.string :as s]))

(defn total
  [steps]
  (reduce (fn [sum [_ v]]
            (+ sum (clojure.edn/read-string v)))
          0
          steps))

(defn part-1
  "Day 02 Part 1"
  [input]
  (let [commands (group-by first 
                           (map #(s/split % #" ")
                                (s/split-lines input)))] 
    (* (total (commands "forward"))
       (- (total (commands "down"))
          (total (commands "up"))))))

(defn part-2
  "Day 02 Part 2"
  [input]
  (loop [[[dir v] & commands] (map #(s/split % #" ") (s/split-lines input))
         pos      0
         aim      0
         depth    0]
    (cond
      (not dir)         (* pos depth)
      (= dir "forward") (recur  commands 
                                (+ pos (clojure.edn/read-string v))
                                aim
                                (+ depth 
                                   (* aim (clojure.edn/read-string v))))
      (= dir "down")    (recur  commands
                                pos
                                (+ aim (clojure.edn/read-string v))
                                depth)
      (= dir "up")      (recur  commands
                                pos
                                (- aim (clojure.edn/read-string v))      
                                depth))))
