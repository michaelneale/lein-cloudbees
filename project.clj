(defproject lein-cloudbees "1.0.2-SNAPSHOT"
  :description "Cloudbees lein deployment plugin for deploying your special snowflake apps really really easily.
  See https://github.com/michaelneale/lein-cloudbees for docs and installation instructions.
  (when installed type lein cloudbees to see what you can do. For any ring based webapps."

  :url "https://github.com/michaelneale/lein-cloudbees"

  :dependencies [[org.clojure/clojure "1.2.1"]
                 [com.cloudbees/cloudbees-api-client "1.1.1"]
                 [lein-ring "0.4.5"]]

  :dev-dependencies [[lein-clojars "0.6.0"]]

  :eval-in-leiningen true
  )