(ns rag
  (:require [ragtacts.core :refer :all]
            [clojure.java.io :as io]))
(comment
  (System/setProperty "OPENAI_API_KEY" "")

  (ask "yo!")
  (-> [{:system "You are a wondrous wizard of math."}
       {:user "2+2"}
       {:ai "4"}
       {:user "2+3"}
       {:ai "5"}
       {:user "What's the square of a triangle?"}]
      ask
      last
      :ai)
  ;; "The phrase \"square of a triangle\" is a ..."


  



  (let [db (vector-store)]
    (add db ["The new data outside of the LLM's original training data set is called external data."
             "What Is RAG?"
             "The next question may be—what if the external data becomes stale?"
             "Retrieval-Augmented Generation (RAG) is the process of optimizing the output of a large language model."
             "The next step is to perform a relevancy search."
             "Recursive summarization as Context Summarization techniques provide a condensed view of documents"])
    (search db "Tell me about RAG"))
  ;; ("What Is RAG?" "Retrieval-Augmented Generation (RAG) is the process of optimizing the output of a large language 
  ;;  model." "Recursive summarizat...)
  :rcf)