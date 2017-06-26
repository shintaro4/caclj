(defproject caclj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha17"]
                 [org.clojure/tools.cli "0.3.5"]]

  ;; Leiningen test hooks clash with test.check 0.9.0
  ;; https://github.com/technomancy/leiningen/issues/2173
  :monkeypatch-clojure-test false

  :profiles {:dev {:dependencies [[org.clojure/test.check "0.9.0"]]}}
  :main caclj.core)
