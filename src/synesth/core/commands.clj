(ns synesth.core.commands
  
  (:require [bultitude.core :as b])
  )

(defn commands
  []
  (->>  (b/namespaces-on-classpath :prefix "synesth")
        (filter #(re-find #"^synesth\. (?!core|main|util)[^\.]+$" (name %)))
        (distinct)
        (sort)))
