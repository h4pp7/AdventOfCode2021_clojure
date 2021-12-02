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

(defn parse
  [input]
  (->> input
       s/split-lines
       (map #(s/split % #" ")) 
       (map (fn [[k v]] [k (clojure.edn/read-string v)]))))

(defn part-2
  "Day 02 Part 2"
  [input]
  (->> input
       parse
       (reduce (fn [m [dir amount]]
                 (cond
                   (= dir "forward")  (-> m 
                                         (update :pos + amount) 
                                         (update :depth + (* (m :aim) amount))) 
                   (= dir "down")     (update m :aim + amount)
                   (= dir "up")       (update m :aim - amount)))
               {:pos 0 :depth 0 :aim 0})
       (#(* (% :pos) (% :depth))))) 
