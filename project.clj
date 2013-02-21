(defproject webfui-playground "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :plugins [[lein-cljsbuild "0.3.0"]]
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [webfui "0.2.1"]]
  :cljsbuild {:builds [{:id "playground"
                        :source-paths ["src-cljs/webfui_playground/"]
                        :compiler {:output-to "resources/public/js/playground.js"
                                   :optimizations :whitespace
                                   :pretty-print true}}]})
