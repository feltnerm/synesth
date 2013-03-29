(ns synesth.plugins.impurt
  (:require [synesth.scanner :only (importer)])
  )

(def impurt 
  {:name "import"
   :command (fn [args]  (synesth.scanner/importer args))})
