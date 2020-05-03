(ns tick-prices.state.effects
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer [<! take!]]
            [re-frame.core :as rf]
            [tick-prices.api.binance :refer [get-price!]]))

(defonce timer
  (let [timer-ids (atom {})]
    (fn handler [[cmd & opts]]
      (condp = cmd
        :stop-all
        (doseq [timer-id (keys @timer-ids)]
          (handler [:interval :stop timer-id]))

        :start
        (let [[id frequency event] opts]
          (swap! timer-ids assoc id (js/setInterval #(rf/dispatch event) frequency)))

        :stop
        (let [id (first opts)]
          (js/clearInterval (get @timer-ids id))
          (swap! timer-ids dissoc id))))))

(timer [:stop-all])

(rf/reg-fx
  :interval
  timer)

(rf/reg-fx
  :fetch-tick-price
  (fn [symbol]
    (go
      (let [{:keys [success body]} (<! (get-price! symbol))]
        (if success
          (rf/dispatch [:on-tick-price-success {:price (js/Number (:price body))}])
          (rf/dispatch [:on-tick-price-error]))))))
