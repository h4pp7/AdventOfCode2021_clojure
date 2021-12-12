(ns advent-of-code.day-11
  "AOC 2021 Day 11")

(defn neighboring
  [[x y] grid all-idx]
  (for [dx [-1 0 1]
        dy [-1 0 1]
        :let [n [(+ x dx) (+ y dy)]]
        :when (some #{n} all-idx)]
    n))

(defn flashing
  [grid all-idx]
  (for [i all-idx
        :when (< 9 (get-in grid i))]
    i))

(defn increase-energy
  [grid coords]
  (reduce (fn [m c] (update-in m c inc)) grid coords))

(defn reset
  [grid coords]
  (reduce (fn [m c] (assoc-in m c 0)) grid coords))

(defn step
  [all-idx grid]
  (if-let [flashing (not-empty (for [i all-idx
                                     :when (= 10 (get-in grid i 0))]
                                 (neighboring i grid all-idx)))]
    (recur all-idx (increase-energy grid (apply concat flashing))) 
    grid)) 


(defn part-1
  "Day 11 Part 1"
  [input]
  (let [grid (mapv (fn [l] (mapv #(clojure.edn/read-string %)
                                 (re-seq #"\d" l)))
                   (clojure.string/split-lines input))
        all-idx (for [x (range (count grid)) 
                      y (range (count (first grid)))] 
                  [x y])]
    (reduce (fn [m _]
              (let [g (step all-idx (:grid m))
                    f (count (flashing g all-idx))]
                (-> m
                    (update :flashes #(+ % f))
                    (assoc :grid (increase-energy (reset g all-idx) all-idx)))))
            {:flashes 0 :grid grid}
            (range 100))))

(defn part-2
  "Day 11 Part 2"
  [input]
  input)
