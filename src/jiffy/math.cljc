(ns jiffy.math)

;; TODO: find proper value for min and max

(def long-max-value 9223372036854775807)
(def long-min-value -9223372036854775808)

;; TODO: check correctness

(defn add-exact [& args]
  (apply + args))

(defn floor-div [x y]
  (long (/ x y)))

(defn floor-mod [x y]
  (mod x y))

(defn multiply-exact [x y]
  (* x y))

(defn subtract-exact [x y]
  (- x y))
