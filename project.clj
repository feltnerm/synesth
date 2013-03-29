(defproject synesth "0.1.0-SNAPSHOT"
  :description "Audio cataloging toolkit in clojure"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/tools.cli "0.2.2"]
                 [org.clojure/tools.namespace "0.2.2"]
                 [org/jaudiotagger "2.0.3"]
                 [congomongo "0.4.1"]
                 ]
  :dev-dependencies [[lein-run  "1.0.0"]]

  :main synesth.core 
)
