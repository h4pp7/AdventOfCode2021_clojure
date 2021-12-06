(ns advent-of-code.day-06-test
  (:require [clojure.test :refer [deftest testing is]]
            [advent-of-code.day-06 :refer [part-1 part-2 initial-state]]
            [advent-of-code.util :refer [read-input]]))

(deftest initial
  (testing "Create initial fish spawns"
    (let [input [3 4 3 1 2]
          expected [1 1 2 1 0 0 0]]
      (is (= expected (initial-state input))))))

(deftest part1
  (testing "Part 1 with example input"
    (let [expected 5934]
      (is (= expected (part-1 (read-input "day-06-example.txt")))))))

(deftest part2
  (testing "Part 2 with example input"
    (let [expected 26984457539]
      (is (= expected (part-2 (read-input "day-06-example.txt")))))))
