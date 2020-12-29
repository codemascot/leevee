(ns leevee.cli
  ""
  (:require [clojure.string :as str]
            [clojure.tools.cli :refer [parse-opts]])
  (:gen-class))

(def cli-options
  [["-d" "--day DATE" "Date in yyyy-mm-dd format"]
   ["-t" "--task TASK ID" "The exact task ID"]
   ["-o" "--order TASK ORDER" "The task order for a specific day"]])

(defn usage [options-summary]
  (->> ["Usage: leevee [options] action"
        ""
        "Options:"
        options-summary
        ""
        "Actions:"
        "  add    Add new task"
        "  list   List the tasks"
        "  edit   Edit a task"
        "  delete Delete a task"
        ""
        "This is a Clojure CLI implementation of Ive Lee method"
        "of personal productivity where you have to list every"
        "evening 6 tasks for tomorrow and you'll complete"
        "them one after another. But you can't skip any"
        "task for it's next one."]
       (str/join \newline)))

(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (str/join \newline errors)))

(defn validate-args
  "Validate command line arguments. Either return a map indicating the program
  should exit (with a error message, and optional ok status), or a map
  indicating the action the program should take and the options provided."
  [args]
  (let [{:keys [options arguments errors summary]} (parse-opts args cli-options)]
    (cond
      (:help options) ; help => exit OK with usage summary
      {:exit-message (usage summary) :ok? true}
      errors ; errors => exit with description of errors
      {:exit-message (error-msg errors)}
      ;; custom validation on arguments
      (and (= 1 (count arguments))
           (#{"add" "list" "edit" "delete"} (first arguments)))
      {:action (first arguments) :options options}
      :else ; failed custom validation => exit with usage summary
      {:exit-message (usage summary)})))

(defn exit [status msg]
  (println msg)
  (System/exit status))
