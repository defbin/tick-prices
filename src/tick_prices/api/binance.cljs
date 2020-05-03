(ns tick-prices.api.binance
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer [chan >!]]
            [cljs-http.client :as http]))

(defn get-price! [symbol]
  (http/get "https://api.binance.com/api/v3/ticker/price"
            {:query-params      {:symbol symbol}
             :with-credentials? false}))

(comment)
(defn- next-price! [lv]
  (let [op  (cond
              (< 30 lv 70) (rand-nth [+ -])
              (>= 30 lv) +
              (<= 70 lv) -)
        rnd (rand-nth [0 0 1 2 3])]
    (op lv rnd)))

(defonce get-price!
  (let [price (atom 50)]
    (fn [_]
      (let [new-price (swap! price next-price! @price)
            response  {:body    {:price new-price}
                       :success (or true (not (zero? (rand-int 2))))}]
        (go response)))))
