;; scanner.clj
;; The synesth scanner. Provides an interface, via `scan` with which one can 
;; generate a list of AudioFiles from a path.

(ns synesth.scanner
  (:import [org.jaudiotagger.audio SupportedFileFormat]
           [java.util.concurrent Executors]
           )
  (:require [synesth.util :as util] 
            [synesth.audiofile :as audiofile]))

(defn file-formats []
  "Return a list of valid AudioFile suffixes."
  (map #(. % getFilesuffix) (. SupportedFileFormat values))) 

(defn is-audio-file [file]
  "Returns true if file is a valid AudioFile (based on file extension)"
  (let  [filename   (.getName file)
         pattern    (re-pattern 
                      (format ".*\\.(%s)" 
                              (clojure.string/join "|" (file-formats))))]
    (if (re-matches pattern filename) true false)))

(defn scan [path]
  "List of all the files in path."
   (map audiofile/create-audiofile 
        (util/walk-pred is-audio-file path)))
