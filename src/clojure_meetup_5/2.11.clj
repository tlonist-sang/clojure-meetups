(ns clojure-meetup-5.2.11
  (:require [clojure.test :refer [deftest testing is run-tests]]))

;; PersistentVector implements Spliterable


(defmacro sectime
  [expr]
  `(let [start# (. System (currentTimeMillis))
         ret# ~expr]
     (/ (double (- (. System (currentTimeMillis)) start#)) 1000.0)))

(deftest count-even-test
  (testing "Count even numbers in a collection"
    (let [numbers (vec (range 1000000))

          count-even-standard
          (fn [v]
            (count (filter even? v)))

          count-even-spliterator
          (fn [v]
            (let [spltr (.spliterator ^java.util.Collection v)
                  counter (atom 0)
                  action (reify java.util.function.Consumer
                           (accept [_this n]
                             (when (even? n)
                               (swap! counter inc))))]
              (.forEachRemaining spltr action)
              @counter))]
      (is (> (sectime (count-even-standard numbers))
             (sectime (count-even-spliterator numbers)))))))
