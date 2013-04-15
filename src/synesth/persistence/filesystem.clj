;; core/filesystem.clj
;;
;; Low-level interface to the filesystem. Used in finding audiofiles
;; and eventually tagging them.
(ns synesth.core.filesystem
  (:import  [org.jaudiotagger.audio SupportedFileFormat])
  )

(def file-formats
  "Return a list of valid AudioFile suffixes."
  (map #(. % getFilesuffix) (. SupportedFileFormat values))) 

(defn is-audio-file [file]
  "Returns true if file is a valid AudioFile (based on file extension)"
  (let  [filename   (.getName file)
         pattern    (re-pattern 
                      (format ".*\\.(%s)" 
                              (clojure.string/join "|" file-formats)))]
    (if (re-matches pattern filename) true false)))

(defn walk [path]
  "Recurse down a directory returing all files and dirs."
  (file-seq (clojure.java.io/file path)))

(defn walk-map [f path]
  "Recurse down a directory, calling f on each file and dir found."
  (map f (walk (clojure.java.io/file path))))

(defn walk-pred [p path]
  "Recurse down a directory, returning only files and dirs matching p"
  (filter p (walk (clojure.java.io/file path))))

(defn scan [path]
  "List of all the files in path."
   (map audiofile/create-audiofile 
        (util/walk-pred is-audio-file path)))

