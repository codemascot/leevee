(ns leevee.core
  ""
  (:require [leevee.cli :as cli]
            [leevee.db :as db]
            [leevee.util :as util])
  (:gen-class))

(def tasks (atom {:date ""
                  :tasks {}}))

(defn tasks-input
  ""
  [^Integer number, ^Integer chars, ^String date]
  (swap! tasks assoc :date date)
  (dotimes [i number]
    (println "Task No." (inc i))
    (do (print (str "> "))
        (flush)
        (swap! tasks assoc-in [:tasks (inc i)] (util/trim-text chars (read-line))))))

(defn add-tasks
  ""
  [options]
  (let [number 3
        chars 80
        today (.format (java.text.SimpleDateFormat. "yyyy-M-dd") (new java.util.Date))
        date (if (nil? (:day options)) today (:day options))]
    (if (db/exists? "task_dates" "task_date" date)
      (cli/exit 1 "Tasks already exist for this date!")
      (tasks-input number chars date)))
  (db/insert-tasks @tasks)
  ;; TODO: Need to make this msg conditional on insert-tasks
  (cli/exit 0 "\nThe tasks added successfully."))

(defn list-tasks
  ""
  [options]
  (let [today (.format (java.text.SimpleDateFormat. "yyyy-M-dd") (new java.util.Date))
        date (if (nil? (:day options)) today (:day options))
        result (db/retrive-tasks date)]
    (println "\nTask list for" date ":\n")
    (doseq [i (into [] (db/retrive-tasks date))] (println (str "[" (:o i) "] " "[" (:s i) "]") (:t i) (str "(ID:" (:i i) ")") ))))

(defn edit-tasks
  ""
  [options]
  (let [today (.format (java.text.SimpleDateFormat. "yyyy-M-dd") (new java.util.Date))
        date (if (nil? (:day options)) today (:day options))
        task (db/update-task options)]
    (if (= task false)
      (cli/exit 1 "\nSome error happened, task not updated!")
      (cli/exit 0 "\nThe task updated successfully."))))

(defn delete-tasks
  ""
  [options]
  (println "delete " options)
  )

(defn -main [& args]
  (let [{:keys [action options exit-message ok?]} (cli/validate-args args)]
    (println (util/ascii-art))
    (if exit-message
      (cli/exit (if ok? 0 1) exit-message)
      (case action
        "add"  (add-tasks options)
        "list" (list-tasks options)
        "edit" (edit-tasks options)
        "delete" (delete-tasks options)))))
