;; cor e.clj
;; Contains main and any initial setup.
(ns synesth.core 
  (:use [clojure.tools.cli  :only (cli)])
  (:require [synesth.plugins :as plugins] 
            [synesth.scanner :as scanner])
  (:gen-class))

(defn parse-cli [[opts]]
  "Parse a list of command-line arguments into a hash of argument keys and 
values, a list of following options, and the help banner for this program."
  (cli opts 
       ["-h" "--help" "Show help" :default false :flag true]
       ["-v" "--[no-]verbose" "Verbosity" :default false :flag true]
       ["-c" "--config" "Path to configuration file." :default "/etc/synesth"]
    ))

(defn parse-command [args]
  "Parse the arguments following the command line options.
  Syntax: <command> [<options>]"
  [(first args) (rest args)])

(defn -main 
  "Main entry point for the synesth application."
  [& opts]
  (let  [[opts args banner] (parse-cli opts)]
    (when (:help opts)
      (println banner)
      (System/exit 0))
    (when args
      (let  [[command args] (parse-command args)
             plugin        (first (filter #(= (clojure.string/lower-case command) (clojure.string/lower-case (:name %))) (plugins/load-defaults)))]
        (if plugin
          (apply (:command plugin) args))))))
