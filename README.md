Game selector with money/betting and save/load functionality.
by Matt Benitez, Naj Junaid, and Justin Thompson

If it exists, loads save file with existing player data (id, name, password (not shown to user), win count, games played, user wallet and borrowed money).
Allows players to select the game they want to play, or game info, or scoreboard (if existing players).
Once in the game, it allows for optional selection of previous players and requires a preset password to unlock the player (three attempts boots the player back to menu).  Once a player enters their password once, they do not need to re-enter it until they close the session at the main menu.
Takes bet from existing players wallet into the game (some games pool the money, others play against the system).
NOTE: if an existing player does not have enough money, they can borrow money from the bank to keep playing, or choose not to borrow.
Allows n number of new players determined by the users after existing players are selected (new players are optional IF there are existing players in the game).
Allows each new player to set a preferred name.
Created an ID for each player as the primary key, which is used on loading a player to allow for multiple same names.
Takes bet from the new players wallet into the game (some games pool the money, others play against the system).

Game Specific Information
SLOTS
This game plays against the system, so there is no pool but the players can win on a modifier or not win at all.
Generates three slot reels based on a preset list of symbols.
Compares players symbols for payout (if a player has all 7's they win the jackpot, all A's is runner-up prize, and others payout less).
Provides alternate payout if there are two matches instead of three.
Provides score table based on payout modifier with highest payout (or tie) getting a "win" in the system.
Then prompts for playing again.
Saves all players (new and old) into a file for reuse in the user's directory.

RANDOM NUMBER
This game runs on a pool of money depending on player count and displays this pool after each new player joins.
Prompts each player for whether or not they would like to select bounds, then generates random number based on those bounds (inclusive of bounds).
Asks each player for a guess and provides feedback.
If guess is correct it notes so and provides an exclusion instead of further guesses.
When prompting each player following the first round, it provides information the feedback on the previous guess (too high, too low, or correct).
After all players are successful it provides score table (player name, total guesses/valid guesses, games and win count).
Provides payout based on number of players and place. (If one player that player gets their money back, two is 75/25 split, three or more is 75/20/5 with nothing after third).
Least gussses in the system gets a "win".  If its a tie it goes to the first player to guess right.
Then prompts for playing again.
Saves all players (new and old) into a file for reuse in the user's directory.

ROULETTE
This game runs against the system, so there is not pool but players can win on a modifier or not win at all.
Generates roulette color and board (stored in system).
Asks players for color/number selection (location on a roulette board).
Compares board to player guesses, if correct gets jackpot payout, if just number correct gets partial payout, if color correct gets negligible payout.
Highest payout gets a win in the system.
Then prompts for playing again.
Saves all players (new and old) into a file for reuse in the user's directory.

A sample save file (save.csv) has been provided, this should be put in the same directory as the game files in order to run, however the game does not need the save file to start, it can start from a blank game.
