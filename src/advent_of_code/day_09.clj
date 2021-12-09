(ns advent-of-code.day-09
  "AOC 2021 Day 9")

(defn padded
  [coll]
  (let [stride (first (keep-indexed #(when (not (number? %2)) %1) coll))
        padding (repeat (inc stride) 0)]
    (into [] (concat padding coll padding))))

(defn manhattan-neighbors
  [idx stride coll]
  (remove nil? (map #(get coll % 9) [(- idx 1) (+ idx 1) (- idx stride) (+ idx stride)])))

(defn lowest?
  [neighbors number]
  (every? #(< number %) neighbors))

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
