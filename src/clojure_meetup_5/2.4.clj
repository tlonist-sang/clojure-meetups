(ns clojure-meetup-5.2.4
  (:require [clojure.test :refer [deftest is testing]]))

(deftest method-values-test
  (testing "Method values are automatically wrapped"
    (is (= [1 0 1]
           ;; After
           (->> [-1 0 1]
                (map #(java.lang.Math/abs %))
                (take 3)))))
  (testing "Method values are automatically wrapped"
    (is (= [1 0 1]
           ;; After
           (->> [-1 0 1]
                (map #(java.lang.Math/abs %))
                (take 3))))))
;; Manual wrapping of Java methods is NOT needed anymore

(comment
  ;; Before
  (->> [-1 0 1]
       (map #(java.lang.Math/abs %)))
  (->> ["2024-01-01T00:00:00Z" "2024-01-01T00:00:00Z"] 
       (map #(java.time.Instant/parse %)))
  :rcf)