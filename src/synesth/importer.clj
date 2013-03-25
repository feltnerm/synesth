(ns synesth.importer
  (:require [synesth.database :as database]
            [synesth.scanner  :as scanner]))

(defn importer [path]
  "Import AudioFiles found in `path` to database."
  (if (.exists (clojure.java.io/file path))
    (map database/insert-audiofile (scanner/scan path))))

