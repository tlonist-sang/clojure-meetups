(ns clojure-meetup-5.2.10
  (:require [clojure.test :refer [deftest testing is run-tests]]))

; Streams with seq, into, reduce, and transduce support
; Java APIs increasingly return Streams and are hard to consume because they do not implement interfaces that Clojure already supports, and hard to interop with because Clojure doesn’t directly implement Java functional interfaces.

(deftest stream-usage-test
  (testing "stream-into!"
    (is (= {:a 1 :b 2} (stream-into! {} (.stream ^java.util.Collection [[:a 1] [:b 2]])))))

  (testing "stream-seq!"
    (is (= '(1 2 3 4) (stream-seq! (.stream ^java.util.Collection [1 2 3 4])))))
  
  (testing "stream-reduce!"
    (is (= 10 (stream-reduce! + 0 (.stream ^java.util.Collection [1 2 3 4]))))))

(comment
  (run-tests)
  :rcf)