(ns chatbot.core

  (:require
            [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.pprint :as p]
            [identify.simple :as ntw]
            [cortex.util :as util])
  (:import [java.io File]))



(use 'clojure.java.browse)

(def parks-info
          {"bertramka" "Bertramka info"
           "frantiska zahrada" "Frantiska zharada info"
           "obora hvezda" "Obora Hvezda info"
           "kampa" "Kampa info"
           "kinskeho sady" "Kinskeho sady info"
           "klamovka" "Klamovka info"
           "ladronka" "Ladronka info"
           "letenske sady" "Letenske sady info"
           "petrin" "Petrin info"
           "riegrovy sayd" "Riegrovy sady info"
           "stromovka" "Stromovka info"
           "vojanovy sady" "Vojanovy sady info"
           "vysehrad" "Vysehrad info"
            })







(defn identify_image []
  (loop [state :hello]
      (let [input (read-line)]
          (when-not (= ":done" input)
           (cond
             (= state :hello) (do
                                (println (str "Hello! Do you want me helping you recognize flowers? Type 'yes' or 'no'"))
                                (recur :flower-interested?))
             (= state :flower-interested?)
             (cond (= input "yes") (do
                                     (println (str "Great! What was the color? Type 'red' or 'yellow'"))
                                     (recur :flower-yes))
                   (= input "red") (println "I'm just a park bot. Do you need information about parks?")
                    :else (do (println "Please answer ............")
                            (recur state)))
             (= state :flower-yes)
             (cond (= input "yes") (do
                                     (println (str "Was top of the flower round? Type 'yes' or 'no'"))
                                     (recur :flower-round))
                   (= input "no") (do
                                    (println (str "It's hard to determine. Would you like to upload a picture? Type 'yes' or 'no'"))
                                    (recur :park-bike-yes))
                    :else (do (println "Please answer .........")
                            (recur state)))
             (= state :flower-round)
             (cond (= input "yes") (do
                                     (println (str "I can guess it might be flower A or flower B"))
                                     (recur :toimplement))
                   (= input "no") (do (println "Please upload a picture or the path below")
					                            (recur :toimplement))
                   :else (do (println "Please answer ...........")
                           (recur state)))
             :else
             (do (println "Unknown state" state)
               (recur :hello)))))))


(defn read-input []
  (loop [state :park-interested?]
      (let [input (read-line)]
          (when-not (= ":done" input)
           (cond
;             (= state :hello) (do
;                                (println (str "Nice to meet you " input "! Are you interested in a park? Type 'yes' or 'no'"))
;                                (recur :park-interested?))
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

                   (= input "no") (do (println "Pity they had Svickova on the menu today. Would you like to skate instead? Type 'yes' or 'no'")
					                            (recur :park-skate-yes))
                   :else (do (println "Please answer betramka or no")
                           (recur state)))
             (= state :park-bike-yes)
             (cond (= input "yes") (do
                                     (println (str "Would you like to have food? Type 'yes' or 'no'"))
                                     (recur :park-food-yes))
                   (= input "no") (do
                                    (println (str "Are you looking for a WC? Type 'yes' or 'no'"))
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
             (cond (= input "yes") (do (println (str "I suggest Ladronka park. Can I open the link to the map? Type 'ladronka' or 'no'"))
                                       (recur :park-map-yes))
                   (= input "no") (do (println (str "Not a problem, it also nice to walk")))
             :else (do (println "Please answer 'ladronka' or no")
					(recur state)))
             (= state :park-wc-yes)
             (cond (= input "yes") (do (println (str "I suggest Riegrovy Sady. Can I open the link to the map? Type 'ladronka' or 'no'"))
                                       (recur :park-map-yes))
                   (= input "no") (do
                                     (println (str "Not a problem, it also nice to walk")))
             :else (do (println "Please answer 'ladronka' or no")
					(recur state)))
             :else
             (do (println "Unknown state" state)
               (recur :hello)))))))

(defn park-info-bot []
                (loop [state :start]
                  (newline)
                   (println "What park do you need info on? (Type name or \"list\" for list of parks with available information)")
                     (let [input (str/lower-case (read-line))]
                       (cond
                         (contains? parks-info input)
                         (do
                           (println (get parks-info input))
                           (println "Would you like info on another park or help with choosing a park?(Type info or help)")
                           (let [input2 (str/lower-case (read-line))]
                             (cond
                               (= input2 "help")(do
                                 (read-input))
                               (= input2 "info")(do
                                  (recur state)))))
                         (= input "list")
                           (do
                             (println (str/upper-case (keys parks-info)))
                             (recur state))))))

(defn identify []
  (println "What do you want identified? You can just drop the image here and I will have a look :)")
  (def image (read-line))
  (def image-path (str/trim (str/replace image "'" "")))
  (print "Great it seems like you saw a:")
  (ntw/guess_image image-path)
  )



(defn start-bot []
                 "A starting function"
                 (newline)
                 (println "Hello, I am your Prague Park Chatbot!")
                 ;(Thread/sleep 1000)
                 (newline)
                 (println "I can help you choose a park to visit or give you information regarding a park.")
                 ;(Thread/sleep 1000)
                 (println "I will also be able to help identify things, but I am still learning to do this")
                 ;(Thread/sleep 1000)
                 (loop [state :start]
                   (newline)
                   (println "Would you like help or you need information? I can also help you with identifying a picture.")
                   (let [input (read-line)]
                     (cond
                       (= input "help")
                         (do
                           (read-input)
                           (recur state))
                        (= input "information")
                          (do
                           (park-info-bot)
                           (recur state))
                        (= input "identify")
                          (do
                            (identify_image)
                            (recur state))

                        :else (do
                          (println "Please reply with \"help\" or \"information\" or \"identify\"")
                          (recur state))))))

(defn start-bot1 []
                 "A starting function"
                 (println "Hello, I am your Prague Park Chatbot!")
                 (println "I can help you choose a park to visit or give you information regarding a park.")
                 (println "Would you like help or you need information?")
                 (loop [state :start]
                   (let [input (read-line)]
                     (cond
                       (= input "help")
                         (do
                           (read-input))
                        (= input "information")
                          (do
                           (park-info-bot))
                        :else (do
                          (println "Please reply with \"help\" or \"information\"")
                          (recur state))))))



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
  (start-bot)
  (read-input)
  )
