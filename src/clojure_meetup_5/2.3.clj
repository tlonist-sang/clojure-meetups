(ns clojure-meetup-5.2.3
  (:require [clojure.java.process :as process]))

;; start and control external processes

(comment
  ;; Start an external process with control over input/output streams
  (let [p (process/start {} "ping" "-c" "3" "localhost")]
    (println (slurp (.getInputStream p))))
  
  ;; Execute an external process and capture the output 
  (-> (process/exec "ls" "-la")
      (println)) 
  :rcf)