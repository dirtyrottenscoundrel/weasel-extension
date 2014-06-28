(defproject weasel-extension "NOT_RELEASED"
  :description "weasel ClojureScript bREPL client packaged as a Chrome DevTools extension"
  :url "https://github.com/dirtyrottenscoundrel/weasel-extension"
  :license {:name "Unlicense"
            :url "http://unlicense.org/UNLICENSE"
            :distribution :repo}

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2202"]
                 [weasel "0.2.1-jtg-SNAPSHOT"]
                 [prismatic/dommy "0.1.2"]]

  :source-paths ["src"]
  :profiles {:dev {:dependencies [[com.cemerick/piggieback "0.1.3"]]
                   :plugins [[lein-cljsbuild "1.0.3"]]
                   :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
                   :cljsbuild {:builds [{:id "weasel-extension"
                                         :source-paths ["src"]
                                         :compiler {:output-to "extension/js/compiled/weasel.js"
                                                    :output-dir "extension/js/compiled/out"
                                                    :optimizations :whitespace
                                                    :source-map "extension/js/compiled/weasel.js.map"}}]}}})
