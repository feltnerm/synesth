;; main.clj 
(ns synesth.main
  (:require [synesth.core.confparse :as confparse]
            [synesth.core.argparse :as argparse])
  (:gen-class))

(defn -main 
  "Main entry point for the synesth application."
  [& raw-args]
  (let [[opts args banner command]  (argparse/parse-args raw-args)
        config                      (confparse/parse-config (get opts :config nil))
        settings                    (merge opts config)
        ;;settings            (make-settings config opts args)
       ]
    (when (:help opts)
      (println banner)
      (System/exit 0))
    (when settings
      [settings command])))
