(defproject advent-of-code "0.1.0-SNAPSHOT"
  :description "My attempt at Advent of Code 2021 in Clojure"
  :url "https://git.sr.ht/~happy/AdventOfCode2020_clojure"
  :dependencies [[org.clojure/clojure "1.10.3"] 
                 [criterium "0.4.6"]
                 [org.clojure/data.priority-map "1.1.0"]] 
  :main advent-of-code.core
  :repl-options {:init-ns advent-of-code.core}
  :jvm-opts ["-Djdk.attach.allowAttachSelf"])
