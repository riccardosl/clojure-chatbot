(ns chatbot.core
  (:require [clojure.string :as str]
            [clojure.pprint :as p]))





(defn read-input []
  (loop [state :hello]
      (let [input (read-line)]
          (when-not (= ":done" input)
           (cond
             (= state :hello) (do
                                (println (str "Nice to meet you " input "! Are you interested in a park? Type 'yes' or 'no'"))
                                (recur :park-interested?))
             (= state :park-interested?)
             (cond (= input "yes") (recur :park-interested-yes)
                   (= input "no") (println "I can't help sorry. I'm just a park bot. Bye bye")
                    :else (do (println "Please answer yes or no")
                            (recur state)))
             (= state :park-interested-yes) (println "we are stuck")

             :else
             (do (println "Unknown state" state)
               (recur :hello)))))))

(defn park-interested-yes []
  (loop [state :hello]
      (let [input (read-line)]
          (when-not (= ":done" input)
           (cond
             (= state :hello) (do
                                (println (str "Nice to meet you " input "! Are you interested in a park? Type 'yes' or 'no'"))
                                (recur :park-interested?))
             (= state :park-interested?)
             (cond (= input "yes") (recur :park-interested-yes)
                   (= input "no") (println "I can't help sorry. I'm just a park bot. Bye bye")
                    :else (do (println "Please answer yes or no")
                            (recur state)))
             :else
             (do (println "Unknown state" state)
               (recur :hello)))))))


(def test-phrase
  [["hello" "hello how can I help?"]
   ["bye" "see you soon"]
   ["my name is Peter" "nice to meet you Peter"]
   ["Yes" "Do you know a parking space?"]])

(def firststep
  [["yes" "These parks have parking sport: "]
   ["bye" "see you soon"]
   ["my name is Peter" "nice to meet you Peter"]])

(defn response-test [input]
    (or (second (first (filter (fn [[in out]]
                                  (= in input))
                               test-phrase)))
        "please say it again"))

(defn response-first [input]
    (or (second (first (filter (fn [[in out]]
                                 (= in input))
                               firststep)))
        "please say it again"))

(def test-phrase2
  [[#"I'm near (.*)" "Let me search a park near "]
   [#"hello (.*)" "nice to meet you "]
   [#"bye (.*)" "see you soon "]])

(defn match-test [in [pattern out]]
  (when-let [[_ dyn] (re-matches pattern in)]
    (str out dyn)))

(defn response-test2 [input]
  (or (some (partial match-test input)
        test-phrase2)
      "please repeat"))

;(response-test2 "bye clojure")



(defn -main
  [& args]
  (println "Hello, I'm a chatbot. What is your name?")
  (read-input)
  (park-interested)
  )
