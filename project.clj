(defproject leevee "0.1.0-SNAPSHOT"
  :description "A todo list CLI app based on Ivy Lee method, written in Clojure."
  :url "https://www.codemascot.com/leevee"
  :license {:name "EPL-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/tools.cli "1.0.194"]
                 [org.clojure/java.jdbc "0.7.5"]
                 [environ "1.2.0"]
                 [org.xerial/sqlite-jdbc "3.34.0"]
                 [mysql/mysql-connector-java "8.0.22"]]
  :plugins [[lein-environ "1.2.0"]]
  :repl-options {:init-ns leevee.core})
