(ns advent-of-code.day-22-test
  (:require [clojure.test :refer [deftest testing is]]
            [advent-of-code.day-22 :refer [part-1 part-2]]
            [advent-of-code.util :refer [read-input]]))

(deftest part1
  (testing "Part 1 with example input"
    (let [expected 39]
      (is (= expected (part-1 (read-input "day-22-example.txt"))))))
  (testing "Part 1 with the larger example input"
    (let [expected 590784]
      (is (= expected (part-1 (read-input "day-22-example-2.txt")))))))

(deftest part2
  (testing "Part 2 with example input"
    (let [expected nil]
      (is (= expected (part-2 (read-input "day-22-example.txt")))))))
