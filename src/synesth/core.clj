;; core.clj
;; Contains main and any initial setup.
(ns synesth.core 
  (:require [synesth.importer :as importer])
  (:gen-class))

(defn -main 
  "Main entry point for the synesth application."
  [& [args]]
  (if args
    (importer/importer args)  
    (println "Usage: synesth '/path/to/music'")))
