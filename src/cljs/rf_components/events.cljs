(ns rf-components.events
  (:require [re-frame.core :as re-frame]
            [rf-components.db :as db]))

(re-frame/reg-event-db
  ::initialize-db
  (fn [_ _]
    db/default-db))

(re-frame/reg-event-db
  ::change-name
  (fn [db [_ new-name]]
    (assoc db :name new-name)))