/**
 * All the tennis scoring contexts are handled here :
 *  - The game : it is the smaller scoring context. It counts the points.
 *  - The set : it counts the number of games each player won. A set has its
 *  own end conditions : at least 6 games won and 2 more than the opponent.
 *  - The match : it counts the number of sets each player won. A match has its
 *  own end condition : 3 sets won.
 * 
 * Each context scores its own kind of value : game scores points, set scores
 * games and match scores sets.
 */
package fr.sgcib.katatennis.scoreboard.contexts;