
(ns aoc.three
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
  ;  (io/slurp "./example_input3.txt")
   (io/slurp "./input3.txt")
   #"\n"))


(defn parse-line [line]
  (let  [[loc size] (string/split line ": ")
         [_ loc] (string/split loc "@ ")
         [left top] (map #(js/parseInt % 10) (string/split loc ","))
         [width height] (map #(js/parseInt % 10) (string/split size "x"))]
    [[left width] [top height]]))


(def claims (map parse-line input))


(defn in-range [x [s l]]
  (and
   (>= x s)
   (< x (+ s l))))

; (println (in-range 5 [3 1]))
; (println (in-range 13 [3 10]))

(defn on-claim [[px py] [cx cy]]
  (and
   (in-range px cx)
   (in-range py cy)))

; (println)
;  (parse-line (first input)))

(def width
  (apply max
         (map
          #(apply + %)
          (map first claims))))

(def height
  (apply max
         (map
          #(apply + %)
          (map second claims))))


(println width)
(println height)

; (println
;  (on-claim [1 4] (first claims)))


(def squares
  (for [x (range (inc width))
        y (range (inc height))]
    [x y]))

(defn has-two-elements [seq]
  (< 1 (count (take 2 seq))))

(defn double-claimed [pos]
  (has-two-elements
   (filter
    #(on-claim pos %)
    claims)))



; (println
;  (double-claimed [3 2]  claims))

;pt 1:
; (println
;  (count (filter double-claimed  squares)))

;pt2:
(defn claim-squares [[[l w] [t h]]]
  (for [x (range l (+ l w))
        y (range t (+ t h))]
    [x y]))

(defn clean-claim [claim]
  (not
   (has-two-elements
    (filter
     double-claimed
     (claim-squares claim)))))

(println (count claims))

(println
 (filter
  (fn [[i claim]]
    (cond (= (mod i 100) 0) (println i))
    (clean-claim claim))
  (map-indexed vector claims)))
