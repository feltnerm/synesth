;; audiofile.clj
;; Represents an audiofile and the operations that are performed on one.

(ns synesth.audiofile
  (:require [synesth.util :as util])
  (:import  (java.util Date)
            (org.jaudiotagger.audio AudioFileIO FieldKey)))

;; JAudioTagger utilities
(def fields
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
  {:path (. file (getAbsolutePath))
   :mtime (new Date (.lastModified file)) 
   :stime (new Date)})

(defn get-fieldcontent [fieldkey]
  (if-not (or (. fieldkey isEmpty) (. fieldkey isBinary))
    (let [content (. fieldkey getContent)]
      (if (and (sequential? content) (= 1 (count content)))
       (first content)
       content))))

(defn get-field [tag v]
  (let  [fieldcontent (map get-fieldcontent (. tag (getFields v)))]
    (if (= 1 (count fieldcontent))
      (first fieldcontent)
      (seq fieldcontent))))

(defn tags [file]
 "Return an AudioFile's tags." 
  (if-let  [tag  (. file (getTag))]
    (apply conj {}
           (filter (fn [[k v]] (and v (not (empty? v))))
                   (map (fn [[k v]] 
                          [k (get-field tag v)])
                        (util/fields))))))

(defn create-audiofile [file]
  (let  [audiofile (AudioFileIO/read file)
         data      (merge {} (metadata file) (headers audiofile) (tags audiofile))]
    (apply conj {}
           (filter (fn [[k v]] (not (class? v)))
                   (map #(if (seq? %) (first %) %) data)))))
