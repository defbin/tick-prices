(ns tick-prices.util)

(defn split-by [pred xs]
  (let [[a b] (split-with (complement pred) (drop-while pred xs))]
    (if (seq a)
      (lazy-seq (cons a (split-by pred b)))
      ())))
