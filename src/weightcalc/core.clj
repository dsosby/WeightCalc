(ns weightcalc.core)

(defn floor
  "Given number n, returns the largest integer less than n. Optionally takes m to find largest integer of multiple m less than n"
  ([n] (Math/floor n))
  ([n m]
    (let [nfloored (floor n)]
      (first (filter #(zero? (mod % m)) (range nfloored (- nfloored m) -1))))))

(defn percent
  "Returns a percentage of the target weight, unrounded and not floored"
  [percentage]
  (fn [target] (* percentage target)))

(defn format-weight
  [real-weight]
  (max 45 (Math/round (floor real-weight 5))))

(def weights {:squat    [(constantly 45) (constantly 45) (percent 0.4) (percent 0.6) (percent 0.8) (percent 1) (percent 1) (percent 1)]
              :bench    [(constantly 45) (constantly 45) (percent 0.5) (percent 0.7) (percent 0.9) (percent 1) (percent 1) (percent 1)]
              :deadlift [(percent 0.4)   (percent 0.4)   (percent 0.6) (percent 0.85) (percent 1)]
              :press    [(constantly 45) (constantly 45) (percent 0.55) (percent 0.7) (percent 0.85) (percent 1) (percent 1) (percent 1)]
              :row      [(constantly 45) (constantly 45) (percent 0.55) (percent 0.7) (percent 0.85) (percent 1) (percent 1) (percent 1)]})

(defn get-sets
  "Returns a list of the weights to be lifted for the given exercise type and target weight"
  [exercise target-weight]
    (map #(format-weight (% target-weight)) (get weights exercise)))

(defn print-workout [workout]
  "Pretty print a table of the workout"
  (doseq [sets workout]
    (let [exercise (first sets)]
      (print (format "%20s" exercise))
      (doseq [s (rest sets)] 
        (print (format "%6d" s)))
      (println))))

(defn -main [& args]
  (if (or (odd? (count args)) (zero? (count args)))
    (println "Ummm...#FAIL. Use the form \"lein run exercise1 weight exercise2 weight ...\"")
    (let [exercises (partition 2 args)
          workout (map (fn [[exercise weight]] (cons exercise (get-sets (keyword exercise) (Integer/parseInt weight)))) exercises)]
      (print-workout workout))))
