(ns advent-of-code.day-18-test
  (:require [clojure.test :refer [deftest testing is]]
            [advent-of-code.day-18 :refer [part-1 part-2 sum magnitude]]
            [advent-of-code.util :refer [read-input]]))

(deftest part1
  (testing "Final sum"
    (testing "of example sum-1"
      (let [expected [[[[1,1],[2,2]],[3,3]],[4,4]]]
        (is (= expected (sum (read-input "day-18-sum-1.txt"))))))
    (testing "of example sum-2"
      (let [expected [[[[3,0],[5,3]],[4,4]],[5,5]]]
        (is (= expected (sum (read-input "day-18-sum-2.txt"))))))
    (testing "of example sum-3"
      (let [expected [[[[5,0],[7,4]],[5,5]],[6,6]]]
        (is (= expected (sum (read-input "day-18-sum-3.txt"))))))
    (testing "of example sum-4"
      (let [expected [[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]]
        (is (= expected (sum (read-input "day-18-sum-4.txt"))))))
    (testing "of example sum-5"
      (let [expected [[[[6,6],[7,6]],[[7,7],[7,0]]],[[[7,7],[7,7]],[[7,8],[9,9]]]]]
        (is (= expected (sum (read-input "day-18-example.txt")))))))
  (testing "Magnitude"
    (testing "of [9,1]"
      (let [expected 29]
        (is (= expected (magnitude [9 1])))))
    (testing "of [1,9]"
      (let [expected 21]
        (is (= expected (magnitude [1 9])))))
    (testing "of [[9,1],[1,9]]"
      (let [expected 129]
        (is (= expected (magnitude [[9,1],[1,9]])))))
    (testing "of [[1,2],[[3,4],5]]"
      (let [expected 143]
        (is (= expected (magnitude [[1,2],[[3,4],5]])))))
    (testing "of [[[[0,7],4],[[7,8],[6,0]]],[8,1]]"
      (let [expected 1384]
        (is (= expected (magnitude [[[[0,7],4],[[7,8],[6,0]]],[8,1]])))))
    (testing "of [[[[1,1],[2,2]],[3,3]],[4,4]]"
      (let [expected 445]
        (is (= expected (magnitude [[[[1,1],[2,2]],[3,3]],[4,4]])))))
    (testing "of [[[[3,0],[5,3]],[4,4]],[5,5]]"
      (let [expected 791]
        (is (= expected (magnitude [[[[3,0],[5,3]],[4,4]],[5,5]])))))
    (testing "of [[[[5,0],[7,4]],[5,5]],[6,6]]"
      (let [expected 1137]
        (is (= expected (magnitude [[[[5,0],[7,4]],[5,5]],[6,6]])))))
    (testing "of [[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]"
      (let [expected 3488]
        (is (= expected (magnitude [[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]])))))
    (testing "of [[[[6,6],[7,6]],[[7,7],[7,0]]],[[[7,7],[7,7]],[[7,8],[9,9]]]]"
      (let [expected 4140]
        (is (= expected (magnitude [[[[6,6],[7,6]],[[7,7],[7,0]]],[[[7,7],[7,7]],[[7,8],[9,9]]]]))))))
  (testing "Example home work assignement"
    (let [expected 4140]
      (is (= expected (part-1 (read-input "day-18-example.txt")))))))

(deftest part2
  (testing "Part 2 with example input"
    (let [expected nil]
      (is (= expected (part-2 (read-input "day-18-example.txt")))))))
