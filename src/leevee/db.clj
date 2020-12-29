(ns leevee.db
  ""
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.edn :as edn]))

(def db (-> (slurp ".env") edn/read-string :env :db))

(defn exists?
  ""
  [^String table, ^String column, ^String data]
  (let [query (str "SELECT id FROM " ^String table " WHERE " ^String column " LIKE '" ^String data "'")]
    (if (nil? (-> (jdbc/query db [query]) first :id))
      false
      true)))

(defn insert-tasks
  ""
  [{:keys [date tasks]}]
  (let [task-date-id (jdbc/insert! db :task_dates {:task_date date})
        tasks-ids (jdbc/insert-multi! db :tasks
                                      (into [] (map (fn [v] (into {} [[:task (val v)] [:status "TODO"]] ) ) tasks)))
        task-date-pivot (into [] (map-indexed
                                  (fn [k v] (into {} {:task (:generated_key v)
                                                     :task_date (-> task-date-id first :generated_key)
                                                     :task_order (inc k)}))
                                  tasks-ids))]
    (jdbc/insert-multi! db :task_date_pivot task-date-pivot)))

(defn retrive-tasks
  "Usage: (list-tasks \"2020-12-28\")
  In return value the keyword i is id, t is task, s is status and o is task_order"
  [date]
  (jdbc/query db ["SELECT tasks.id AS i, tasks.task AS t, tasks.status AS s, task_date_pivot.task_order AS o
  FROM tasks
         LEFT JOIN task_date_pivot on tasks.id = task_date_pivot.task
         LEFT JOIN task_dates on task_dates.id = task_date_pivot.task_date
 WHERE task_dates.task_date = ?
 ORDER BY task_date_pivot.task_order" date]))
