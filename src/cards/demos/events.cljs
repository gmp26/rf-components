(ns demos.events
  (:require [re-frame.core :refer [reg-event-db reg-event-fx]]
            [demos.db :refer [default-db]]))

(reg-event-db
  ::initialize-db
  (fn [_ _]
    default-db))

(reg-event-db
  ::update-input
  (fn [db [key val]]
    (assoc-in db [:inputs key] val)))
