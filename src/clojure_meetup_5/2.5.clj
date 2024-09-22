(ns clojure-meetup-5.2.5
  (:require [clojure.test :refer [deftest is testing]]))

;; Qualified methods
;; Classname/method - value is a Clojure function that invokes a static method
;; Classname/.method - value is a Clojure function that invokes an instance method
;; Classname/new - value is a Clojure function that invokes a constructor

(deftest qualified-methods-test
  (testing "Qualified methods are automatically wrapped"
    (is (= 1
           (java.lang.Math/abs -1))))
  (testing "Instance method"
    (is (int? (-> (java.util.Date/new)
                  (java.util.Date/.getTime)))))
  (testing "Constructor"
    (is (= "http://localhost"
           (-> (java.net.URI/new "http://localhost")
               (.toString))))))

(comment
  :rcf)