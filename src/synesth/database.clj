;; database.clj
;; Interface to MongoDB
(ns synesth.database
  (:use     [somnium.congomongo :as db]
            [somnium.congomongo.config :only [*mongo-config*]])
  (:require [synesth.util :as util]))

;; Lower-level database functions
;; @TODO: Connect to other databases
(def conn
  (db/make-connection "synesth-dev-db"
                      :host "127.0.0.1"
                      :port 27017))

(defn drop-collections [& colls]
  "Drop all the collections."
  (db/with-mongo conn
    (dorun (db/drop-coll! colls))))

(defn drop-db []
  "Destroy the database and all collections."
  (db/with-mongo conn
    (db/drop-database! "synesth-dev-db")))

(defn db-fetch [col & options]
  "Fetches documents from the database."
  (let  [query  (cons col (first options))]
    (db/with-mongo conn
      (apply db/fetch query))))

(defn library-fetch [& options]
  "Fetches documents from the configured library."
  (if options
    (db-fetch :library options)
    (db-fetch :library)))

(defn by-fields [fields & options]
  "Fetch from the configured library all documents with matching the 
query provided in the hash-map `fields."
  (apply library-fetch (into [:where fields] options)))

(defn is-unique [& {:keys [path artist title]}]
  (not (< 0 (by-fields  {:$or [
                                  {:path  path}
                                  {:$and [
                                          {:artist  artist}
                                          {:title   title }]}]} 
                          :count? true))))

(defn insert-audiofile [data] 
  "Insert an audiofile into the database." 
  (if (is-unique :path (:path data) :artist (:artist data) :title (:title data))
      (db/with-mongo conn
        (dorun
          (db/insert! :library data)))))
