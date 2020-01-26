# Park Chatbot

This small application is developed to give a sample to "Prague municipality" regarding capabilities of a chatbot app.
The demo wanted initially to present basic function of asking the name of the user, asking different questions about the specific requirements for a public park in Prague.
The chatbot is collecting feedback from the user and offering information about a public park offering a link to the online maps or a small description to reach it.
In the second phase of the development the chatbot has been improved with image recognition functionality.

# Image recognition
Clojure can benefit from a big community of developers that add new libraries. We explored Cortex, a machine learning library that allows to train neural networks, integrate functions to resolve image recognition tasks.
To continue on the Prague parks topic, a dataset of flowers images has been trained.
In the project he chatbot has a new option called "identify" that attempt to match the flowers colors and shape. It is not easy for the bot to have always the right answer, more training is needed. Flowers images are located in the /sample folder of the project or it is possible to test an own flower image.
The best option is to upload the path to an image and attempt the recognition. 

## Installation

The chatbot is written in Clojure, a functional programming language running on Java Virtual Machine.
Depending on your operating system different steps are necessary to install the required components:


- Java Development Kit (JDK) version 11 or newer.

- [Leiningen](https://leiningen.org/)

- A Command Line Interface (CLI)


## Usage

```bash

> git clone https://github.com/riccardosl/clojure-chatbot.git  # Clone the github repository, request your Gituhub credentials.
> cd /clojure-chatbot # Browse the project folder.
> lein run # Run the chatbot and start interaction. Type :done at anytime to quit the app.

```
## Future development

If the Prague municipality will approve the project a testing phase will be planned to fix bugs.


## Authors
[Riccardo](https://github.com/riccardosl) &
[Jan](https://github.com/jandvorak-dot)
## License
[Eclipse Public License 1.0](https://www.eclipse.org/legal/epl-2.0/)
