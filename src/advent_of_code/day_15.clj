(ns advent-of-code.day-15
  (:require [clojure.string :refer [split-lines]]
            [advent-of-code.util :refer [abs]]
            [clojure.data.priority-map :refer [priority-map-keyfn]]))
  
(defn parse-input
  [input]
  (let [lines (split-lines input)
        height (count lines)
        width (count (last lines))]
    (mapv (fn [l] (mapv #(clojure.edn/read-string %) (re-seq #"\d" l))) lines)))

(defn manhattan-neighbors
  [[y x] height width]
  (for [[ny nx] [[(- x 1) y] [(+ x 1) y] [x (- y 1)] [x (+ y 1)]] 
        :when (and (<= 0 nx width)
                   (<= 0 ny height))]
    [nx ny]))
  
(defn expand-node
  [current open-list closed-list goal height width cost-fn h]
  (if-let [neighbors (remove #(contains? closed-list %)
                             (manhattan-neighbors (first current) height width))]  
    (reduce (fn [ol n]
              (let [tentative-g (+ (:g-score (last current))
                                   (cost-fn n))]
                (-> ol
                    (update-in [n :g-score] #(if-some [old %] (min old tentative-g) tentative-g))
                    (assoc-in [n :f-score] (+ tentative-g (h n goal))))))
            open-list
            neighbors)
    open-list))
  
(defn a*
  [start goal height width cost-fn h]
  (loop [open-list   (priority-map-keyfn :g-score start {:g-score 0 :f-score 0})
         closed-list #{}]
    (let [current (first (remove (fn [[k v]] (contains? closed-list k)) open-list))] 
      (if (= goal (first current))
        (:g-score (second current))
        (recur (expand-node current 
                            (pop open-list)
                            closed-list
                            goal
                            height
                            width
                            cost-fn
                            h)
               (conj closed-list (first current)))))))

(defn part-1
  "Day 15 Part 1"
  [input]
  (let [cavern (parse-input input)
        start [0 0]
        cost-fn (fn [n] (get-in cavern n))
        h (fn [[x y] [a b]]
            (+ (abs (- x a)) (abs (- y b)))) 
        height (dec (count cavern)) 
        width (dec (count (first cavern)))
        goal [height width]]
    (a* start goal height width cost-fn h)))

(defn part-2
  "Day 15 Part 2"
  [input]
  (let [cavern (parse-input input)
        start [0 0]
        input-height (count cavern) 
        input-width (count (first cavern))
        height (dec (* 5 input-width))
        width (dec (* 5 input-width))
        goal [height width] 
        cost-fn (fn [[y x]]
                  (-> cavern
                      (get-in [(mod y input-height) (mod x input-width)])
                      (+ (quot x input-width)) 
                      (+ (quot y input-height))
                      dec
                      (mod 9)
                      inc))
        h (fn [[x y] [a b]]
            (+ (+ (abs (- x a)) 
                  (quot x 10))
               (+ (abs (- y b)) 
                  (quot y 10))))]
    
    (a* start goal height width cost-fn h)))
