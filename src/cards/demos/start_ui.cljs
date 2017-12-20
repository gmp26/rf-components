(ns demos.start-ui
  (:require [devcards.core :refer [start-devcard-ui!]]
            [demos.reagent]
            [demos.db :refer [default-db]]
            ))

(def default-db
  {:name "re-frame"
   :inputs
         {:age   nil
          :min   25
          :max   85
          :step1 1
          :step2 5}})

(defn ^:export main []
  (re-frame/dispatch-sync [::demos.events/initialize-db])
  (start-devcard-ui!))
