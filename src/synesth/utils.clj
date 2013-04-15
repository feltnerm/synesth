;; util.clj
(ns synesth.utils
  (:require    [clojure.java.io :as io])
  (:import (org.jaudiotagger.tag FieldKey)))

;; namespace utils
(defn ns-exists? [namespace]
  (some (fn [suffix]
          (-> (#'clojure.core/root-resource namespace)
              (subs 1)
              (str suffix)
              io/resource))
        [".clj" (str clojure.lang.RT/LOADER_SUFFIX ".class")]))

(defn require-resolve
  "Resolve a fully qualified symbol by first requiring its namespace."
  ([sym]
     (when-let [ns (namespace sym)]
       (when (ns-exists? ns)
         (let [ns (symbol ns)]
           (when-not (find-ns ns)
             (require ns)))
         (resolve sym))))
  ([ns sym] (require-resolve (symbol ns sym))))

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

