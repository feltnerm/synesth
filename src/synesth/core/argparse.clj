(ns synesth.core.argparse
  (:use [clojure.tools.cli :only (cli)]))

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

(defn parse-args [raw-args]
 (let [[opts args banner]         (parse-cli raw-args)]
   [opts args banner (parse-command args) ]))
