(ns clojure-meetup-5.2.7
  (:require [clojure.test :refer [deftest testing are run-tests]]))

;; Array class syntax
;; Array class type hints are made more readable

(deftest array-class-syntax-test
  (testing "Array class type hints are made morereadable"
    (let [double-array (make-array Double/TYPE 2 2)
          string-array (into-array String ["hello" "world"])]
      (are [expected actual] (= expected actual)
        ;; previously [[D
        double/2 (.getClass double-array)
        ;; previously [Ljava.lang.String;
        java.lang.String/1 (.getClass string-array)))))

(comment
  (run-tests)
  :rcf)