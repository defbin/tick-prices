(ns tick-prices.state.subs
  (:require [re-frame.core :as rf]))

(rf/reg-sub
  :price
  (fn [db _]
    (:prices db)))

(rf/reg-sub
  :recent-limit
  (fn [db _]
    (:recent-limit db)))


(defn prices []
  (rf/subscribe [:price]))

(defn recent-limit []
  (rf/subscribe [:recent-limit]))
