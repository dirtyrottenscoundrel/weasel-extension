(ns weaselExtension.core
  (:require [weasel.repl :as repl]
            [dommy.utils :as utils]
            [dommy.core :as dommy])
  (:use-macros [dommy.macros :only [sel sel1]]))

(declare activateForm deactivateForm)

(defn updateLog [latest]
  (let [log (sel1 "textarea")
        msg (dommy/text log)]
    (dommy/set-text! log (str msg latest "\r\n\r\n"))))

(defn tryConnect [_]
  (let [fields (sel :input)
        keys (map #(keyword (dommy/attr % :id)) fields)
        vals (map dommy/value fields)
        opts (zipmap keys vals)
        ws-addr (str "ws://" (:hostname opts) ":" (:port opts))]

    (repl/connect ws-addr
                  :on-open #(do
                              (updateLog (str ">> Websocket connection opened to " ws-addr))
                              (deactivateForm))
                  :on-close #(do
                               (updateLog ">> Websocket connection closed")
                               (activateForm))
                  :on-error #(updateLog  ">> Websocket connection error")
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
