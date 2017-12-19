(ns rf-components.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [rf-components.core-test]))

(doo-tests 'rf-components.core-test)
