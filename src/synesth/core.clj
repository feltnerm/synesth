;; core.clj
;; Contains main and any initial setup.
(ns synesth.core 
  (:use [clojure.tools.cli  :only (cli)])
  (:require [synesth.config  :as config]
            [synesth.plugins :as plugins] 
            [synesth.scanner :as scanner])
  (:gen-class))

(defn parse-cli [opts]
  "Parse a list of command-line arguments into a hash of argument keys and 
  values, a list of following options, and the help banner for this program."
  (cli opts 
       ["-h" "--help" "Show help" :default false :flag true]
       ["-v" "--[no-]verbose" "Verbosity" :default false :flag true]
       ["-c" "--config" "Path to configuration file." 
        :default "./resources/default.config"]))

(defn parse-command [args]
  "Parse the arguments following the command line options.
 Syntax: <command> [<options>]"
  [(first args) (rest args)])

(defn initialize-synesth [settings]
  (synesth.database/init-db 
    (:mongodb_database  (:database settings))
    (:mongodb_hostname  (:database settings))
    (:mongodb_port      (:database settings))))

(defn -main 
  "Main entry point for the synesth application."
  [& opts]
  (let  [[opts args banner] (parse-cli opts)]
    (when (:help opts)
      (println banner)
      (System/exit 0)) 
    (when args
      (let   [[command args]  (parse-command args)
              plugin          (plugins/find-plugin command)
              settings        (merge {} 
                                     (config/load-config "./resources/default.config") 
                                     (config/load-config (:config opts)))]
        (initialize-synesth settings)
        (if plugin
          (do
            (apply (:command plugin) args)))))))
