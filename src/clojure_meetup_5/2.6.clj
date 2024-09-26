(ns clojure-meetup-5.2.6)

;; Param-tags metadata

;; When used as values, qualified methods supply only the class and method name, and thus cannot resolve overloaded methods.
;; Developers can supply :param-tags metadata on qualified methods to specify the signature of a single desired method, 'resolving' it.

;; In my understanding, param-tags can reduce the cognitive overhead of understanding which overloaded method is being called.
;; It does not necessarily prevent bugs or errors, but it can make the code more readable and maintainable.


(comment

  (import 'java.util.ArrayList)

  ;; Create an ArrayList (which implements List)
  (def my-list (ArrayList.))

  ;; Basic usage without param-tags
  ;; Confusion. Adding 0 to the list? or adding nil to zeroth position?
  (.add my-list 0)

  ;; Using below param-tags make it more explicit
  ;; It adds Object to the index position
  (. ^[int Object] my-list add 0 "World")
  
  ;; Using param-tags to call add(E e) explicitly
  ;; It adds Object to the end of the list
  (. ^[Object] my-list add "!")


  ;; Demonstrating with a function
  (defn insert-at-beginning [^{:param-tags [int Object]} lst item]
    (.add lst 0 item))

  (insert-at-beginning my-list "Start")
  (insert-at-beginning my-list 0)

  ;; Print the list to see the results
  (println my-list)


   ;; Outputs "loj"
  :rcf)