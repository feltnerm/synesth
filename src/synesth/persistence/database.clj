;; database.clj
;; Interface to MongoDB
(ns synesth.database
  (:require [monger.core  :as mg]
            [synesth.util :as util])
  (:import  [com.mongodb MongoOptions ServerAddress]))
