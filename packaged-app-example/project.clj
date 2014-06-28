(defproject weasel-example "NOT_RELEASED"
  :description "example packaged app project for testing the DevTools extension"
  :url "https://github.com/dirtyrottenscoundrel/weasel-extension"
  :license {:name "Unlicense"
            :url "http://unlicense.org/UNLICENSE"
            :distribution :repo}

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2202"]]

  :source-paths ["src"]
  :profiles {:dev {:dependencies [[weasel "0.2.1-jtg-SNAPSHOT"]
                                  [com.cemerick/piggieback "0.1.3"]]
                   :plugins [[lein-cljsbuild "1.0.3"]]
                   :cljsbuild {:builds [{:id "weasel-example"
                                         :source-paths ["src"]
                                         :compiler {:output-to "app/js/compiled/weasel_example.js"
                                                    :output-dir "app/js/compiled/out"
                                                    :optimizations :whitespace
                                                    :source-map "app/js/compiled/weasel_example.js.map"}}]}}})
