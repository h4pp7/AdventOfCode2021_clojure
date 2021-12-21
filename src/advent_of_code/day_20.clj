(ns advent-of-code.day-20)

(defn parse-input [input]
  (let [[algo img] (clojure.string/split input #"\n\n") 
        img (mapv vec (clojure.string/split-lines img))]
    [(vec algo) 
     img
     (for [x (range -2 (count img)) 
           y (range -2 (count (first img)))] 
       [x y])]))

(defn neighbor-idx [[x y]] 
  (for [dy [-1 0 1]
        dx [-1 0 1]]
    [(+ y dy) (+ x dx)]))

(defn get-index [pixel outside img]
  (loop [neighbors (neighbor-idx pixel)
         bit-pos (take 9 (iterate #(quot % 2) 256))
         n 0]
    (if (empty? neighbors)
      n
      (recur (rest neighbors)
             (rest bit-pos)
             (if (= \# (get-in img (first neighbors) outside))
               (+ n (first bit-pos))
               n)))))

(defn render [img]
  (->> img
       (map #(apply str %))
       (clojure.string/join \newline)
       print))

(defn enhance [algo outside img]
  (vec (for [y (range -2 (+ 2 (count img)))] 
         (vec (concat 
                (for [x (range -2 (+ 2 (count (first img))))] 
                  (get algo (get-index [x y] outside img))))))))

(defn part-1
  "Day 20 Part 1"
  [input]
  (let [[algo img pixel] (parse-input input)
        pass-1 (enhance algo \. img)]
    (->> pass-1
         (enhance algo \#)
         flatten
         (filter #(= \# %))
         count)))

(defn part-2
  "Day 20 Part 2"
  [input]
  input)
