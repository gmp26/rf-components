(ns demos.db
  (:require [reagent.core :refer [ratom]]))

(def default-db
  {:name "test-fixture"
   :inputs
         {:age     nil
          :options {:min   25
                    :max   85
                    :precision 1
                    :interval 200}}})