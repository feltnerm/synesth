;; audiofile.clj
;; Represents an audiofile and the operations that are performed on one.

(ns synesth.audiofile
  (:import  (java.util Date)
            (org.jaudiotagger.audio AudioFileIO)
            (org.jaudiotagger.tag FieldKey)))

(defn fields []
  "Return a hashmap of valid AudioFile fields."
  (apply conj {} 
         (map (fn [n] [(keyword (. (. n toString) toLowerCase)) n]) 
              (. FieldKey values))))

;; AudioFile manipulation
(defn headers [file]
  "Return the header information of an AudioFile."
  (bean (. file (getAudioHeader))))

(defn metadata [file]
  "File metadata"
  {:path (list (. file (getAbsolutePath)))
    :mtime (list (new Date (.lastModified file))) 
    :stime (list (new Date))})

(defn tags [file]
 "Return an AudioFile's tags." 
  (if-let  [tag  (. file (getTag))]
    (apply conj {}
           (filter (fn [[k v]] (and v (not (empty? v))))
                   (map (fn [[k v]] 
                          [k (seq (map (fn [fieldkey]
                                         (if-not (or
                                                   (. fieldkey isEmpty)
                                                   (. fieldkey isBinary))
                                         (. fieldkey getContent)))
                                       (. tag (getFields v))))])
                        (fields))))))

(defn make-audiofile [file]
  (let  [audiofile (AudioFileIO/read file)]
    (conj (metadata file) (headers audiofile) (tags audiofile))))
