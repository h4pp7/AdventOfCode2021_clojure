(ns advent-of-code.day-15
  (:require [clojure.string :refer [split-lines]]
            [advent-of-code.util :refer [abs]]))
  
(defn parse-input
  [input]
  (let [lines (split-lines input)
        height (count lines)
        width (count (last lines))]
    (mapv (fn [l] (mapv #(clojure.edn/read-string %) (re-seq #"\d" l))) lines)))

(defn manhattan-neighbors
  [[x y] cavern]
  (for [[nx ny] [[(- x 1) y] [(+ x 1) y] [x (- y 1)] [x (+ y 1)]] 
        :when (and (<= 0 nx (dec (count (last cavern))))
                   (<= 0 ny (dec (count cavern))))]
    [nx ny]))

(defn h
  "estimates the cheapest cost to goal by calculating the manhattan distance"
  [[x y] [a b]]
  (+ (abs (- x a)) (abs (- y b)))) 
  
(defn expand-node
  [current open-list closed-list goal cavern]
  (if-let [neighbors (remove #(contains? closed-list %)
                             (manhattan-neighbors current cavern))]  
    (let [current-g-score (get-in open-list [current :g-score])
          new-open-list (reduce (fn [ol n]
                                  (let [tentative-g (+ current-g-score 
                                                       (get-in cavern n))]
                                    (-> ol
                                        (update-in [n :g-score] #(if-some [old %] 
                                                                   (min old tentative-g) 
                                                                   tentative-g))
                                        (assoc-in [n :f-score] (+ tentative-g (h n goal))))))
                                open-list
                                neighbors)]
      (dissoc new-open-list current))
    (dissoc open-list current)))
  
(defn a*
  [start goal cavern]

  (loop [open-list   {start {:g-score 0 :f-score 0}}
         closed-list #{}]
    (let [current (->> open-list 
                       (sort-by (comp :g-score second))
                       (remove (fn [[k _]] (contains? closed-list k))) 
                       first
                       key)] 
      (if (= goal current)
        (get-in open-list [current :g-score])
        (recur (expand-node current 
                            open-list
                            closed-list
                            goal
                            cavern)
               (conj closed-list current))))))

(defn part-1
  "Day 15 Part 1"
  [input]
  (let [cavern (parse-input input)
        goal [(dec (count (last cavern))) (dec (count cavern))]
        start [0 0]]
    (a* start goal cavern)))

(defn part-2
  "Day 15 Part 2"
  [input]
  input)
