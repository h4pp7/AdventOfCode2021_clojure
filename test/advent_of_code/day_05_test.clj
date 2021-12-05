(ns advent-of-code.day-05-test
  (:require [clojure.test :refer [deftest testing is]]
            [advent-of-code.day-05 :refer [part-1 part-2]]
            [advent-of-code.util :refer [read-input]]))

(deftest parsing
  (testing "Parse"
    (testing "straight lines into set of 2D vectors"
      (let [input "0,9 -> 2,9"
            expected #{[0 9] [1 9] [2 9]}]))))

(deftest part1
  (testing "Part 1 with example input"
    (let [expected 5]
      (is (= expected (part-1 (read-input "day-05-example.txt")))))))

(deftest part2
  (testing "Part 2 with example input"
    (let [expected 12]
      (is (= expected (part-2 (read-input "day-05-example.txt")))))))
