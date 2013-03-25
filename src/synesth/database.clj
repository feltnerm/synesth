;; database.clj
;; Interface to MongoDB
(ns synesth.database
  (:require [somnium.congomongo :as db]))

(def conn
  (db/make-connection "synesth-dev-db"
                      :host "127.0.0.1"
                      :port 27017))

(defn drop-collections []
  "Drop all the collections."
  (db/with-mongo conn
    (db/drop-coll! "items")))

(defn drop-all []
  "Destroy the database and all collections."
  (db/with-mongo conn
    (db/drop-database! "synesth-dev-db")))

(defn insert-audiofile [data] 
  "Insert an audiofile into the database." 
    (db/with-mongo conn
      (dorun 
        (db/insert! :items data))))
