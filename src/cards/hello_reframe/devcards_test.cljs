(ns rf-components.devcards-test
  (:require
    [reagent.core :as r]
    [cljs.test :refer [testing is]]
    [rf-components.core :refer [init]]
    [rf-components.views :refer [main-panel]]
    [rg.numeric-input :refer [numeric-input]]
    )
  (:require-macros
    [devcards.core :as dc :refer [defcard
                                  defcard-rg
                                  deftest
                                  ]]))

(init)

(defonce app-state (r/atom {:count 0}))

(defn on-click [ratom]
  (swap! ratom update-in [:count] inc))

(defn counter [ratom]
  (let [count (:count @ratom)]
    [:div
     [:p "Current count: " count]
     [:button
      {:on-click #(on-click ratom)}
      "Increment"]]))

(defcard-rg counter
            [counter app-state]
            app-state
            {:inspect-data true
             :history      true})

(deftest fake-test
         (testing "fake description"
                  (is (= 1 2))
                  (is (= 1 1))))

(defcard-rg numeric-input
            [numeric-input])

(defcard reagent-no-help
  (r/as-element [:h1 "Reagent example"]))

(defcard main-panel-no-help
  (r/as-element [main-panel]))

(defcard-rg main-panel-rg
            [main-panel])

(defcard main-panel-reagent
  (dc/reagent [:p "Using dc/reagent"]))

