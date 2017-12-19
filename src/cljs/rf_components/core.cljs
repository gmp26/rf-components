(ns rf-components.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [rf-components.events :as events]
            [rf-components.views :as views]
            [rf-components.config :as config]))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (if-let [node (.getElementById js/document "app")]
    (reagent/render [views/main-panel] node)))

(defn ^:export init []
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
