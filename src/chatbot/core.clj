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
          {"bertramka" "Bertramka is a park with a garden which was often the home to W. A. Mozart, who's museum you can visit there. It is open all year long."
           "frantiska zahrada" "Frantiska zharada is a garden in the center of Prague suitable for sitting on a bench or to play with children. It is open all yer long."
           "obora hvezda" "A great place for kite flyting or nordic skiing during winter and to see the hexagram summer castle."
           "kampa" "Small park near Charle's bridge."
           "kinskeho zahrada" "A garden near Petrin with complicated water garden."
           "klamovka" "Romantic park ideal for walks or visiting the playground with children."
           "ladronka" "Grass plains with in-line skate track."
           "letenske sady" "One of the best places to sit in summer. "
           "petrin" "Beatifull in spring and autumn with the Petrin tower and observatory nearby."
           "riegrovy sayd" "100 year old park with great views of Prague"
           "stromovka" "Could be considered the Central park of Prague. Great place for picknics or sports."
           "vojanovy sady" "A garden with melancholy of the middle ages. The place to go to get away from the city rush"
           "vysehrad" "Old baroque fortress related to many old czech legends"
            })





(defn identify []
  (println "What do you want identified? You can just drop the image here and I will have a look :)")
  (def image (read-line))
  (def image-path (str/trim (str/replace image "'" "")))
  (print "Great it seems like you saw a:")
  (ntw/guess_image image-path))



(defn read-input []
 (loop [state :park-interested?]
   (let [input (read-line)]
     (if-not (= ":done" input)
       (cond

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
             (recur :hello)))
       (do
         (println "I'm sorry that you want to go... You can always come back in the future!")
         :done)))))

(defn identify_image []
  (loop [state :hello]
    (let [input (when-not (= :hello state) (read-line)) ]
      (if-not (= ":done" input)
        (cond
          (= state :hello) (do
                             (println (str "Hello! Do you want me helping you recognize flowers? Type 'yes' or 'no'"))
                             (recur :flower-interested?))
          (= state :flower-interested?)
          (cond (= input "yes") (do
                                  (println (str "Great! What is the flower predominant color? Type 'red' or 'yellow'"))
                                  (recur :flower-yes))
                (= input "no") (do
                                 (:done))
                :else (do (println "Please type one of the two options")
                          (recur state)))
          (= state :flower-yes)
          (cond (= input "yellow") (do
                                  (println (str "Was the shape of the flower round? Type 'yes' or 'no'"))
                                  (recur :flower-round))
                (= input "red") (do
                                 (println (str "ok let's continue"))
                                 (recur :flower-round))
                :else (do (println "Please type one of the two options")
                          (recur state)))
          (= state :flower-round)
          (cond (= input "yes") (do
                                  (println (str "I can guess it might be in the category of Sunflower or Daisy.. Please press enter to upload a picture so I can be precise"))
                                  (recur :upload))
                (= input "no") (do (println "I can guess it might be in the category of Roses. Please upload a picture or the path below")
                                   (recur :upload))
                :else (do (println "Please type enter once again to continue")
                          (recur state)))
          (= state :upload)
          (cond (= input "yes") (do
                                  (identify))

                (= input "no") (do (println "I'm still learning to recognize pictures. Type :done to exit")
                                   )
                :else (do (println "Please type enter once again to continue")
                          (recur state)))
          :else
          (do (println "Unknown state" state)
              (recur :hello)))
        (do
          (println "I'm sorry that you want to go... You can always come back in the future!")
          :done)))))



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
              (= input2 "help") (do
                                  (read-input))
              (= input2 "info") (do
                                  (recur state)))))
        (= input "list")
        (do
          (println (str/upper-case (keys parks-info)))
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



(defn start-bot []
  "A starting function"
  (newline)
  (println "Hello, I am your Prague Park Chatbot!")
  (Thread/sleep 1000)
  (newline)
  (println "I can help you choose a park to visit or give you information regarding a park.")
  (Thread/sleep 1000)
  (newline)
  (println "I will also be able to help identify flowers, but I am still learning to do this")
  (Thread/sleep 1000)
  (loop [state :start]
    (newline)
    (println "Please reply with \"help\" or \"information\" or \"identify\". At any time you can exit typing \":done\"")
    (let [input (read-line)]
      (if-not (= input ":done")
        (cond
          (= input "help")
          (when-not (= :done (read-input))
            (recur state))
          (= input "information")
          (when-not (= :done (park-info-bot))
            (recur state))
          (= input "identify")
          (when-not (= :done (identify_image))
            (recur state))

          :else (do
                  (println "Please reply with \"help\" or \"information\" or \"identify\". At any time you can exit typing \":done\"")
                  (recur state)))
        (do
          (println "I'm sorry that you want to go... You can always come back in the future!")
          :done)))))

(defn -main
  [& args]
  (start-bot))
