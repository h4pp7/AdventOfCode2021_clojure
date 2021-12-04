(ns advent-of-code.day-04
  "AOC 2021 Day 4"
  (:require [clojure.string :refer [split split-lines]]
            [advent-of-code.util :refer [split-p]]
            [clojure.set :refer [union]]))

(defn parse-board
  [board-string]
  (let [rows (reduce (fn [board row]
                       (conj board
                             (->> (re-seq #"\d+" row) 
                                  (map clojure.edn/read-string)
                                  (into []))))
                     [] 
                     (split-lines board-string))]   
    (concat rows (apply mapv vector rows))))
   
(defn cross-out
  [number board]
  (map (fn [col] (remove #(= number %) col)) board))

(defn winner-sum
  [winner]
  (reduce + (distinct (flatten winner))))

(defn part-1
  "Day 04 Part 1"
  [input]
  (let [[numbers & boards] (split-p input)  
        numbers (map clojure.edn/read-string (split numbers #","))    
        boards (map parse-board boards)] 
    (reduce (fn [boards number]
              (let [boards (map #(cross-out number %) boards)] 
                (if-let [winner (some #(if (some empty? %) %) boards)]
                  (reduced (* number 
                              (winner-sum winner)))
                  boards)))
            boards
            numbers)))

(defn part-2
  "Day 04 Part 2"
  [input]
  (let [[numbers & boards] (split-p input)  
        numbers (map clojure.edn/read-string (split numbers #","))    
        boards (map parse-board boards) 
        winner (->> numbers
                    (reduce (fn [m number]
                              (let [boards (map #(cross-out number %) (m :boards))] 
                                (if-let [winner (some #(if (some empty? %) %) boards)]
                                  (-> m
                                      (assoc :winner {:board winner :number number})
                                      (assoc :boards (remove #(some empty? %) boards))) 
                                  (assoc m :boards (remove #(some empty? %) boards)))))
                            {:boards boards})
                    :winner)]
    (* (winner-sum (winner :board))
       (winner :number))))
