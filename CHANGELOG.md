== Changes between versions 1.0.4 and 1.0.5

Version 1.0.5 introduces no breaking change.

=== Features

- Addition of a `project.clj` key, `:cloudbees-api-url`, for configuring api endpoints beyond the default one

    ```clojure
    (defproject
      ...
      ; for European region
      :cloudbees-api-url "https://api-eu.cloudbees.com/api"
      ... )
    ```
