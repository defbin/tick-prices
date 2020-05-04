(ns tick-prices.state.handlers
  (:require [re-frame.core :as rf]))

(rf/reg-event-db
  :app/init
  (constantly
    {:prices   []
     :interval     10000
     :recent-limit 10}))

(defn- on-tick-price-done [db value]
  (-> db
      (update :prices conj value)
      (update :prices (partial take 20))))

(rf/reg-event-db
  :on-tick-price-success
  (fn [db [_ {:keys [price]}]]
    (on-tick-price-done db {:price (js/Number price)})))

(rf/reg-event-db
  :on-tick-price-error
  (fn [db _]
    (on-tick-price-done db {:price nil})))

(rf/reg-event-fx
  :tick-price
  (fn [_ [_ symbol]]
    {:fetch-tick-price symbol}))

(rf/reg-event-fx
  :interval
  (fn [{{:keys [interval]} :db} [_ cmd id event]]
    {:interval [cmd id interval event]}))
