(ns leiningen.cloudbees
    (:require [leiningen.ring.uberwar :as war]))

    (def endpoint "https://api.cloudbees.com/api")
    (def usage 
    "Usage:
    lein cloudbees list-apps
    lein cloudbees deploy
    lein cloudbees tail (tail log)
    lein cloubees restart
    lein cloudbees stop
    lein cloudbees start")

    (defn cb-client [project] (doto (new com.cloudbees.api.BeesClient 
                        endpoint 
                        (get project :cloudbees-api-key) 
                        (get project :cloudbees-api-secret) 
                        "xml" 
                        "1.0") (.setVerbose false)))

    (defn list-apps [project]       
        (doall (map (fn [info] (println (. info getId) " - " (. info getStatus))) (. (. (cb-client project) applicationList) getApplications)))
    )   

    ;; todo should have account in project, and pass in name from command line - secrets? 
    (defn deploy-app [project]
            (println "Deploying app to CloudBees, please wait....")
            (println (. 
                    (. (cb-client project) applicationDeployWar (get project :cloudbees-app-id) nil nil ".project.zip" nil nil)     
                getUrl))
            (println "Applcation deployed.")    
    )   



    (defn tail [project]
        (. (cb-client project) tailLog (get project :cloudbees-app-id) "server" (. System out))
    )

    (defn restart [project] (. (cb-client project) applicationRestart (get project :cloudbees-app-id) ))
    (defn stop [project] (. (cb-client project) applicationStop (get project :cloudbees-app-id) ))
    (defn start [project] (. (cb-client project) applicationStart (get project :cloudbees-app-id) ))

    (defn package-deploy [project]      
        (war/uberwar project ".project.zip")
        (deploy-app project)
    )
    

    (defn contains-element? [project key msg] 
        (if (contains? project key) true (println "ERROR: Missing project config item" key msg))
    )


    (defn validate [project]
        (and 
            (contains-element? project :cloudbees-api-key "- The api key is the id provided by cloudbees.")
            (contains-element? project :cloudbees-api-secret "- The api secret is the secret key from grandcentral.cloudbees.com")
            (contains-element? project :cloudbees-app-id "- The appid is the account name/app name: for example acme/appname"))
    )

    (defn dispatch [project command]
        (cond 
            (= command "list-apps") (list-apps project)
            (= command "deploy") (package-deploy project)
            (= command "tail") (tail project)
            (= command "restart") (restart project)
            (= command "stop") (stop project)
            (= command "start") (start project)
        )
    )
                    

    (defn cloudbees [project & args] 
        (if (validate project) 
            (if (= nil (first args)) (println usage) (dispatch project (first args)))   
            (println "\n")      
        )
    )   







    (defn cloudbees-old [project & args] 
        (println (doto (new java.util.HashMap) (.put "a" 1) (.put "b" 2)))) 

