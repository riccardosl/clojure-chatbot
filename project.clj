(defproject chatbot "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :repositories {"hellonico"   {:sign-releases false :url "https://repository.hellonico.info/repository/hellonico"}}
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :plugins [[nightlight/lein-nightlight "2.4.1"]
            [org.clojure/core.unify "0.5.7"]]
  :dependencies [[org.clojure/clojure "1.10.0"]
                  [org.clojure/clojure "1.9.0"]
                 [thinktopic/experiment "0.9.22"]
                 [com.github.fommil.netlib/all "1.0" :extension "pom"]
                 [com.cognitect/transit-clj "0.8.319"]
                 [org.clojure/tools.cli "0.3.5"]
                 [thinktopic/think.datatype "0.3.17"]
                 [com.taoensso/nippy "2.13.0"]
                 [org.clojure/math.combinatorics "0.1.4"]
                 ; to manipulate images
                 [thinktopic/think.image "0.4.8" ]
                 [thinktopic/think.datatype "0.3.17"]
                 [thinktopic/think.parallel "0.3.7"]
                 [thinktopic/think.resource "1.2.1"]
                 [org.clojure/math.combinatorics "0.1.4"]
                 [org.hellonico/imgscalr-lib "4.3"]
                 [org.hellonico/sizing "0.1.0"]
                 [net.mikera/imagez "0.12.0"]
                 ;;If you need cuda 8...
                 [org.bytedeco/cuda "10.2-7.6-1.5.2"]
                 ;;[org.bytedeco.javacpp-presets/cuda "10.2-1.2"]
                 ;;If you need cuda 7.5...
                 ;;[org.bytedeco.javacpp-presets/cuda "7.5-1.2"]
                 ]
  :repl-options {:init-ns chatbot.core}
  :main chatbot.core)
