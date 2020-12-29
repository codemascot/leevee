(ns leevee.util
  ""
  (:require [clj-time.format :as f]
            [clj-time.core :as t]))

(defn ^String ascii-art
  "Returns ASCII art written LEEJOURE"
  []
  "
██╗     ███████╗███████╗██╗   ██╗███████╗███████╗
██║     ██╔════╝██╔════╝██║   ██║██╔════╝██╔════╝
██║     █████╗  █████╗  ██║   ██║█████╗  █████╗
██║     ██╔══╝  ██╔══╝  ╚██╗ ██╔╝██╔══╝  ██╔══╝
███████╗███████╗███████╗ ╚████╔╝ ███████╗███████╗
╚══════╝╚══════╝╚══════╝  ╚═══╝  ╚══════╝╚══════╝")

(defn ^String trim-text
  "Only takes 80 characters."
  [^Integer n, ^String s]
  (if (< (count s) n) s (subs s 0 (dec n))))

(defn ^String str-to-date
  "Usage: (str-to-date \"2020-12-27\" \"yyyy-M-dd\")"
  [^String s, ^String p]
  (let [formatter (java.text.SimpleDateFormat. p)]
    (.format formatter (.parse formatter s))))
