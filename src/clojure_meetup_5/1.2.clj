(ns clojure-meetup-5.1.2)

(comment
  ;; Virtual Thread pinning
  ;; 1. Lazy sequences in Clojure previously used synchronized blocks to guarantee run-once behavior. 
  ;;    This is crucial because evaluating lazy sequences multiple times is inefficient and redundant.

  ;; 2. Java 21 's virtual threads participate in cooperative blocking. 
  ;;    This means that a virtual thread spending most of its time waiting (e.g., for I/O operations) yields its carrier thread so that it can be used by other virtual threads.

  ;; 3. Using synchronized blocks with virtual threads pins (or fixates) the carrier thread. 
  ;;    In other words, when a virtual thread enters a synchronized block, it does not yield its carrier thread to be shared among other virtual threads.
  
  ;; 4. Switching to locks instead of synchronized blocks can avoid this issue, allowing for better cooperation among virtual threads.
  :rcf)