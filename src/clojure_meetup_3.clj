(ns clojure-meetup-3
  (:require
   [clojure.java.io :as io]
   [clojure.string :as str])
  (:import java.net.ServerSocket
           java.lang.String))

(comment
  ;;1.11.3
  (set! *warn-on-reflection* true)
  (Thread/sleep (Integer. 100))
  ;; Reflection warning, call to static method sleep on java.lang.Thread can't be resolved (argument types: java.lang.Integer).

  ;; does not work on 1.11.2
  ;; but works on 1.11.3
  (defn sleep [x] (Thread/sleep x))
  (sleep (Integer/valueOf 1))

  ;; 1.12.0-alpha10
  ;; 1. qualified methods
  ;; https://clojure.atlassian.net/browse/CLJ-2793
  ;; https://clojure.atlassian.net/browse/CLJ-2844
  ;; https://clojure.atlassian.net/browse/CLJ-2835

  ;; does not work on 1.11.x
  ;; works on 1.12.0-alpha10

  ;; class/static-methods
  ;; class/.instance-methods
  ;; class/constructor 

  ;; works on 1.11.x
  (map #(.toUpperCase %) ["a" "b" "c"])
  (map #(java.time.Instant/parse %) ["2024-01-01T00:00:00Z" "2024-01-01T00:00:00Z"])
  
  ;; works only on 1.12.0-alpha10
  (map String/.toUpperCase ["a" "b" "c"]) 
  (map Integer/parseInt ["1" "2" "3"])  
  (map String/new ["1" "2" "3"])
  (map java.time.Instant/parse ["2024-01-01T00:00:00Z" "2024-01-01T00:00:00Z"])

  ;; 2. param tags metadata
  ;; https://clojure.atlassian.net/browse/CLJ-2805
  ;; param tags can resolve overloaded methods (class/method)
  (^[String] java.net.URI/new "http://localhost")
  (^[long] java.net.URI/new "http://localhost") ;; Error - param-tags [long] insufficient to resolve constructor in class java.net.URI
  (java.net.URI/new "http://localhost")
  
  ;; 3. Array class syntax
  ;; before this version,
  ;; [[D for 2-dim primitive doubles
  ;; [Ljava.lang.String;" for 1-dim string
  ;; https://clojure.atlassian.net/browse/CLJ-2807
  (def double-array (make-array Double/TYPE 2 2))

  ;; Create a single-dimensional array of Strings
  (def string-array (into-array String ["hello" "world"]))

  ;; Print the class type of the arrays
  (println "Double array type:" (.getClass double-array)) ;; Double array type: [[D
  (println "String array type:" (.getClass string-array)) ;; String array type: [Ljava.lang.String;

  :rcf)

(comment
  (let [server (ServerSocket. 8085)]
    (loop [conn (.accept server)]
      (with-open [r (io/reader (.getInputStream conn))
                  w (io/writer (.getOutputStream conn))]
        (loop [line (.readLine r)]
          (when (seq (str/trim line))
            (recur (.readLine r))))
        (.write w "HTTP/1.1 200 OK\r\n\r\n"))
      (recur (.accept server))))
  :rcf)