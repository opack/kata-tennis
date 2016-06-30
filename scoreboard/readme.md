We try to display a tennis score board.

I imagined this project as a component that could be used by a GUI. As such, the component should be able to :
 - Store the current scores (game and set count)
 - Give the current score (for the GUI to display it)
 - Change the scores according to the scoring player
 - Determine whether a game is won (meaning a player scores after having 40 points)
 - Determine whether a set is won (meaning a player won 6 games, with 2 more sets than the opponent)
 - Determine whether a match is won (meaning a player won 2 sets)

Rules
 - 1 jeu = 0, 15, 30, 40, A
 - Au moins 6 jeux et 2 jeux d'avance = 1 set
	- Si 6-6 -> tie break : On compte alors les points de 1 à 7. Le premier joueur arrivé à 7 points remporte le set, à condition qu'il ait 2 points d'avance (ex : 7-5). Sinon, le jeu se poursuit jusqu'à ce que cette avance soit obtenue.
		-> Au moins 7 points nécessaires et 2 de plus que l'adversaire (https://en.wikipedia.org/wiki/Tennis_scoring_system#Scoring_a_tiebreak_game)
 - 2 sets = 1 match
 
Je vote pour la simplification des accesseurs. Lorsqu'ils sont inutiles, je préfère les retirer. Ex : dans ScoreBoard, le champ tiebreak est un simple flag. Ajouter un getter et un setter n'apporte rien, si ce n'est compliquer la lecture du code. En attendant l'implémentation des propriétés en Java, ce type de cas peut se gérer avec un public : inutile de compliquer le code en déléguant cette affectation/lecture à une autre méthode qui est elle aussi publique, étant donné qu'on ne fait aucun traitement supplémentaire.