# Maths

Ce projet a pour but initial de pouvoir stocker dans des matrices d'autres objets que des nombres réels (classe Double).
Or, pour pouvoir tout de même définir le produit et la somme matricielle, il faut définir des structures algébriques qui régiront les interactions entre objets.

On s'intéresse dans ce projet tout particulièrement à deux objets : 
- les matrices
- les graphes

## Le package "algebraicObjects"

ici sont stockés tous les objets algébriques que l'on souhaitera "habiller" par la suite.
Par "habiller", on entend y ajouter des méthodes pour les manipuler.

## Le package "algebraicStructure"

Ici sont ajoutées les différentes structures algébriques qui nous serviront à travailler sur les objets dans le
package présenté juste au dessus.

On pourra observer dans ce package la présence d'interface représentant la structure algébrique de corps, ou encore celles représentant une k-algèbre, un k-espace vectoriel ou un espace euclidien.

Ces interfaces sont des "vues" des objets algébriques. En effet, implémenter l'interface des espaces vectoriels
sur l'objet "Matrix" revient à travailler sur les matrices en les observant tel des vecteurs.
Changer de structure algébrique revient donc à changer de point de vue.

## La classe MatrixDouble

Cette classe sert à utiliser les matrices contenant tout simplement des nombres réels. Elle sert en particulier à manipuler les matrices sans se soucier du point de vue dans lequel on se place. Autrement dit, elle sert à manipuler les matrices sans passer par les structures algébriques.
Cela ne rend pas caduc ces dernières pour autant, elles seront utiles pour les cas où l'on veut se placer dans un point de vue particulier.


## Le package graphTheory

Ce package regroupe tous les objets servant à représenter les graphes. 

### La class Graph

Cette classe justifie la création des structures algébriques car elle possède deux classes variables:

- v : représente le type d'info stockés dans les sommets
- k : représente le type des valeurs sur les arcs valués, il est aussi au passage le type stocké dans les matrices d'adjacence. 

Le graphe devra donc être muni d'une K-algèbre pour être en capacité de manipuler les matrices sur le type k.



# Le mot de la fin

Ce projet n'a finalement pas pour réel but d'optimiser l'écriture de code ou l'implémentation de nouvelles structures. Il a seulement pour but d'harmoniser du mieux possible les relations entre objets et structures tout en essayant de rester le plus général possible.


