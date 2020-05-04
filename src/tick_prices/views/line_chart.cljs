(ns tick-prices.views.line-chart
  (:require ["react-vis" :as rvis]
            [tick-prices.util :refer [split-by]]))

(defn- first-y [data]
  (:y (first (drop-while #(nil? (:y % :none)) data))))

(def y-axis-style {:line  {:stroke        "#333"
                           :strokeLinecap "square"}
                   :ticks {:stroke "#999"}
                   :text  {:stroke "none"
                           :fill   "#333"}})

(def x-axis-style (assoc-in y-axis-style [:text :fill] "none"))

(defn line-chart-preset [preset-x preset-y]
  [:> rvis/XYPlot
   {:width  600
    :height 300
    :margin {:left 100 :right 50}}
   [:> rvis/YAxis
    {:tickSizeInner 0
     :tickSizeOuter 3
     :style         y-axis-style}]
   [:> rvis/XAxis
    {:tickTotal     10
     :tickSizeInner 0
     :tickSizeOuter 3
     :style         x-axis-style}]
   [:> rvis/LineSeries
    {:data        [{:x 0 :y preset-y}
                   {:x preset-x :y preset-y}]
     :strokeWidth 0
     :style       {:fill "none"}}]])

(defn draw-line [data]
  [:> rvis/LineMarkSeries
   {:data        data
    :color       "#e47320"
    :strokeWidth 5
    :style       {:fill           "none"
                  :strokeLinejoin "round"
                  :strokeLinecap  "round"}}])

(defn line-chart [data & {:keys [preset-x preset-y]
                          :or   {preset-x 0
                                 preset-y 0}}]
  (conj (line-chart-preset preset-x (or (first-y data) preset-y))
        (for [line (split-by #(nil? (:y % :none)) data)]
          (vary-meta (draw-line line) assoc :key (gensym "draw-line-")))))
