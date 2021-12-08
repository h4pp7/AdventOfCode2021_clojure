(ns advent-of-code.day-07-test
  (:require [clojure.test :refer [deftest testing is]]
            [advent-of-code.day-07 :refer [part-1 part-2 median]]
            [advent-of-code.util :refer [read-input]]))

(deftest calculate-median
  (testing "Calculating the median"
    (testing "of an odd number of numbers"
      (let [input [1 1 2 4 37]
            expected 2]
        (is (= expected (median input)))))
    (testing "of an even number of numbers"
      (let [input [30 5 2 13 8 132]
            expected 21/2]
        (is (= expected (median input)))))))

(deftest part1
  (testing "Part 1 with example input"
    (let [expected 37]
      (is (= expected (part-1 (read-input "day-07-example.txt")))))))

(deftest part2
  (testing "Part 2 with example input"
    (let [expected 168]
      (is (= expected (part-2 (read-input "day-07-example.txt")))))))
