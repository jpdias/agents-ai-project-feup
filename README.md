# ROADSIDE #

T**R**ust In Information Sources on a C**O**mpetitive and **D**i**s**tr**i**bute**d** **E**nvironment



### What is this repository for? ###

* Agentes de Inteligência Artificial Distribuida
* Faculdade de Engenharia da Universidade do Porto


### How do I get set up? ###

#### Configure Intellij IDEA ####

- Add JADE.jar to the project

File → Project Structure (CTRL + SHIFT + ALT + S) → Module → Dependencies → Add... → Project Library → Attach Jar (Jade.jar)

- Configure Run

Main class: *jade.Boot*

Program args: *-gui  jade.Boot <agent nickname>:<package name>:<agent class name>*
