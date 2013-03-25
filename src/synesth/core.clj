;; core.clj
;; Contains main and any initial setup.
(ns synesth.core 
  (:require [synesth.scanner])
  (:gen-class))

(defn -main 
  "Main entry point for the synesth application."
  [& [args]]
  (if args
    (synesth.scanner/scan args)  
    (println "Usage: synesth '/path/to/music'")))
