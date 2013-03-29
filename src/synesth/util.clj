;; util.clj
(ns synesth.util
 (:import (org.jaudiotagger.tag FieldKey)))

;; JAudioTagger utilities
(defn fields []
  "Return a hashmap of valid AudioFile fields."
  (apply conj {} 
         (map (fn [n] [(keyword (. (. n toString) toLowerCase)) n]) 
              (. FieldKey values))))

;; Regex Functions
;; String->Regex 
(defn re [value]           (re-pattern value))
(defn like [value]         (re (format "%s" value)))
(defn ends-with [value]    (re (format "%s$" value)))
(defn starts-with [value]  (re (format "^%s" value)))
(defn ignore-case [regex]  (re (format "%s(?i)" (. regex toString))))
(defn fuzzy-search [value] (re (ignore-case (like (format "%s" value)))))

;; File and Directory Utilities
(defn walk [path]
  "Recurse down a directory returing all files and dirs."
  (file-seq (clojure.java.io/file path)))

(defn walk-map [f path]
  "Recurse down a directory, calling f on each file and dir found."
  (map f (walk (clojure.java.io/file path))))

(defn walk-pred [p path]
  "Recurse down a directory, returning only files and dirs matching p"
  (filter p (walk (clojure.java.io/file path))))
