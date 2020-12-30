# Leevee 
This is a todo list app based on *Ivy Lee* method, written in *Clojure*.

## Background
Actually this is my practice DB project with *Clojure*. As of **30 Dec 2020**, I'm writing *Clojure* for 17 days and this is my second project. My first project is the *WooCommerce* REST API client in *Clojure* ([wc-api-clj](https://github.com/codemascot/wc-api-clj)). Hereby, I wanted to make my second one something DB interaction oriented. But web app seemed too boring to me as I've come from web development background. Also I didn't have much exposure to *CLI* app writing world. So, I took this chance to make a CLI app with DB interaction. Yes, I'm aware of the cost of fire up JVM whenever this app is initiating, still I've grabbed the chance to play with *Clojure* as well as *CLI*.

## Setup

1. Rename `env.example` to `.env` and put your DB credentials there.

2. Run the migration. Also for MySQL you can import `db/leevee.sql` to create the DB.

3. Run the program with `lein run`.
Example: 
```
lein run add --day "2020-12-29"
lein run edit --day "2020-12-29" --order 2 --status "DONE"
```.

## License

Copyright © 2020 [CodeMascot](https://www.codemascot.com/) AKA [Khan Mohammad R.](https://www.codemascot.com/)

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.

