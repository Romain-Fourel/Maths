# Maths

Ce projet a pour but initial de pouvoir stocker dans des matrices d'autres objets que des nombres réels (classe Double) comme des nombres complexes par exemple.
Or, pour pouvoir tout de même définir les opérations usuelles sur les matrices (somme, produit, inversion,...), il faut définir des structures algébriques qui régiront les interactions entre les objets.


## Le package "linalg.objects"

Ici sont stockés tous les objets mathématiques utiles pour l'algèbre linéaire : 
 - Matrix : représente les matrices contenant des éléments de type générique K. <br>
  Pour pouvoir manipuler les éléments contenus dans les matrices, il faut donc spécifier une structure algébrique (tels qu'un anneau ou un corps)
 - Vector : représente des n-uplets d'éléments de type générique K. <br>
 Du fait de la généricité de cette classe, elle demande, à l'instar de Matrix<K>, l'utilisation de structures algébriques.
 - Complex : représente les nombres complexes et implémente les principales fonctions usuelles (somme,produit,module,élément conjugé,ect)

## Le package "linalg.algstruct"

Ici sont ajoutées les différentes structures algébriques qui nous serviront à travailler sur les objets mathématiques de notre choix.

Les structures algébriques sont représentés sous forme d'interface. En effet, une structure algébrique (corps, anneau, espace vectoriel) peut être avant tout vue comme un modèle à respecter.<br>
Le but dans notre cas est de définir pour un type générique K des opérations utiles tels que la somme ou le produit de deux éléments K.

Il y a quatre structures algébriques implémentées : 
<ul>
 <li> Ring : Cette interface représente un anneau, elle requiert donc de définir sur un type générique K la somme, le produit, leurs éléments neutre respectifs ainsi que la fonction qui trouve l'inverse d'un élément pour la loi + (En effet, un anneau requiert l'inversibilité par l'addition de tous ses éléments).
 <li> Field : Cette interface représente un corps et étend l'interface Ring en exigeant en plus une fonction qui trouve l'inverse d'un élément pour la loi x (En effet, un corps demande l'inversibilité par la multiplication de tous ses éléments sauf 0)
 <li> VectorSpace : cette interface représente un K-espace vectoriel sur un type générique E. Elle requiert la somme sur les vecteurs, le produit externe, le neutre pour la somme, un corps sur l'espace des scalaires, une fonction calculant l'inverse d'un élément pour la somme, une liste de vecteurs représentant une base de l'espace vectoriel.<br>
On entend par vecteur ici, non pas un élément de la classe Vector, mais bien un élément de type E.
 <li> Euclidean : cette espace représente un R-espace vectoriel euclidien sur un type générique E. Elle étend l'interface VectorSpace pour les scalaires réels (K=Double). Cette interface requiert un produit scalaire et calcule à partir de cette fonction les opérations usuelles : cos, sin, distance, norme, ect
</ul>
 
<p>
Les structures algébriques les plus courantes ont déjà été implémentées :
<li> Pour l'interface Ring : Les anneaux usuels sur les réels, complexes et matrices sont implémentés.<br>(Attention cependant : l'anneau sur les matrices n'est en réalité qu'un demi anneau : la loi x n'a pas forcément de neutre)
<li> Pour l'interface Field : les corps usuels sur les réels et les complexes sont implémentés
<li> Pour l'interface VectorSpace : les K-ev usuels sur les matrices et vecteurs sont implémentés. Le R-ev usuel sur les complexes est aussi implémenté
<li> Pour l'interface Euclidean : les R-ev euclidiens usuels sur les matrices, vecteurs et complexes sont implémentés.
</p>

## Les classes MatC,MatR et VectR : 

Ces classes ont vu le jour après le constat suivant : du fait de la généricité de la classe Matrix, l'utilisation de cette dernière est assez lourde en écriture. En effet, pour chaque opération utilisée (plus,prod,inv), il faut spécifier l'anneau ou le corps utilisé pour les éléments contenus dans la matrice.

MatC et MatR implémentent donc Matrix avec respectivement K=Complex et K=Double. Ainsi, les corps utilisés sont prédéfinis et fixes. L'utilisateur n'a plus à s'en soucier.

Il en est de même pour VectR avec la classe Vector et K=Double.

# Pour finir

Ce projet n'a finalement pas pour réel but d'optimiser l'écriture de code ou l'implémentation de nouvelles structures. Il a seulement pour but d'harmoniser du mieux possible les relations entre objets et structures tout en essayant de rester le plus général possible.


