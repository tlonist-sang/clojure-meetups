(ns clojure-meetup-5.1.4)

(comment
  ; SerializationUID
  ; 직렬화 과정에서 객체의 버전을 구분하기 위한 고유 식별자

  ; Some libraries depended on serialization format of keyword instances. 
  ; Hence update on the serialization UID for instances with keyword were REVERTED and PINNED. 
  :rcf)