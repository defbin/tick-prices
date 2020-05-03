(ns tick-prices.state.core
  (:require [re-frame.core :as rf]
            [tick-prices.state.subs]
            [tick-prices.state.handlers]
            [tick-prices.state.effects]))

(defn init []
  (rf/dispatch-sync [:app/init])
  (rf/dispatch [:tick-price "BTCUSDT"])
  (rf/dispatch [:interval :start :tick-price [:tick-price "BTCUSDT"]]))
