(ns views.numeric-input
  (:require [reagent.core :as reagent]
            [reframe.core :refer [subscribe dispatch]]))

(defn numeric-input []
  [:div {:on-key-down #(dispatch :numeric-input/key-down %)}])

#_(rum/defc numeric-input < rum/static rum/reactive [{:keys [key onChange min max precision unit] :as props}]
            ;(println "NumericInput = ") js/NumericInput

            (let [val (rum/react (input-cursor key))]
              [:div {:style       {:width      "130px"
                                   :tab-index  1
                                   :selectable true}
                     :on-key-down #(let [key-code (.. % -nativeEvent -code)]
                                     (handle-inc val onChange min max
                                                 (cond
                                                   (= "ArrowRight" key-code) 5
                                                   (= "ArrowUp" key-code) 1
                                                   (= "ArrowDown" key-code) -1
                                                   (= "ArrowLeft" key-code) -5
                                                   :else 0)))}
               [:button-group.form-control
                (inc-dec-button (assoc props :increment -1 :cursor (input-cursor key)))
                [:input.numeric
                 {:key       key
                  :type      "text"
                  :value     val
                  :on-click  (partial handle-numeric-input onChange)
                  :on-change (partial handle-numeric-input onChange)
                  :style     {:width            "58px" :height "36px" :font-size "14px"
                              :border-top       "2px solid #ddd"
                              :border-left      "2px solid #ddd"
                              :background-color (if (not= val "") "#CCEEF8" "#fff")
                              :padding          "0 0 4px 0"
                              :text-align       "center"
                              :font-weight      "bold"}
                  }]
                (inc-dec-button (assoc props :increment 1 :cursor (input-cursor key)))
                ]]

              #_(when (:unit props) [:label.control-label.col-xs-3 (:unit props)])))
