(ns clojure-meetup-5.2.12
  (:require [clojure.test :refer [deftest testing are run-tests]]))

;; Efficient drop and partition for persistent or algorithmic collections
;; Partitioning of a collection uses a series of takes (to build a partition) and drops (to skip past that partition) . CLJ-2713 adds a new internal interface (IDrop) indicating that a collection can drop more efficiently than sequential traversal, and implements that for persistent collections and algorithmic collections like range and repeat. These optimizations are used in drop, nthrest, and nthnext.

(deftest efficient-drop-and-partition-for-persistent-or-algorithmic-collections-test
  (testing "Efficient drop and partition for persistent or algorithmic collections"
    (let [coll (range 10)]
      (are [expected actual] (= expected actual)
        '([0 1 2] [3 4 5] [6 7 8]) (partitionv 3 coll)
        '([0 1 2] [3 4 5] [6 7 8] [9]) (partitionv-all 3 coll)
        [[0 1 2] '(3 4 5 6 7 8 9)] (splitv-at 3 coll)))))

(defn test-for-213 [x] x)

(comment
  (run-tests)
  :rcf)