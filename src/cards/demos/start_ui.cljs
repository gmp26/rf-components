(ns demos.start-ui
  (:require [devcards.core :refer [start-devcard-ui!]]
            [demos.reagent]
            [demos.db :refer [default-db]]
            [re-frame.core :as re-frame]
            ))


(defn ^:export main []
  (re-frame/dispatch-sync [:demos.events/initialize-db])
  (start-devcard-ui!))
