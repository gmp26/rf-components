(ns views.buttons
  (:require [reagent.core :as reagent]
            [reframe.core :refer [subscribe dispatch]]))

(defn inc-dec
  [{:keys [label increment]}]
  [:a.btn.btn-default {:style         {:border-top-right-radius    left-r
                                       :border-bottom-right-radius left-r
                                       :border-top-left-radius     right-r
                                       :border-bottom-left-radius  right-r
                                       :width                      "36px"}
                       :aria-hidden   "true"
                       :tab-index     -1
                       :on-mouse-down #(do (reset! (::timer state) (start-timer %))
                                           ;(handle-inc @cursor onChange min max increment)
                                           )
                       :on-mouse-up   #(do (js/clearInterval @(::timer state))
                                           (reset! (::timer state) nil))
                       :on-mouse-out  #(do (js/clearInterval @(::timer state))
                                           (reset! (::timer state) nil))
                       :on-click      #(handle-inc @cursor onChange min max increment)}
   label])

#_(rum/defcs inc-dec-button < rum/static (rum/local nil ::timer)
             [state
              {:keys [cursor increment onChange min max precision unit]
               :as   props}]
             (let [[left-r right-r] (if (pos? increment) [10 0] [0 10])
                   start-timer (fn [e] (js/setInterval #(handle-inc @cursor onChange min max increment) 200))
                   ]
               [:a.btn.btn-default {:style         {:border-top-right-radius    left-r
                                                    :border-bottom-right-radius left-r
                                                    :border-top-left-radius     right-r
                                                    :border-bottom-left-radius  right-r
                                                    :width                      "36px"}
                                    :aria-hidden   "true"
                                    :tab-index     -1
                                    :on-mouse-down #(do (reset! (::timer state) (start-timer %))
                                                        ;(handle-inc @cursor onChange min max increment)
                                                        )
                                    :on-mouse-up   #(do (js/clearInterval @(::timer state))
                                                        (reset! (::timer state) nil))
                                    :on-mouse-out  #(do (js/clearInterval @(::timer state))
                                                        (reset! (::timer state) nil))
                                    :on-click      #(handle-inc @cursor onChange min max increment)}
                (if (pos? increment) "+" "–")]))