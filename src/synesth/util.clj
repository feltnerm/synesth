;; util.clj
;;;; File and Directory Utilities
(ns synesth.util)

(defn walk [path]
  "Recurse down a directory returing all files and dirs."
  (file-seq (clojure.java.io/file path)))

(defn walk-map [f path]
  "Recurse down a directory, calling f on each file and dir found."
  (map f (walk (clojure.java.io/file path))))

(defn walk-pred [p path]
  "Recurse down a directory, returning only files and dirs matching p"
  (filter p (walk (clojure.java.io/file path))))
