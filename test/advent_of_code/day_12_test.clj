(ns advent-of-code.day-12-test
  (:require [clojure.test :refer [deftest testing is]]
            [advent-of-code.day-12 :refer [part-1 part-2 parse-map]]
            [advent-of-code.util :refer [read-input]]))

(deftest part1
  (testing "Part 1"
    (testing "with the first, small example input"
      (let [expected 10]
        (is (= expected (part-1 (read-input "day-12-example.txt"))))))
    (testing "with the second, slightly larger example input"
      (let [expected 19]
        (is (= expected (part-1 (read-input "day-12-example-2.txt"))))))
    (testing "with the second, even large example input"
      (let [expected 226]
        (is (= expected (part-1 (read-input "day-12-example-3.txt"))))))))

(deftest part2
  (testing "Part 2"
    (testing "with the first, small example input"
      (let [expected 36]
        (is (= expected (part-2 (read-input "day-12-example.txt"))))))
    (testing "with the second, slightly larger example input"
      (let [expected 103]
        (is (= expected (part-2 (read-input "day-12-example-2.txt"))))))
    (testing "with the second, even large example input"
      (let [expected 3509]
        (is (= expected (part-2 (read-input "day-12-example-3.txt"))))))))
