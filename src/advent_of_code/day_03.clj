(ns advent-of-code.day-03
  "AOC 2021 Day 3"
  (:require [clojure.string :refer [split-lines]]))

(defn column
  [n coll] 
  (map #(nth % n) coll))

(defn col-freq
  ([n bits] (col-freq n \1 bits))
  ([n rating bits]
   (let [[[x xv] [y yv]] (->> bits
                              (column n)
                              frequencies
                              (sort-by val))]
     (cond 
       (some nil? [x xv y yv]) x 
       (= xv yv)  rating
       (= rating \1) y
       (= rating \0) x))))

(defn mask
  [size]
  (- (bit-shift-left 1 size) 1)) 
       
(defn power-consumption
  [bits mask-size]
  (let [gamma (->> bits
                   first
                   count
                   range
                   (map #(col-freq % bits))
                   (apply str)
                   (str "2r")
                   (clojure.edn/read-string))
        epsilon (bit-and-not (mask mask-size) gamma)] 
    (* gamma epsilon)))

(defn filtered
  [n rating bits]
  (if (= (count bits) 1)
    bits
    (filter #(= (col-freq n rating bits) 
                (nth % n))
            bits)))

(defn as-number
  [bits]
  (->> bits
       first
       (apply str)
       (str "2r")
       (clojure.edn/read-string)))

(defn part-1
  "Day 03 Part 1"
  [input]
  (let [bits (map seq (split-lines input))
        mask-size (count (first bits))]
    (power-consumption bits mask-size)))

(defn part-2
  "Day 03 Part 2"
  [input]
  (let [bits (map seq (split-lines input))]
    (->> (range (count (first bits)))
         (reduce (fn [[o2-bits co2-bits] n]
                   [(filtered n \1 o2-bits)          
                    (filtered n \0 co2-bits)])
                 [bits bits])
         (map as-number)
         (apply *))))
