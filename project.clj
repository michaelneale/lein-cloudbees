(defproject lein-cloudbees "1.0.4"
  :description "Cloudbees lein deployment plugin for deploying your special snowflake apps really really easily.
  See https://github.com/michaelneale/lein-cloudbees for docs and installation instructions.
  (when installed type lein cloudbees to see what you can do. For any ring based webapps."

  :url "https://github.com/michaelneale/lein-cloudbees"

  :dependencies [[org.clojure/clojure "1.4.0"]
                 [com.cloudbees/cloudbees-api-client "1.2.0"]
                 [lein-ring "0.7.1"]]

  :plugins [[lein-clojars "0.9.0"]]

  :eval-in-leiningen true
  )
