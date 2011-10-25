(ns leiningen.cloudbees
  (:use [leiningen.help :only (help-for)])
  (:require [leiningen.ring.uberwar :as war]))

(def endpoint "https://api.cloudbees.com/api")

(defn- cb-client [project]
  (doto (new com.cloudbees.api.BeesClient
          endpoint
          (:cloudbees-api-key project)
          (:cloudbees-api-secret project)
          "xml"
          "1.0") (.setVerbose false)))

(defn list-apps
  "List the current applications deployed to CloudBees."
  [client project]
  (doall (map
           (fn [info] (println (. info getId) " - " (. info getStatus)))
           (. (. client applicationList) getApplications))))

;; todo should have account in project, and pass in name from command line - secrets?
(defn- deploy-app
  [client project]
  (println "Deploying app to CloudBees, please wait....")
  (println (.
             (. client applicationDeployWar (:cloudbees-app-id project) nil nil ".project.zip" nil nil)
             getUrl))
  (println "Applcation deployed."))

(defn deploy
  "Deploy the ring application to CloudBees."
  [client project]
  (war/uberwar project ".project.zip")
  (deploy-app client project))

(defn tail
  "Tail the runtime log of the deployed application."
  [client project]
  (. client tailLog (:cloudbees-app-id project) "server" (. System out)))

(defn restart
  "Restart the deployed application."
  [client project]
  (. client applicationRestart (:cloudbees-app-id project)))

(defn stop
  "Stop the deployed application."
  [client project]
  (. client applicationStop (:cloudbees-app-id project)))

(defn start
  "Start the deployed application."
  [client project]
  (. client applicationStart (:cloudbees-app-id project)))

(defn- contains-element? [project key msg]
  (if (contains? project key) true (println "ERROR: Missing project config item" key msg)))

(defn- validate [project]
  (and
    (contains-element? project :cloudbees-api-key "- The api key is the id provided by cloudbees.")
    (contains-element? project :cloudbees-api-secret "- The api secret is the secret key from grandcentral.cloudbees.com")
    (contains-element? project :cloudbees-app-id "- The appid is the account name/app name: for example acme/appname")))

(defn cloudbees
  "Manage a ring-based application on Cloudbees."
  {:help-arglists '([list-apps deploy tail restart stop start])
   :subtasks [#'list-apps #'deploy #'tail #'restart #'stop #'start]}
  ([project]
    (println (help-for "cloudbees")))
  ([project subtask & args]
    (if (validate project)
      (let [client (cb-client project)]
        (case subtask
          "list-apps" (apply list-apps client project args)
          "deploy" (apply deploy client project args)
          "tail" (apply tail client project args)
          "restart" (apply restart client project args)
          "stop" (apply stop client project args)
          "start" (apply start client project args))))))
