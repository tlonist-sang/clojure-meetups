(ns clojure-meetup-4 
  (:require [clojure.tools.deps.interop :as itrp]))

(comment
  ;; #####################################################################################################
  ;; #  CLJ-2853 Reflection error incorrectly reported target object type, not qualifying class          #
  ;; #####################################################################################################
  
  ;; Problem
  (Object/.add (java.util.ArrayList.) 1)
  ;; Qualified methods that fail to resolve reflectively report errors in relation to the class of the instance target instead of the qualifying class (when there is one).
  
  ;; before => ; No matching method add found taking 1 args for class java.util.ArrayList
  ;; after => ; No matching method add found taking 1 args for class java.lang.Object 
  

  ;; ####################################################################################################
  ;; #  CLJ-2859 Expand scope of FI adapting to include Supplier (and other 0 arg FI)                  #
  ;; #. FI = function interfaces, @FunctionalInterface. Interface with only one abstract method.        #
  ;; ####################################################################################################
  
  ;; Problem
  ;; FI adapting released in 1.12.0-alpha12 does not adapt 0-arg FIs such as Supplier, because 0 arg methods are not functional. 
  ;; For “value suppliers” we instead found IDeref to be a semantic match for a value provider interface and in [CLJ-2792: Java Supplier interop
  ;; CLOSED] we extended IDeref to implement Supplier (and family).
  
  ;; Solution
  ;; support Supplier and other 0-arg FIs
  
  (import '(java.util.function Function Supplier))
  (import '(java.lang ThreadLocal))
  
  (defn get-value [] "Hello from ThreadLocal")

  
  ;; 1.12.0-alpha12 => Cannot cast clojure_meetup_4$get_value to java.util.function.Supplier
  ;; 1.12.0-beta1   => IDeref was extended to support Supplier directly
  (def thread-local (ThreadLocal/withInitial get-value))
  (.get thread-local)


  ;; ############################################################################################
  ;; # CLJ-2858 Fix encoding of FnInvoker method for prim-returning FIs with arity > 2          #
  ;; ############################################################################################
  
  ;; Problem
  ;; This would fail to compile or throw a runtime error
  ;; Because primitive returns are supported 'only' for arity 1-2
  ;; For arity 3-10, only boolean types or Object types are supported
  
  ;; Solution
  ;; For functions with arity>2, their return types can only be objects OR booleans.
  (require '[clojure.test :refer :all])
  (import '(clojure.lang FnInvokers))

  (defn long-returning-fn [a b c]
    (+ (long a) (long b) (long c)))

  
  (let [method (.getMethod (class long-returning-fn) "invoke" (into-array Class [Object Object Object]))]
          ;invoker (FnInvokers/getInvoker method)]
    method
    #_(is (= (class invoker) clojure.lang.FnInvokers))
    #_(is (= (.getSimpleName (class invoker)) "InvokeOOOO"))  ; This is the key test
    #_(is (= 6 (invoker long-returning-fn 1 2 3))))


  ;; #############################################################################
  ;; # CLJ-2864 Stop using truthy return logic in FI adapters                    #
  ;; #############################################################################
  
  ;; Problem
  ;; FI adapter returns inserted Clojure truthy return logic.
  ;; This is at odds with adapter implementation for FI/SAM(single abstract method) types in the future
  
  ;; Define a functional interface with a boolean return
  
  ;; Define a simple functional interface with a boolean return
  

  ;; ########################################################################
  ;; # CLJ-2863 Reflective FI dynamic proxy should use runtime classloader  #
  ;; ########################################################################
  

  ;; ##############################################################################################
  ;; # CLJ-2770 invoke-tool - remove external process name parameter (this is a runtime property) #
  ;; ##############################################################################################
  
  ;; Problem 
  ;; invoke-tool should not take a command paramter, because it is a runtime property.
  ;; https://clojure.github.io/clojure/branch-master/clojure.tools.deps.interop-api.html#clojure.tools.deps.interop/invoke-tool
  
  ;; Solution
  ;; invoke-tool does not take the command parameter anymore.
  
  (require '[clojure.tools.deps.interop :as itrp])
  (itrp/invoke-tool {:tool-alias ""
                     :tool-name ""

                     ;; not allowed anymore!!
                     :command ["java" "-version"]})


  ;; ##############################################################################################
  ;; # CLJ-2645 PrintWriter-on now supports auto-flush, and prepl uses it for the err stream.     #
  ;; ##############################################################################################
  
  ;; Problem
  ;; Solution
  
  ;; Example
  
  ;; before =>
  ;; 
  
  ;; after =>
  ;; {:tag :ret, :val "nil", :ns "user", :ms 10, :form "(binding [*out* *err*] (flush))"}
  
  ;; ##############################################################################################
  ;; # CLJ-2698 defprotocol - ignore unused primitive return type hints                           #
  ;; ##############################################################################################
  
  ;; Problem
  ;; Protocols make a universal call site which have a uniform return type (Object), but defprotocol allows hinting with primitive types, which can result in ASM exceptions during compilation when the reported function type differs from actual.
  
  ;; Solution
  ;; Ignore unused primitive return type hints in defprotocol.
  

  ;; Example
  (defprotocol SomeProtocol
    (^double p1 [p a])
    (^double p2 [p a]))

  ;; before => 
  ;; Syntax error (ArrayIndexOutOfBoundsException) compiling fn* at (src/clojure_meetup_4.clj:105:3).
  ;; Because double takes 2 slots (8 bytes), whereas object takes 1 slot. Hence the mismatch.
  (let [p (reify SomeProtocol)]
    (min 0.0
         (case :p1
           :p1 (p1 p :foo)
           :p2 (p2 p :foo))))

  ;; after =>
  ;; Receiver class clojure_meetup_4$eval8885$reify__8886 does not define or inherit an implementation of the resolved method 'abstract java.lang.Object p1(java.lang.Object)' of interface clojure_meetup_4.SomeProtocol.
  ;; reifying the protocol removes the error!
  (let [p (reify SomeProtocol (p1 [this arg] 42.0))]
    (min 0.0
         (case :p1
           :p1 (p1 p :foo)
           :p2 (p2 p :foo))))

  ;; ##############################################################################################
  ;; # CLJ-1385 transient - include usage model from reference docs                               #
  ;; ##############################################################################################
  
  ;; Problem
  ;; The docstrings of both `assoc!` and `conj!` say "Returns coll.", possibly suggesting the transient edit happens (always) in-place, `coll` being the first argument. However, this is not the case and the returned collection should always be the one that's used.
  
  ;; Solution
  ;; Reuse the good docs already at https://clojure.org/reference/transients and enhance the docstring for transient which is the starting place for all of the transient ops.
  
  :rcf)
