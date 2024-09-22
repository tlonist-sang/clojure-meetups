(ns clojure-meetup-5.1.3 
  (:require [clojure.edn :as edn]
            [clojure.test :refer [deftest run-tests testing is]]))

;; Below code is a concept.
;; For the actual example, one needs to serialize/deserialize the edn-map to/from a string.

(defn parse-edn-map [edn-str]
  (edn/read-string {:readers {'eg identity}} edn-str))

(deftest infinite-sequence-test
  (testing "무한 시퀀스를 키로 가지는 맵을 파싱할 수 있습니다."
    ;; arrange
    (let [edn-map-str "{#eg(clojure.core/repeat \"something\") \"value\"}"
          ;; act
          parsed-map (parse-edn-map edn-map-str)]
      ;; assert
      (is (map? parsed-map))
      (is (= ["something" "something" "something"] 
             (take 3 (eval (ffirst parsed-map)))))
      (is (= (second (first parsed-map)) "value")))))

(comment
  (run-tests)
  :rcf)