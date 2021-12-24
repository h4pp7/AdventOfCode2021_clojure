(ns advent-of-code.util
  "AOC 2021 helper functions")

(defn read-input
  "Reads the file and returns its content as a string"
  [day]
  (slurp (clojure.java.io/resource day)))

(defn split-p
  "splits a str at double linebreak into a vector strings"
  [input]
  (-> input
      clojure.string/trim
      (#(clojure.string/split % #"\n\n"))))

(defn abs [n]
  (if (neg? n)
    (- n)
    n))

(defn mod-1 [n d]
  (-> n dec (mod d) inc))

(defn cartesian-product [colls]
  (if (empty? colls)
    '(())
    (for [more (cart (rest colls))
          x (first colls)]
      (cons x more))))
