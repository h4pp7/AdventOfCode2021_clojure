(ns advent-of-code.day-16)

(defn bit-stream [hex-string] 
  (let [d {"0" "0000" "1" "0001" "2" "0010" "3" "0011" "4" "0100" "5" "0101"
           "6" "0110" "7" "0111" "8" "1000" "9" "1001" "A" "1010" "B" "1011"
           "C" "1100" "D" "1101" "E" "1110" "F" "1111"}]
    (apply str (map d (clojure.string/split hex-string #""))))) 

(defn to-dec [char-seq]
  (clojure.edn/read-string (str "2r" (apply str char-seq))))

(defn part-1
  [input]
  (letfn 
    [(header [bs sum]
       #(if (> 11 (count bs))
          sum
          (let [[h r]      (split-at 6 bs)
                [ver t-id] (split-at 3 h)
                sum        (+ sum (to-dec ver))]
            (if (= t-id '(\1 \0 \0))  
              (literal r sum)
              (operator r sum)))))
     (literal [bs sum]
       #(loop [bs bs]
          (let [[[prefix & _] r] (split-at 5 bs)] 
            (if (= \0 prefix)
              (header r sum)
              (recur r)))))
     (operator [[l-id & r] sum]
       #(let [l-id l-id]
          (case l-id
            \0    (header (drop 15 r) sum)
            \1    (header (drop 11 r) sum)
            :else false)))]
    (trampoline header (bit-stream input) 0)))

(defn part-2
  "Day 16 Part 2"
  [input]
  input)
