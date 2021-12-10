(ns advent-of-code.day-10
  "AOC 2021 Day 10")

(defn first-illegal
  [line]
  (loop [[c & r] line
         stack []]
    (cond 
      (some #{c} '(\( \[ \{ \<))        (recur r (conj stack c))
      (= ({\) \( \] \[ \} \{ \> \<} c)
         (peek stack))                  (recur r (pop stack))
      :else                             c)))

(defn auto-complete
  [line]
  (let [stack (loop [[c & r] line
                     stack []]
                (cond 
                  (some #{c} '(\( \[ \{ \<))        (recur r (conj stack c))
                  (= ({\) \( \] \[ \} \{ \> \<} c)
                     (peek stack))                  (recur r (pop stack))
                  :else                             stack))]
    (loop [stack stack
           closing []]
      (if (empty? stack) 
        closing
        (recur (pop stack)
               (conj closing ({\( \) \[ \] \{ \} \< \>} (peek stack))))))))

(defn score-closing
  [brackets]
  (reduce (fn [sum bracket]
            (+ (* sum 5) 
               ({\) 1 \] 2 \} 3 \> 4} bracket)))
          0
          brackets))

(defn part-1
  "Day 10 Part 1"
  [input]
  (let [lines (clojure.string/split-lines input)]
    (reduce (fn [sum l]
              (if-let [illegal (first-illegal l)]
                (+ sum ({\) 3 \] 57 \} 1197 \> 25137} illegal))
                sum))
            0
            lines)))

(defn part-2
  "Day 10 Part 2"
  [input]
  (->> input
       clojure.string/split-lines
       (remove #(first-illegal %))
       (map auto-complete)
       (map score-closing)
       sort
       vec 
       (#(get % (quot (count %) 2)))))
