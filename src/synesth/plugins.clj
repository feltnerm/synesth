(ns synesth.plugins
  (:use   [clojure.tools.namespace.find :only (find-clojure-sources-in-dir)]))

(defn find-plugins [dir]
  (find-clojure-sources-in-dir (clojure.java.io/file dir)))

(defn default-plugins [] 
  (find-plugins "./src/synesth/plugins"))

(defn load-plugin [file]
  """Load a plugin from `path and return its value."
  (var-get (load-file (. file getAbsolutePath))))

(defn load-from-path  [paths]
  (map load-plugin (find-plugins paths)))

(defn load-defaults []
  (map load-plugin (default-plugins)))
