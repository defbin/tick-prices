(ns tick-prices.prod
  (:require
    [tick-prices.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
