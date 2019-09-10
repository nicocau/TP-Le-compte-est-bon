TP : Le compte est bon
===

http://emmanuel.adam.free.fr/site/spip.php?article203

Enoncé
---
L’objectif de ce TP est de de créer un programme qui permet de trouver à partir de six nombres entiers, et des 4 opérations entières, les étapes de calculs permettant d’atteindre un nombre objectif à 3 chiffres.
Un nombre ne peut être utilisé qu’une seule fois.

Exemple de résultat d’exécution :
```
A partir des opérations sur les nombres  [10, 5,  75,  4,  6,  1]
le but est d'obtenir  654
--
Fin de recherche, valeur trouvée =  654
Nb d'essais effectues =  2580
Détail des opérations :
75 + 10 = 85
5 + 1 = 6
4 * 6 = 24
24 + 85 = 109
6 * 109 = 654
```
Il existe plusieurs façon de coder, mais les codes demandés pour de TP devront utiliser au plus la programmation fonctionnelle.

Monte-Carlo
---
Ce TP utilise un type particulier de recherche de solution qui est la recherche aléatoire (ou méthode de Monte-Carlo).
A chaque étape, on prends 2 nombres au hasard (a et b) parmi les restants (2 parmi 6 au début), on sélectionne aléatoirement une opération op compatible ; c’est à dire que a op b doit donner un nombre entier strictement positif.
Par exemple, si a=5 et b=75, les opération - et / ne sont pas valides, seules + ou x peuvent être sélectionnées. (a et b) sont extraits du tableau, le résultat de a op b est ajouté au tableau.
On répète 5 fois ce processus..
Par exemple, cela peut donner :
```
a partir de [10, 5, 75, 4, 6, 1], tenter de trouver 654
Etape 1
tab = [10, 5, 75, 4, 6, 1]       
tirage de a et b (a=5, b=75)
—> tab = [10, 1, 6, 4, 0, 0],
tirage de op (+) ->  tab = [10  4  6  1 80 0],
Etape 2
tab = [10, 1, 6, 4, 80, 0]       
tirage de a et b (a=80, b=6)
—> tab = [10, 1, 4, 0, 0, 0],
tirage de op (-) ->  tab = [10, 1, 4, 74, 0, 0],
Etape 3
tab = [10, 1, 4, 74, 0, 0]       
tirage de a et b (a=10, b=4)
—> tab = [74, 1, 0, 0, 0, 0],
tirage de op (x) ->  tab = [74, 1, 40, 0, 0, 0],
Etape 4
tab = = [74, 1, 40, 0, 0, 0]       
tirage de a et b (a=40, b=1)
—> tab = [74, 0, 0, 0, 0, 0],
tirage de op (-) ->  tab = [74, 0, 0, 0, 0, 0],
Etape 5
tab = [74, 0, 0, 0, 0, 0]       
tirage de a et b (a=74, b=39)
—> tab = [0, 0, 0, 0, 0, 0],
tirage de op (x) ->  tab = [2886, 0, 0, 0, 0, 0],
```
On s’arrête donc au pire au bout de 5 étapes, ou dès que le but est trouvé.
L’objectif n’étant ici pas atteint, on réitère l’ensemble de l’algorithme précédent ; tant que la solution n’est pas trouvée (ce qui suppose qu’il y en a forcément une) et/ou tant que le nombre d’essais max n’est pas atteint.

Enumeration
---

Il existe plusieurs façon de faire, il est même possible de résoudre le pb en 1 seule classe. Mais le but ici est d’utiliser la programmation fonctionnelle permise par les dernières versions de Java.

Les opérateurs
Le principe de l’algorithle est de piocher 2 nombres a et b parmi les restants et de piocher un opérateur op applicable sur ces 2 nombres.
C’est à dire que le résultatsde a op b doit être un entier strictement positif.

Il y a 4 opérateurs définis : ADDITION, SOUSTRACTION, MULTIPLICATION, DIVISION

Créer l’énumération Operateur qui contient ces termes.
Cet énumération est composé des champs :

String signe; qui est la chaine contenant le signe de l’opération (« + », « - », « * », « / »)
BiFunction<Integer, Integer, Integer>  calcul; qui est une fonction prenant 2 nombres entiers et retournant le résultat de l’opération
BiFunction<Integer, Integer, Boolean>  test; qui est une fonction prenant 2 nombres entiers et retournant vrai si l’opération est retournerait un nb entier strictement positif
L’énumération Operateur possède donc un construceur :
OperateurBis(String _signe, BiFunction<Integer, Integer, Integer> _calcul, BiFunction<Integer, Integer, Boolean> _test)

Test unitaire

Il est important de réaliser des tests unitaires de morceaux de projet.
Utiliser la classe suivante pour tester votre énumération :

```Java
public class Test {
 static void testOpe() {
  Operateur op = Operateur.SOUSTRACTION;
  int a=6, b=5;
  boolean ok = op.test.apply(a,b);
  System.out.println("faisabilite de " + a + " " + op.signe + " " + b + " = " + ok);
  if (ok)
   System.out.println("resultat de " + a + " " + op.signe + " " + b + " = " + op.calcul.apply(a,b));;
 }
 public static void main(String[] args) {
  testOpe();
 }
}
```

Solution
---

Définir la classe Solution, contenant les champs :

int but; : nombres a utiliser
int[] nombres; : solution à trouver
Stack<Integer> valeursLibres; : valeurs libres (valeurs pouvant être utilisée pour le calcul). Une pile permet de dépiler et d’empiler des valeurs
StringBuilder etapes : enchainement des étapes de calcul effectuées sous forme de chaines de caractères
int but; : solution à trouver
Voici la classe abstraite qu’il faut étendre et donc implémenter :
```Java
import java.util.Stack;
 
/** classe abstraite à étendre pour permettre la resolution du compte est bon
 * @author emmanueladam
 * */
abstract class SolutionAbstraite {
 /** solution à trouver */
 int but;
 /** nombres a utiliser */
 int[] nombres;
 /** valeurs libres (valeurs pouvant être utilisée pour le calcul) */
 Stack<Integer> valeursLibres;
 /** enchainement des etapes sous forme de chaines de caracteres*/
 StringBuilder etapes;
 
 //constantes pour optimiser la création de la chaine des étapes
 static final String SPACE = " ";
 static final String EGAL = " = ";
 static final String SEP = " ; ";
 
/**
 *  initialise l'algorithme avec de nouveaux nombres et un nouveau but
 *
 * @param _nombres
 *            nombres à utiliser pour le calcul
 * @param _but
 *            nombre à atteindre
 */
abstract void init(int[] _nombres, int _but);
 
/**
 * reinitialise les variables globales
 * replace les 6 nombres de base dans la pile valeursLibres et
 * efface la liste des étapes
 */
abstract void reinit();
 
/**
 * fonction de calcul d'une solution par méthode aléatoire <br>
 * effectue au plus 5 cycles (pioche de 2 nb, pioche d'1 op)
 * @return la meilleure solution trouvée
 */
abstract int calcule();
 
/**choix au hasard d'un operateur applicable sur nb1 et nb2
 * @param nb1 premier argument de l'operation
 * @param nb2 second argument de l'operation
 * @return nb entre 0 et 3 representant l'operateur dans (+, -, *, /)*/
abstract Operateur choixOperateurApplicable(int nb1, int nb2);
 
/**
 * fonction  qui lance la recherche d'une solution
 * jusqu'a ce que la solution exacte soit trouvee
 * ou que le nombre de tests max a été atteint
 * @param nbCalculsMax
 *            nb max de tests de solution autorisé
 * @return l'enchainement des etapes menant à la solution ou à la valeur la
 *         plus proche trouvée, séparées par des ';' le dernier élément de la chaine est la valeur trouvée
 */
abstract  String testDesSols( int nbCalculsMax);
}
```
Dans une fonction main, lancez 5 résolutions du problème suivant :
trouver 533, avec : [2, 3, 7, 9, 15, 75] avec une limite de 10000 tests par résolution


Processus
---

Cette fois, dans la fonction main, lancez 5 résolution sous forme de processus, en utilisant la syntaxe abbrégée permise par les lambdas expression.

Ajoutez ces processus dans un groupe et stoppez les dès qu’un processus a trouvé 1 solution.

Tentez de trouver 987 à partir de 2,4,6,8,15,75 en limitant à 3000 tests par résolution.

Note perso :
---
Il faudra utiliser la méthode de Monte Carlo.
