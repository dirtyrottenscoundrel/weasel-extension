(ns weaselExtension.core
  (:require [weasel.repl :as repl]
            [dommy.utils :as utils]
            [dommy.core :as dommy])
  (:use-macros [dommy.macros :only [sel sel1]]))

(declare activateForm deactivateForm)


(defn tryConnect [_]
  (let [fields (sel :input)
        keys (map #(keyword (dommy/attr % :id)) fields)
        vals (map dommy/value fields)
        opts (zipmap keys vals)
        ws-addr (str "ws://" (:hostname opts) ":" (:port opts))]

    (repl/connect ws-addr
                  :on-open #(deactivateForm)
                  :on-close #(activateForm)
                  :verbose true)))

(defn activateForm []
  (doseq [tag (sel "input,button")]
    (dommy/remove-attr! tag :disabled))
  
  (dommy/listen-once! (sel1 :button) :click tryConnect))

(defn deactivateForm []
  (doseq [tag (sel "input,button")]
    (dommy/set-attr! tag :disabled)))

(if (repl/alive?)
  (deactivateForm)
  (activateForm))
