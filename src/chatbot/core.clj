(ns chatbot.core
  (:require [clojure.string :as str]
            [clojure.pprint :as p]))

(use 'clojure.java.browse)



(defn read-input []
  (loop [state :hello]
      (let [input (read-line)]
          (when-not (= ":done" input)
           (cond
             (= state :hello) (do
                                (println (str "Nice to meet you " input "! Are you interested in a park? Type 'yes' or 'no'"))
                                (recur :park-interested?))
             (= state :park-interested?)
             (cond (= input "yes") (do
                                     (println (str "Would you a parking space for a car? Type 'yes' or 'no'"))
                                     (recur :park-interested-yes))
                   (= input "no") (println "I can't help sorry. I'm just a park bot. Bye")
                    :else (do (println "Please answer yes or no")
                            (recur state)))
             (= state :park-interested-yes)
             (cond (= input "yes") (do
                                     (println (str "Would you like to have food? Type 'yes' or 'no'"))
                                     (recur :park-food-yes))
                   (= input "no") (do
                                    (println (str "Would you like to ride a bike? Type 'yes' or 'no'"))
                                    (recur :park-bike-yes))
                    :else (do (println "Please answer yes or no")
                            (recur state)))
             (= state :park-food-yes)
             (cond (= input "yes") (do
                                     (println (str "I suggest Betramka park. Can I open the link to the map? Type 'betramka' or 'no'"))
                                     (recur :park-map-yes))
                   (= input "no") (println "Pity they had Svickova on the menu today. Would you like to skate instead? Type 'yes' or 'no'")
                   :else (do (println "Please answer betramka or no")
                           (recur state)))
             (= state :park-bike-yes)
             (cond (= input "yes") (do
                                     (println (str "Would you like to have food? Type 'yes' or 'no'"))
                                     (recur :park-food-yes))
                   (= input "no") (do
                                    (println (str "Would you like to have food? Type 'yes' or 'no'"))
                                    (recur :park-food-yes))
                   :else (do (println "Please answer yes or no")
                           (recur state)))
             (= state :park-map-yes)
             (cond (= input "betramka") (do (browse-url "https://en.mapy.cz/s/funetamute"))
                   (= input "ladronka") (do (browse-url "https://en.mapy.cz/s/pakezonala"))
                   (= input "no") (do
                                    (println (str "Not a problem, my suggestion to reach it is Plzenska street or tram stop 'Betramka'")))
                   :else (do (println "Please answer yes or no")
                                         (recur state)))
             (= state :park-skate-yes)
             (cond (= input "yes") (do (println (str "I suggest Ladronka park. Can I open the link to the map? Type 'yes' or 'no'"))
                                       (recur :park-map-yes))
                   (= input "no") (do
                                     (println (str "Not a problem, it also nice to walk")))
             :else (do (println "Please answer yes or no")(recur state)))
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
  (response-test2 "bye clojure"))
