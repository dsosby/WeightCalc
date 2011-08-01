(ns weightcalc.test.core
  (:use [weightcalc.core])
  (:use [clojure.test]))

(deftest test-floor
  (is (zero? (floor 0.5)) "Floor 0.5")
  (is (zero? (floor 0)) "Floor zero")
  (is (= (floor 6.5) 6) "Floor 6.5")
  (is (= (floor -0.5) -1) "Floor -.5")
  (is (= (floor 6 5) 5) "Floor 6, 5")
  (is (= (floor 5 5) 5) "Floor 5, 5")
  (is (= (floor -7 5) -10) "Floor -7, 5")
)

(deftest test-format-weight
  (is (= (str (format-weight 32)) "45") "format-weight returns pretty string and minimum weight")
  (is (= (format-weight 47) 45) "format-weight returns correct weight close to min")
  (is (= (format-weight 65.5) 65) "format-weight returns proper rounding close to multiple")
  (is (= (format-weight 68.99) 65) "format-weight returns proper rounding")
  (is (= (format-weight 75) 75) "format-weight returns itself if appropriate")
)
