# lein-cloudbees

[![Git](https://app.soluble.cloud/api/v1/public/badges/1dcace3f-59fc-44fa-aae2-c49dfd13b8ad.svg?orgId=451115019187)](https://app.soluble.cloud/repos/details/github.com/michaelneale/lein-cloudbees?orgId=451115019187)  

The cloudbees deployment plugin. Deploy your ring apps to cloudbees PaaS trivially.
Of course your apps are not trivial, they are a special snowflake.

Currently uses war based deployment - but could be talked out of it to jetty if enough people nag me.

## Usage

install the plugin in your project:

	:plugins [lein-cloudbees "1.0.3"]

To run:

Create or cd into a ring based webapp project.
If you run

	 lein cloudbees

then a list of commands will be provided.

eg:

	lein cloudbees list-apps -- returns a list of your apps !
	lein cloudbees deploy -- will deploy your app.

For this to work, you will need cloudbees-api-key, cloudbees-api-secret (both of which you can get from grandcentral.cloudbees.com) and cloudbees-app-id (for example michaelvideo/sudoku2 - accountname/app name)
Use ~(slurp file) to read in your cloudbees credentials - DO NOT PUT THEM DIRECTLY IN project.clj.

If you use the cloudbees SDK - it will read the credentials from ~/.bees/bees.config as normal (bees.api.key and bees.api.secret entries in that file, specifically).

If your application is running in European region, override the default api endpoint by setting the `:cloudbees-api-url` key in the project map. e.g. `:cloudbees-api-url "https://api-eu.cloudbees.com/api"`.

## License

Copyright (C) 2011 Michael Neale

Distributed under the Eclipse Public License, the same as Clojure.
