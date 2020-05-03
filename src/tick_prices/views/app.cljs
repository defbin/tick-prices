(ns tick-prices.views.app
  (:require [tick-prices.state.subs :as subs]
            [tick-prices.views.line-chart :refer [line-chart]]
            [tick-prices.views.recent-prices :refer [recent-prices]]))

(defn prices->xy [data]
  (map (fn [{:keys [price]} n] {:x n :y price})
       data
       (range 19 -1 -1)))

(defn app []
  (let [prices       (subs/prices)
        recent-limit (subs/recent-limit)]
    (fn []
      [:div
       [line-chart (prices->xy @prices) :preset-x 19]
       [recent-prices @prices :limit @recent-limit]])))

