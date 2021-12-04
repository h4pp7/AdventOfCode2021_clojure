(ns advent-of-code.day-03-test
  (:require [clojure.test :refer [deftest testing is]]
            [advent-of-code.day-03 :refer [part-1 part-2]]
            [advent-of-code.util :refer [read-input]]))

(deftest part1
  (testing "Part 1 with example input"
    (let [expected 198]
      (is (= expected (part-1 (read-input "day-03-example.txt")))))))

(deftest part2
  (testing "Part 2 with example input"
    (let [expected 230]
      (is (= expected (part-2 (read-input "day-03-example.txt")))))))
