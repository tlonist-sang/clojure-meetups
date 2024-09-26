(ns clojure-meetup-5.2.2)

;; Invoke tool functions out of process

;; You could invoke tools using CLI, like in  https://clojure.org/reference/clojure_cli#tools
;; clj -Ttools install io.github.clojure/tools.deps.graph '{:git/tag "v1.0.63"}' :as deps-graph
;; invoke-tool is a function that can be used to invoke tools out of process

(comment
  (require '[clojure.tools.deps.interop :as tool]) 
  
  ;; add-lib in implementation
  (tool/invoke-tool {:tool-alias :deps
                     :fn 'clojure.tools.deps/find-latest-version
                     :args {:lib 'lib, :procurer 'procurer}})
  :rcf)