(ns advent-of-code.day-09
  "AOC 2021 Day 9")

(defn manhattan-neighbors
  [idx stride coll]
  (remove nil? (map #(get coll % 9) [(- idx 1) (+ idx 1) (- idx stride) (+ idx stride)])))

(defn manhattan-neighbors-idx
  [idx stride]
  [(- idx 1) (+ idx 1) (- idx stride) (+ idx stride)])

(defn lowest?
  [neighbors number]
  (every? #(< number %) neighbors))

(defn spread
  [[idx & indices] heightmap basin stride]
  (if (nil? idx)
    basin
    (let [neighbors (manhattan-neighbors idx stride heightmap)])))
    
(defn part-1
  "Day 09 Part 1"
  [input]
  (let [heightmap (mapv #(clojure.edn/read-string %) (clojure.string/split input #""))
        stride (inc (first (keep-indexed #(when (not (number? %2)) %1) heightmap)))]
    (reduce (fn [sum idx]
              (let [n (get heightmap idx)]
                (if (and (some? n)
                         (lowest? (manhattan-neighbors idx stride heightmap) n))
                  (+ sum (inc n))
                  sum)))
            0
            (range (count heightmap)))))

(defn part-2
  "Day 09 Part 2"
  [input]
  input)
