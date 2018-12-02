
(ns aoc.two
  (:require-macros lumo.util)
  (:require [clojure.string :as string]
            [clojure.set :as set]
            [cljs.js :as cljs]
            [cljs.compiler :as comp]
            [cljs.reader :as edn]
            [goog.string :as gstring]
            [lumo.io :as io]
            fs
            path
            os))

(def input
  (string/split
;    (io/slurp "./example_input2.txt")
   (io/slurp "./input2.txt")
   #"\n"))

(defn repeatCount [id n]
  (not
   (empty?
    (filter
     (fn [[v c]] (= c n))
     (frequencies (string/split id ""))))))

(defn checksum []
  (*
   (count
    (filter
     #(repeatCount % 2)
     input))
   (count
    (filter
     #(repeatCount % 3)
     input))))


(def inputvecs
  (map
   #(string/split % "")
   input))
; (println (checksum))
;pt 2: 
(defn matching [a b]
  (filter #(apply = %)
          (map vector a b)))

(defn checkBox [i]
  (map #(matching i %)
       inputvecs))

(def c (count (first  inputvecs)))

(def matches
  (filter
   #(= (dec c) (count %))
   (mapcat checkBox inputvecs)))


(println (apply str (map first (first matches))))
