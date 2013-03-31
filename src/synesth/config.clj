(ns synesth.config)

(defn load-config [filename]
  (with-open  [r (clojure.java.io/reader filename)]
    (read (java.io.PushbackReader. r))))
