(ns fruits.simple
 (:require
  [clojure.java.io :as io]
  [cortex.util :as util]
  [cortex.nn.execute :as execute]
  [mikera.image.core :as i]
  [mikera.image.filters :as filters]
  [think.image.patch :as patch])
  (:import [java.io File]))

(defn image-file->observation
  "Create an observation from input file."
  [image-path]
{:labels ["test"]
 :data
  (patch/image->patch
    (-> (i/load-image image-path) ((filters/grayscale)) (i/resize 50 50) )
    ; can do without datatype
    :datatype :float
    :colorspace :gray)})

; I want to avoid this, should come from the network
(def categories
  (zipmap (range) (map #(.getName %) (filter #(.isDirectory %) (.listFiles (io/file "fruits/training"))))))

; (defn index->class-name[n]
;   (nth categories n))

(defn guess-with-mappings [nippy image-path mapping-file]
  (let[obs (image-file->observation image-path)  mappings (read-string (slurp mapping-file))]
  (-> (execute/run nippy [obs])
   first
   :labels
   util/max-index
   mappings)))
(def nippy (util/read-nippy-file "trained-network.nippy"))
(defn guess
  ([nippy image-path] (guess nippy image-path categories))
  ([nippy image-path mappings]
  (let[obs (image-file->observation image-path) ]
  (-> (execute/run nippy [obs])
   first
   :labels
   util/max-index
   mappings))))

(defn guess-debug [nippy image-path]
  (let[obs (image-file->observation image-path) ]
  (-> (execute/run nippy [obs])
   first)))

(defn list-images-in[folder]
  (into [] (filter #(let[fname (clojure.string/lower-case %) ]
    (or (clojure.string/includes? fname ".jpg") (clojure.string/includes? fname ".png") )  )
    (map #(.getPath %) (into [] (.listFiles (io/file "samples")))))))

(defn guesses [nippy image-paths]
  (let[obs (map #(image-file->observation %) image-paths) ]
  (map #(categories (util/max-index (:labels %))) (execute/run nippy (into-array obs)))))

(defn -main[& args]
  (if (empty? args)
    (println "Usage: lein run -m fruits.simple <nippy-file> <path-to-image(s)> <mapping-file>")
    (let[ nippy (util/read-nippy-file (first args))
          input (io/as-file (second args))
          mapping (last args)
          ]
      (clojure.pprint/pprint
        (if (.isDirectory input)
         (let[imgs (list-images-in input)] (zipmap imgs (guesses nippy imgs)))
         [input (guess-with-mappings nippy input mapping)])))))

(comment

  (require '[cortex.util :as util])


  (require '[fruits.simple :as simple])
  ; test one
  (simple/guess nippy "samples/apple-1.jpg")
  ; test all from folder
  (simple/guesses nippy (simple/list-images-in "samples"))
  ; compare inline
  (clojure.pprint/pprint (zipmap (simple/list-images-in "samples") (simple/guesses nippy (simple/list-images-in "samples"))))

)
