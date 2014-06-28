(ns weaselExtension.core
  (:require [weasel.repl :as repl]
            [dommy.utils :as utils]
            [dommy.core :as dommy])
  (:use-macros [dommy.macros :only [sel sel1]]))

(declare activateForm deactivateForm)

(defn startPolling []
  (let [interval-id (atom nil)
        checkState #(when-not (repl/alive?) 
                      (js/clearInterval @interval-id)
                      (activateForm))]
   
    (reset! interval-id (js/setInterval checkState 2000))))

(defn tryConnect [_]
  (let [fields (sel :input)
        keys (map #(keyword (dommy/attr % :id)) fields)
        vals (map dommy/value fields)
        opts (zipmap keys vals)]

    (repl/connect (str "ws://" (:hostname opts) ":" (:port opts))
                  :verbose true))
 
  (deactivateForm))

(defn activateForm []
  (doseq [tag (sel "input,button")]
    (dommy/remove-attr! tag :disabled))
  
  (dommy/listen-once! (sel1 :button) :click tryConnect))

(defn deactivateForm []
  (doseq [tag (sel "input,button")]
    (dommy/set-attr! tag :disabled))
 
  (startPolling))

(if (repl/alive?)
  (deactivateForm)
  (activateForm))
