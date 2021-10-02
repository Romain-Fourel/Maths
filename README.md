# Maths

Ce projet a pour but initial de pouvoir stocker dans des matrices d'autres objets que des nombres réels, comme des nombres complexes. Pour cette raison, j'ai rendu la classe représentant les matrices, générique. Le type générique K représente le type des éléments contenus dans la matrice. 
Or, pour pouvoir tout de même définir les opérations usuelles sur les matrices (somme, produit, inversion,...), il faut définir des structures algébriques qui régiront les interactions entre les objets de type K.

Il a ensuite été ajouté au projet des algorithmes de machine learning que j'ai implémenté en utilisant les outils pour l'algèbre linéaire préalablement codés.

## Le package "mlearn"

Ici sont stockés tous les algorithmes de machine learning. Pour l'instant deux algorithmes ont été implémentés : 
 - La régression linéaire par descente de gradient ou par l'équation normale (au choix).
 - La régression logistique permettant de résoudre une classification binaire (sortie 0 ou 1)
 
 
Ce package utilise les objets MatR et VectR du package linalg présenté ci-dessous


## Le package "linalg"

Ici sont stockés tous les objets mathématiques utiles pour l'algèbre linéaire : 
 - <strong>Matrix</strong> : représente les matrices contenant des éléments de type générique K. <br>
  Pour pouvoir manipuler les éléments contenus dans les matrices, il faut donc spécifier une structure algébrique sur K(tels qu'un anneau ou un corps)
 - <strong>Vector</strong> : représente des n-uplets d'éléments de type générique K. <br>
 Du fait de la généricité de cette classe, elle demande, à l'instar de Matrix, l'utilisation de structures algébriques.
 

## Le package "linalg.algstruct"

Ici sont ajoutées les différentes structures algébriques qui nous serviront à travailler sur les objets mathématiques de notre choix.

Les structures algébriques sont représentées sous forme d'interface. En effet, une structure algébrique (corps, anneau, espace vectoriel) peut être avant tout vue comme un modèle à respecter (cahier des charges).<br>
Le but dans notre cas est de définir pour un type générique K des opérations utiles tels que la somme ou le produit de deux éléments K.

Il y a quatre structures algébriques représentées : 
<ul>
 <li> <strong>Ring</strong> : Cette interface représente un anneau, elle requiert donc de définir sur un type générique K la somme, le produit, leurs éléments neutres respectifs ainsi que la fonction qui trouve l'inverse d'un élément pour la loi + (En effet, un anneau requiert l'inversibilité par l'addition de tous ses éléments).
 <li> <strong>Field</strong> : Cette interface représente un corps et étend l'interface Ring en exigeant en plus une fonction qui trouve l'inverse d'un élément pour la loi x (En effet, un corps demande l'inversibilité par la multiplication de tous ses éléments sauf 0)
 <li> <strong>VectorSpace</strong> : cette interface représente un K-espace vectoriel sur un type générique E. Elle requiert la somme sur les vecteurs, le produit externe (E x K -> E), le neutre pour la somme, un corps sur l'espace des scalaires, une fonction calculant l'inverse d'un élément pour la somme, une liste de vecteurs représentant une base de l'espace vectoriel. On entend par vecteur ici, non pas un élément de la classe Vector, mais bien un élément de type E.
 <li> <strong>Euclidean</strong> : cette espace représente un R-espace vectoriel euclidien sur un type générique E. Elle étend l'interface VectorSpace pour les scalaires réels (K=Double). Cette interface requiert un produit scalaire et calcule à partir de cette fonction les opérations usuelles sur les angles et longueurs: cos, sin, distance, norme, ect
</ul>
 
Les structures algébriques les plus courantes ont déjà été implémentées :
<ul>
<li> Pour l'interface Ring : Les anneaux usuels sur les réels, complexes et matrices sont implémentés.<br>(Attention cependant : l'anneau sur les matrices n'est en réalité qu'un demi anneau : la loi x n'a pas forcément de neutre)
<li> Pour l'interface Field : les corps usuels sur les réels et les complexes sont implémentés
<li> Pour l'interface VectorSpace : les K-ev usuels sur les matrices et vecteurs sont implémentés. Le R-ev usuel sur les complexes est aussi implémenté
<li> Pour l'interface Euclidean : les R-ev euclidiens usuels sur les matrices, vecteurs et complexes sont implémentés.
</ul>

## Utilité des classes MatR, MatC, VectR et de l'interface VectorSpace

**Contexte** : Etant donné que la classe Matrix permet de gérer les opérations usuelles sur les matrices contenant un type générique K, les méthodes qui en découlent sont lourdes à l'écriture. En effet, l'utilisateur doit spécifier pour chaque méthode (plus,prod,gaussInv) l'anneau ou le corps sur lequel il souhaite travailler.

Pour simplifier cette écriture, trois possibilités ont été trouvées :
 - la première : On manipule les objets Matrix via une implémentation de l'interface VectorSpace qui ne propose que les opérations sur les matrices vues comme des vecteurs. 
 - la seconde : On crée une classe qui hérite de Matrix pour K déterminé. Ce sont les classes MatR, MatC et VectR. Le but est d'éviter à l'utilisateur de devoir spécifier les structures algébriques sur K avec lesquelles il veut travailler. Ces dernières sont prédéfinies comme final static dans chaque classe.
 - la troisième : l'anneau sur le type K utilisé par la matrice est un attribut de la classe, spécifié par l'utilisateur à l'appel du constructeur. Le problème de cette méthode est que si l'on multiplie deux matrices entre elles qui possèdent des anneaux différents, une ambiguïté existe sur la structure algébrique qui sera utilisée pour manipuler les éléments dans les matrices.

## Le package "utils"

Ce package contient tous les objets mathématiques utiles (seulement les complexes pour l'instant) ainsi que toutes les fonctions mathématiques utiles telles que les normes usuelles. 

## Pour finir

Ce projet n'a pas pour finalité une réelle utilité pratique. Ce projet a été nourri par l'envie de relier deux domaines m'intéressant particulièrement : la programmation orientée objet et l'algèbre linéaire. Le but étant de développer mes compétences et connaissances dans ces domaines.

