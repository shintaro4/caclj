(ns caclj.core
  (:require [clojure.string :as string]
            [clojure.tools.cli :refer [parse-opts]]
            [caclj.cellular-automata :as ca])
  (:gen-class))


(def cli-options
  [["-r" "--rule RULE" "Rule number"
    :default 30
    :parse-fn #(Integer/parseInt %)
    :validate [#(<= 0 % 255) "Must be a number between 0 and 255"]]
   ["-t" "--times TIMES" "The number ob the generations"
    :default 16
    :parse-fn #(Integer/parseInt %)
    :validate [pos? "Must be a positive number"]]
   ["-h" "--help"]])


(defn usage [options-summary]
  (string/join
   \newline
   ["An elementary cellular automata."
    ""
    "Usage: lein run [options]"
    ""
    "Options:"
    options-summary]))


(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (string/join \newline errors)))


(defn validate-args
  "Validate command line arguments. "
  [args]
  (let [{:keys [options arguments errors summary]} (parse-opts args cli-options)]
    (cond
      (:help options) ; help => exit OK with usage summary
      {:exit-message (usage summary) :ok? true}
      errors ; errors => exit with description of errors
      {:exit-message (error-msg errors)}
      :else
      {:options options})))


(defn exit [status msg]
  (println msg)
  (System/exit status))


(defn -main
  [& args]
  (let [{:keys [options exit-message ok?]} (validate-args args)]
    (if exit-message
      (exit (if ok? 0 1) exit-message)
      (let [times (:times options)
            _rule-map (ca/build-rule-map (:rule options))
            _next-gen #(ca/evolve _rule-map %)]
           (->> (ca/first-generation times)
             (iterate _next-gen)
             (take times)
             (map #(ca/convert-characters ca/CELL-CHARACTER-MAP %))
             (run! println))))))
