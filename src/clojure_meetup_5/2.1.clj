(ns clojure-meetup-5.2.1
  (:require [clojure.repl.deps :refer [add-lib add-libs sync-deps]]))

;; mainly for development purpose
;; using deps.edn is still considered best practice
(comment
  (add-lib 'dev.weavejester/medley {:mvn/version "1.8.1"})
  (require '[medley.core :refer [update-existing]])
  (update-existing {:a 1} :b inc)

  ;; not specifying version can work.
  (add-lib 'com.rpl/specter)
  (require '[com.rpl.specter :refer [transform]])
  (transform [:a] inc {:a 1})

  (add-libs {'metosin/malli {:mvn/version "0.16.4"}
             'buddy/buddy {:mvn/version "2.0.0"}})
  
  (all-ns)
  (sync-deps)
  :rcf)