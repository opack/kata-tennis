# The project
This is a simple command-line user interface for the scoreboard project.

# Commands
Each command is a single caracter. One can also construct a string with multiple commands in one time. Example : `aaBp` scores 2 points for the player A, 4 for player B and then prints the score board.

Note that unknown commands are ignored. Use this to put some spaces ! Example : `0 AAAAA: BBBBB: A: B:`

## Score points
To score points for a player (when the opponents makes a fault on the court), you can use the following commands 

Command | Description
------- | -----------
a | Score one point for player A
b | Score one point for player B
A | Score 4 points for player A
B | Score 4 points for player B
0 | Resets the scores

## Program manipulation
Some commands are designed to manipulate the UI program rather than the core score board system.

Command | Description
------- | -----------
? | Print the list of available commands
: | Print the score board using a fancy ASCII table
! | Switch on/off auto print scores after each score modification
x | Exit the program

## Examples

Command string | Description
-------------- | -----------
`0 aaa: bbb:` | Set a new match at deuce state in the first game
`0 AAAAA: BBBBB: A: B:` | Set a new match in a tie-break state
`0 AAAAAA: AAAAAA: AAAAAA:` | Set a new match and make player A win it
`0 !ababab !aaaa:` | Score some points with auto-print, then score some points without it
