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

(defn list-tasks
  ""
  [options]
  (let [today (.format (java.text.SimpleDateFormat. "yyyy-M-dd") (new java.util.Date))
        date (if (nil? (:day options)) today (:day options))
        result (db/retrive-tasks date)]
    (println "\nTask list for" date ":\n")
    (doseq [i (into [] (db/retrive-tasks date))] (println (str "[" (:o i) "] " "[" (:s i) "]") (:t i)))))

(defn add-tasks
  ""
  [options]
  (let [number 3
        chars 80
        today (.format (java.text.SimpleDateFormat. "yyyy-M-dd") (new java.util.Date))
        date (if (nil? (:day options)) today (:day options))]
    (if (db/exists? "task_dates" "task_date" date)
      (cli/exit 1 "Tasks already exist for this date!")
      (tasks-input number chars date))
    (if (= (count (db/insert-tasks @tasks)) number)
      (do (list-tasks options)
        (cli/exit 0 "\nTasks added successfully.\n"))
      (cli/exit 1 "\nSome error happened, tasks addition failed!"))))

(defn edit-tasks
  ""
  [options]
  (let [today (.format (java.text.SimpleDateFormat. "yyyy-M-dd") (new java.util.Date))
        date (if (nil? (:day options)) today (:day options))
        task (db/update-task options)]
    (list-tasks options)
    (if (= task false)
      (cli/exit 0 "\nSome error happened, task update failed!")
      (cli/exit 1 "\nThe task updated successfully."))))

(defn delete-tasks
  ""
  [options]
  (let [row (first (db/delete-tasks options))]
    (if (> row 0)
      (cli/exit 1 "\nAll the data deleted successfully.")
      (cli/exit 0 "\nSome error happened, data delete failed!"))))

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
