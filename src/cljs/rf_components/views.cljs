(ns rf-components.views
  (:require [re-frame.core :as re-frame]
            [rf-components.subs :as subs]
            ))

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div "Hello from " @name]))
