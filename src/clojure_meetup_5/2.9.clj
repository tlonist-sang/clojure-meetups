(ns clojure-meetup-5.2.9
  (:require [clojure.test :refer [deftest testing is run-tests]])
  (:import [java.util Optional]))

;; Java Supplier Interop
;; All IDeref implementations (atom, ref, agent, future) implement Supplier
;; Supplier is a functional interface that has a single method get

  ;; Before Clojure 1.12
(defn wrapped-else-get [optional ideref]
  (.orElseGet optional (fn [] @ideref)))

  ;; With Clojure 1.12
(defn else-get [optional ideref]
  (.orElseGet optional ideref))

(deftest supplier-interop-test
  (testing "Supplier interop"
    ;; wrapped-else-get
    (is (= 1 (wrapped-else-get (Optional/ofNullable nil) (delay 1))))
    (is (= 100 (wrapped-else-get (Optional/ofNullable 100) (delay nil))))
    ;; else-get
    (is (= 1 (else-get (Optional/ofNullable nil) (delay 1))))
    (is (= 100 (else-get (Optional/ofNullable 100) (delay nil))))))

(comment
  (run-tests)
  :rcf)

