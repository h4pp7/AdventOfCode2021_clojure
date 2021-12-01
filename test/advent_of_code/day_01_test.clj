(ns advent-of-code.day-01-test
  (:require [clojure.test :refer [deftest testing is]]
            [advent-of-code.day-01 :refer [part-1 part-2 parse-input]]
            [advent-of-code.util :refer [read-input]]))

(deftest parsing
  (testing "turn input into sequence of numbers"
    (let [expected '(199 200 208 210 200 207 240 269 260 263)]
      (is (= expected (parse-input (read-input "day-01-example.txt")))))))

(deftest part1
  (testing "Part 1 with example input"
    (let [expected 7]
      (is (= expected (part-1 (read-input "day-01-example.txt")))))))

(deftest part2
  (testing "Part 2 with example input"
    (let [expected 5]
      (is (= expected (part-2 (read-input "day-01-example.txt")))))))
