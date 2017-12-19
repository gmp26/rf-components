(ns rf-components.subs
  (:require [re-frame.core :as re-frame :refer [reg-sub]]))

(reg-sub ::name
 (fn [db]
   (:name db)))
