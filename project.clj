(defproject hello-reframe "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.9.946"]
                 [reagent "0.7.0"]
                 [re-frame "0.10.2"]
                 [devcards "0.2.4" :exclusions [cljsjs/react]]
                 [figwheel-sidecar "0.5.14"]
                 [re-frisk "0.5.3"]
                 ]

  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-less "1.7.5"]
            [lein-ancient "0.6.15"]]

  :min-lein-version "2.5.3"

  :source-paths ["src/clj" "src/cljc" "src/cljs" "script"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"
                                    "test/js"]

  :figwheel {:css-dirs ["resources/public/css"]}

  :doo {:build "test"
        :alias {:default [:phantom]}}

  :less {:source-paths ["less"]
         :target-path  "resources/public/css"}

  :aliases {"dev"   ["do" "clean"
                     ["pdo" ["figwheel" "dev"]
                      ["less" "auto"]]]
            "build" ["do" "clean"
                     ["cljsbuild" "once" "min"]
                     ["less" "once"]]
            "test"  ["doo" "phantom" "test" "once"]
            ;["cljsbuild" "test"]
            }

  :profiles
  {:dev
   {:dependencies [[binaryage/devtools "0.9.8"]
                   [re-frisk "0.5.3"]]

    :plugins      [;[lein-figwheel "0.5.14"]
                   [lein-doo "0.1.8"]
                   [lein-pdo "0.1.1"]]}}

  :cljsbuild
  {:builds
   [
    {:id           "devcards"
     :source-paths ["src/cljs" "cards"]
     :figwheel     {:devcards true}
     :compiler     {:main                 hello-reframe.devcards-test
                    :optimizations        :none
                    :asset-path           "devcards/tests/out"
                    :output-dir           "resources/public/devcards/tests/out"
                    :output-to            "resources/public/devcards/tests/all-tests.js"
                    :source-map-timestamp true
                    :preloads             [devtools.prel
                                           re-frisk.preload]}}

    {:id           "dev"
     :source-paths ["src/cljs"]
     :figwheel     {:on-jsload "hello-reframe.core/mount-root"}
     :compiler     {:main                 hello-reframe.core
                    :output-to            "resources/public/js/compiled/app.js"
                    :output-dir           "resources/public/js/compiled/out"
                    :asset-path           "js/compiled/out"
                    :source-map-timestamp true
                    :preloads             [devtools.preload
                                           re-frisk.preload]
                    :external-config      {:devtools/config {:features-to-install :all}}
                    }}


    {:id           "min"
     :source-paths ["src/cljs"]
     :compiler     {:main            hello-reframe.core
                    :output-to       "resources/public/js/compiled/app.js"
                    :optimizations   :advanced
                    :closure-defines {goog.DEBUG false}
                    :pretty-print    false}}

    {:id           "test"
     :source-paths ["src/cljs" "test/cljs"]
     :compiler     {:main          hello-reframe.runner
                    :output-to     "resources/public/js/compiled/test.js"
                    :output-dir    "resources/public/js/compiled/test/out"
                    :optimizations :none}}
    ]}

  )
