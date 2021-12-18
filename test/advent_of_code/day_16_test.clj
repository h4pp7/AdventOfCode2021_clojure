(ns advent-of-code.day-16-test
  (:require [clojure.test :refer [deftest testing is]]
            [advent-of-code.day-16 :refer [part-1 part-2]]
            [advent-of-code.util :refer [read-input]]))

(deftest part1
  (testing "Part 1"
    (testing "with example string 'D2FE28'"
      (let [expected 6]
        (is (= expected (part-1 "D2FE28"))))) 
    (testing "with example string '8A004A801A8002F478'"
      (let [expected 16]
        (is (= expected (part-1 "8A004A801A8002F478"))))) 
    (testing "with example string '620080001611562C8802118E34'"
      (let [expected 12]
        (is (= expected (part-1 "620080001611562C8802118E34"))))) 
    (testing "with example string 'C0015000016115A2E0802F182340'"
      (let [expected 23]
        (is (= expected (part-1 "C0015000016115A2E0802F182340"))))) 
    (testing "with example string 'A0016C880162017C3686B18A3D4780'"
      (let [expected 31]
        (is (= expected (part-1 "A0016C880162017C3686B18A3D4780"))))))) 

(deftest part2
  (testing "Part 2"
    (testing "with example string 'C200B40A82'"
      (let [expected 3]
        (is (= expected (part-1 "C200B40A82"))))) 
    (testing "with example string '04005AC33890'"
      (let [expected 54]
        (is (= expected (part-1 "04005AC33890"))))) 
    (testing "with example string '880086C3E88112'"
      (let [expected 7]
        (is (= expected (part-1 "880086C3E88112"))))) 
    (testing "with example string 'CE00C43D881120'"
      (let [expected 9]
        (is (= expected (part-1 "CE00C43D881120"))))) 
    (testing "with example string 'D8005AC2A8F0'"
      (let [expected 1]
        (is (= expected (part-1 "D8005AC2A8F0")))))
    (testing "with example string 'F600BC2D8F'"
      (let [expected 0]
        (is (= expected (part-1 "F600BC2D8F")))))
    (testing "with example string '9C005AC2F8F0'"
      (let [expected 0]
        (is (= expected (part-1 "9C005AC2F8F0")))))
    (testing "with example string '9C0141080250320F1802104A08'"
      (let [expected 1]
        (is (= expected (part-1 "9C0141080250320F1802104A08")))))))
