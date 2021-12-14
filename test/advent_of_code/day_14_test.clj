(ns advent-of-code.day-14-test
  (:require [clojure.test :refer [deftest testing is]]
            [advent-of-code.day-14 :refer [part-1 part-2 polymerize]]
            [advent-of-code.util :refer [read-input]]))

(deftest part1
  (testing "Part 1 with example input:"
    (testing "Length after step 5"
      (let [expected 97]
        (is (= expected (->> (read-input "day-14-example.txt")
                             polymerize
                             (take 6)
                             last
                             :template
                             count)))))
    (testing "After step 10:"
      (let [result (->> (read-input "day-14-example.txt")
                        polymerize
                        (take 11)
                        last
                        :template)]
        (testing "Template Length"
          (is (= 3073 (count result)))) 
        (testing "Frequency of B"
          (is (= 1749 (count (filter #(= \B %) result)))))
        (testing "Frequency of C"
          (is (= 298 (count (filter #(= \C %) result)))))
        (testing "Frequency of H"
          (is (= 161 (count (filter #(= \H %) result)))))
        (testing "Frequency of N"
          (is (= 865 (count (filter #(= \N %) result)))))))
    (testing "Result"
      (let [expected 1588]
        (is (= expected (part-1 (read-input "day-14-example.txt"))))))))
        
(deftest part2
  (testing "Part 2 with example input"
    (let [expected 2188189693529]
      (is (= expected (part-2 (read-input "day-14-example.txt")))))))
