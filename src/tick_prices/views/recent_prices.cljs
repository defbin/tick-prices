(ns tick-prices.views.recent-prices)

(defn recent-prices [prices & {:keys [limit] :or {limit 10}}]
  [:div
   [:span "Recent prices"]
   (into [:ul]
         (for [{:keys [price]} (take limit prices)]
           ^{:key (gensym)} [:li price]))])
