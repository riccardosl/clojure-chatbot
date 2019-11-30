(ns chatbot.core
  (:require [clojure.string :as str]))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(use 'opennlp.nlp)
(use 'opennlp.treebank)

(def get-sentences (make-sentence-detector "models/en-sent.bin"))
(def tokenize (make-tokenizer "models/en-token.bin"))
(def detokenize (make-detokenizer "models/english-detokenizer.xml"))
(def pos-tag (make-pos-tagger "models/en-pos-maxent.bin"))
(def name-find (make-name-finder "models/namefind/en-ner-person.bin"))
(def chunker (make-treebank-chunker "models/en-chunker.bin"))


(def test-phrase
  [["hello" "hello how can I help?"]
   ["bye" "see you soon"]
   ["my name is Peter" "nice to meet you Peter"]])

(defn response-test [input]
  (or (second (first (filter (fn [[in out]]
                                (= in input))
                             test-phrase)))
      "please say it again"))

[(response-test "hello")
 (response-test "my name is Peter")
 (response-test "i am learning")]




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

(response-test2 "hello")


(pprint (get-sentences "First sentence. Second sentence? Here is another one. And so on and so forth - you get the idea..."))
