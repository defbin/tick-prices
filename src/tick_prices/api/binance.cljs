(ns tick-prices.api.binance
  (:require [cljs-http.client :as http]))

(defn get-price! [symbol]
  (http/get "https://api.binance.com/api/v3/ticker/price"
            {:query-params      {:symbol symbol}
             :with-credentials? false}))
