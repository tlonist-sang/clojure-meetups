(ns clojure-meetup-5.2.13
  (:require [clojure.test :refer [deftest testing is run-tests]]))

;; Interning a var in a namespace (vs aliasing) must create a stable reference that is never displaced, so that all references to an interned var get the same object. There were some cases where interned vars could get displaced and those have been tightened up in 1.12.0-alpha1. If you encounter this situation, you’ll see a warning like "REJECTED: attempt to replace interned var #'some-ns/foo with #'other-ns/foo in some-ns, you must ns-unmap first".
;; In short, if a function name is already defined in a namespace, you cannot define it again.

(defn test-for-213 [x] (inc x))
(deftest interning-test
  (testing "trying to intern abs, but fails"
    (is (nil? (require '[clojure-meetup-5.2.12 :refer [test-for-213]])))))

(comment
  (run-tests)
  :rcf)
