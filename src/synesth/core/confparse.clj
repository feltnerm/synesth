;; core/confparse.clj
;;
;; Functions for working with a configuration file.
(ns synesth.core.confparse)

(def default-config-paths
  ["/etc/synesth/synesth.conf" 
   (str "/home/" (System/getenv "USER") "/.synesth/synesth.conf")])

(defn read-config-file [filename]
  "Open a config file as a hash-map."
  (when (.exists (clojure.java.io/file filename))
    (with-open  [r (clojure.java.io/reader filename)]
      (read (java.io.PushbackReader. r)))))


(defn parse-config [extra-config]
  "Return a hash-map of parsed configuration file(s) including the extra
  configuration file provided as an argument."
  (apply conj {} (map read-config-file (concat default-config-paths [extra-config]))))
