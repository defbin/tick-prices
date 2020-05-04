(ns tick-prices.core
  (:require [reagent.dom :as d]
            [tick-prices.views.app :refer [app]]
            [tick-prices.state.core :as state]))

(defn mount-root []
  (d/render [app] (.getElementById js/document "app")))

(defn ^:export init []
  (state/init)
  (mount-root))
