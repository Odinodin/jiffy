(ns jiffy.dev.duration-impl
  (:require [jiffy.dev.wip :refer [wip]]
            [jiffy.local-time-impl :refer [NANOS_PER_SECOND NANOS_PER_DAY SECONDS_PER_DAY SECONDS_PER_MINUTE SECONDS_PER_HOUR SECONDS_PER_MINUTE NANOS_PER_MILLI MINUTES_PER_HOUR]]
            [jiffy.math :as math]))

(defrecord Duration [seconds nano-of-second])

(def ZERO (->Duration 0 0))

(defn create
  ([big-decimal-seconds] (wip ::create--not-implemented))
  ([seconds nano-adjustment]
   (if (zero? (bit-or seconds nano-adjustment))
     ZERO
     (->Duration seconds nano-adjustment))))

(defn ofSeconds
  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L223
  ([seconds]
   (create seconds 0))

  ;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L246
  ([seconds nano-adjustment]
   (create (math/add-exact seconds (math/floor-div nano-adjustment NANOS_PER_SECOND))
           (int (math/floor-mod nano-adjustment NANOS_PER_SECOND)))))

;; https://github.com/unofficial-openjdk/openjdk/tree/cec6bec2602578530214b2ce2845a863da563c3d/src/java.base/share/classes/java/time/Duration.java#L280
(defn ofNanos [nanos]
  (let [nos (int (mod nanos NANOS_PER_SECOND))
        secs (cond-> (long (/ nanos NANOS_PER_SECOND)) (neg? nos) dec)
        nos (cond-> nos (neg? nos) (+ NANOS_PER_SECOND))]
    (create secs nos)))
