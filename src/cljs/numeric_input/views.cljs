(ns numeric-input.views
  (:require [reagent.core :as reagent]
            [re-frame.core :refer [subscribe dispatch]]
            [clojure.string :as str]))


(defn error? [val] (or (nil? val) (= "" val) (js/isNaN val)))


(defn handle-numeric-input [key e]
  (let [val (js/parseInt (.. e -target -value))
        val (if (error? val) "" val)]
    (dispatch :update-input key (str val))))


(defn handle-inc [key val min max step e]
  (when (not= 0 step)

    (.stopPropagation (.. e -nativeEvent))
    (.preventDefault (.. e -nativeEvent))
    (let [val-1 (if (= val "")
                  (if (pos? step)
                    (dec min)
                    (if (neg? step) (inc max) ""))
                  (js/parseInt val))
          val-2 (if (error? val-1) "" (+ step val-1))
          val-3 (if (< val-2 min) min (if (> val-2 max) max val-2))]
      (dispatch :update-input key (str val-3))
      )))

(defn empty-to-nil [val] (if (= val "") nil val))
(defn parse-or-min [min val] ((fnil js/parseInt (str min)) val))
(defn error-or-step [step val] (if (error? val) "" (+ step val)))
(defn clamp [min max val] (if (< val min) min (if (> val max) max val)))

(defn chain [min max step val]
  (->> val
       (empty-to-nil)
       ((partial parse-or-min min))
       ((partial error-or-step step))
       ;((partial clamp min max))
       ))

(defn handle-inc* [key val min max step e]
  (when (not= 0 step)

    (.stopPropagation (.. e -nativeEvent))
    (.preventDefault (.. e -nativeEvent))
    (->> val
         (#((fnil js/parseInt (str min)) %))
         )
    (let [val-1 (if (= val "")
                  (if (pos? step)
                    (dec min)
                    (if (neg? step) (inc max) ""))
                  (js/parseInt val))
          val-2 (if (error? val-1) "" (+ step val-1))
          val-3 (if (< val-2 min) min (if (> val-2 max) max val-2))]
      (dispatch :update-input key (str val-3))
      )))


(defn stop-timer!
  [timer]
  (when @timer
    (js/clearInterval @timer)
    (reset! timer nil)))


(defn inc-dec-button
  [{:keys [key val increment min max interval precision unit]
    :or   {increment 1 min 0 max 10 interval 200 precision 1 unit nil}}]

  (let [timer (atom nil)
        [left-r right-r] (if (pos? increment) [10 0] [0 10])
        start-timer (fn [e] (js/setInterval #(handle-inc key val min max increment e) interval))
        ]
    [:a.btn.btn-default {:style         {:border-top-right-radius    left-r
                                         :border-bottom-right-radius left-r
                                         :border-top-left-radius     right-r
                                         :border-bottom-left-radius  right-r
                                         :width                      "36px"}
                         :aria-hidden   "true"
                         :tab-index     -1
                         :on-mouse-down #(do (reset! timer (start-timer %)))
                         :on-mouse-up   stop-timer!
                         :on-mouse-out  stop-timer!
                         :on-click      #(handle-inc key val min max increment %)}
     (if (pos? increment) "+" "–")]))

(defn numeric-input [{:keys [key min max precision unit] :as props}]
  ;(println "NumericInput = ") js/NumericInput

  (let [val (subscribe key) #_(rum/react (input-cursor key))]
    [:div {:style       {:width      "130px"
                         :tab-index  1
                         :selectable true}
           :on-key-down #(let [key-code (.. % -nativeEvent -code)]
                           (handle-inc val min max
                                       (cond
                                         (= "ArrowRight" key-code) 5
                                         (= "ArrowUp" key-code) 1
                                         (= "ArrowDown" key-code) -1
                                         (= "ArrowLeft" key-code) -5
                                         :else 0) %))}
     [:button-group.form-control
      [inc-dec-button (assoc props :increment -1 :key key :val val)]
      [:input.numeric
       {:type      "text"
        :value     val
        :on-click  (partial handle-numeric-input key val)
        :on-change (partial handle-numeric-input key val)
        :style     {:width            "58px" :height "36px" :font-size "14px"
                    :border-top       "2px solid #ddd"
                    :border-left      "2px solid #ddd"
                    :background-color (if (not= val "") "#CCEEF8" "#fff")
                    :padding          "0 0 4px 0"
                    :text-align       "center"
                    :font-weight      "bold"}
        }]
      [inc-dec-button (assoc props :increment 1 :key key)]
      ]]

    ))


