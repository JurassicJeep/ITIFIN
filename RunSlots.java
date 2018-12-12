package Runner;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import teamgames.Player;
public class RunSlots
{
	public void slotGame (ArrayList<Player> existingPlayers)
	{
		SaveLoad newSave = new SaveLoad();
		boolean continueGame = false;
		do {
			startGame();
			selectExisting(existingPlayers);
			int players = countPlayers(existingPlayers);
			createPlayers(players, existingPlayers); 
			proceedGame(existingPlayers);
			ArrayList <Player> results = generateResults(existingPlayers);
			provideResults(results);
			newSave.runSave(existingPlayers);
			reset(existingPlayers);
			continueGame = playCheck();
		} while (continueGame == true);
		return;
	}
	static void startGame ()//Displays startup text
	{
		System.out.println("Welcome to the Slots game.\n");
	}
	static void selectExisting (ArrayList <Player> loadedPlayers)
	{
		if (loadedPlayers.size() == 0)
		{
			System.out.println("No existing players found");
		}
		else
		{
			System.out.println("|–––––––––––––––––––––Current Players ––––––––––––––––––––––|");
			System.out.println("| ID  | Name      | Games | Win CT | User Wallet | Borrowed |");
			System.out.println("|–––––|–––––––––––|–––––––|––––––––|–––––––––––––|––––––––––|");
			for (int x = 0; x < loadedPlayers.size(); x++)
			{
				System.out.print("| ");
				System.out.format("%-4d", loadedPlayers.get(x).getID());
				System.out.print("| ");
				System.out.format("%-10s", loadedPlayers.get(x).getName());//Prints player names
				System.out.print("| ");
				//System.out.print(loadedPlayers.get(x).getPwd() + " | ");//FOR TESTING ONLY
				System.out.format("%-6s", loadedPlayers.get(x).getGames());
				System.out.print("| ");
				System.out.format("%-7s", loadedPlayers.get(x).getWinCT());//Prints wincount
				System.out.println("|");
				System.out.format("%-12d", loadedPlayers.get(x).getUsrBal());//Prints user ballance
				System.out.println("|");
				System.out.format("%-9d", loadedPlayers.get(x).getposBankBal());//Prints bank balance
				System.out.println("|");
			}
			System.out.println("|–––––|–––––––––––|–––––––|––––––––|–––––––––––––|––––––––––|");
		}
		boolean morePlayers = false;
		String addPlayers = "";
		do {//Main do, allows multiple players to be selected
			String inputString = "";
			boolean playerCheck = false;
			Scanner keyboard = new Scanner(System.in);
			int input = 0;
			do {//Sub do, checks for proper ID input
				try {
					System.out.println("\nEnter the ID of an existing player you wish to import or enter none to skip player inport.");
					inputString = keyboard.next();
					if (inputString.equals("none"))
					{
						return;
					}
					input = Integer.parseInt(inputString.trim());//removes excess spaces
					final Integer checkInput = input;
					boolean idCheck = loadedPlayers.stream().anyMatch(p -> p.getID() == checkInput);//Checks to make sure playerID is in the list of existing IDs
					if (idCheck == false)	{//Checks invalid ID
						System.out.println("You entered an invalid player ID (" + input + ").");
						playerCheck = true;
					}
					else if (idCheck == true)
					{
						boolean playerExists = playerPresent(input, loadedPlayers);
						if (playerExists == true)
						{
							System.out.println("This player has already been selected, choose another.");
							playerCheck = true;
						}
						boolean rightpwd = passwordCheck(input, loadedPlayers);
						boolean wantbet = loadedPlayers.stream().filter(Player -> checkInput.equals(Player.getID())).findFirst().orElse(null).setBet(5);
						if (rightpwd == true && wantbet == true)
						{
							System.out.println("Player added");
							loadedPlayers.stream().filter(Player -> checkInput.equals(Player.getID())).findFirst().orElse(null).setPlayingTrue();//Sets playing value to true for current game
							playerCheck = false;
						}
					}
				} catch (Exception e) {//if input is invalid catches error and displays text
					System.out.println("A valid response was not entered.  Please an existing player ID or none.");
					System.out.println(e.getCause());
					System.out.println("You entered " + "'" + inputString + "'\n");
					playerCheck = true;
				}
			} while (playerCheck == true);
			boolean validResponse = true;
			do {//sub do ensures valid yes/no input is entered
				if (loadedPlayers.stream().allMatch(p -> p.getPlaying().equals(true)) == true)//if all players selected, exits
				{
					System.out.println("All players have been selected, proceeding.");
					playerCheck = false;
					morePlayers = false;
					return;
				}
				try {
					System.out.println("Would you like to add another player? (Yes/No)");
					addPlayers = keyboard.next();
					addPlayers = addPlayers.toLowerCase();
					if (addPlayers.equals("yes"))
					{
						morePlayers = true;
					}
					else if (addPlayers.equals("no"))
					{
						morePlayers = false;
					}
					else
					{
						System.out.println("You have entered an invalid selection, (" + addPlayers + ") please try again.");
					}
					validResponse = false;
				} catch (Exception e) {
					System.out.println("You have entered an invalid selection, (" + addPlayers + ") please try again.\n");
					System.out.println(e.getCause());
				}
			} while (validResponse == true);
		} while (morePlayers == true);
	}
	static boolean playerPresent (int playerID, ArrayList <Player> current)
	{
		boolean isPresent = false;
		if (current.stream().filter(x -> x.getID() == playerID).findFirst().orElse(null).getPlaying() == true)//checks if player already has playing value set
		{
			isPresent = true;
		}
		return isPresent;
	}
	static boolean passwordCheck (int playerID, ArrayList <Player> current)//Function to verify user is legitimate based on password
	{
		boolean rightPwd = false;
		if (current.stream().filter(x -> x.getID() == playerID).findFirst().orElse(null).getAuth() == true)//If user is already auth, skip password entry
		{
			return rightPwd = true;
		}
		int wrongCT = 0;
		String savedpwd = current.stream().filter(x -> x.getID() == playerID).findFirst().orElse(null).getPwd();//pulls password based on userid to a string that can be used more easily
		String inputpwd = "";
		Boolean pwdRepeat = true;
		System.out.println("Player ID: " + playerID + " Name: "+ current.stream().filter(x -> x.getID() == playerID).findFirst().orElse(null).getName() + " please enter your passowrd.");
		System.out.println("If you have selected the wrong player, please enter three wrong passwords to return to the menu.");
		do	{
			if (wrongCT >= 3)//If more than three wrong guesses, boots player back to previous menu
			{
				return rightPwd = false;
			}
			Scanner keyboard = new Scanner(System.in);
			inputpwd = keyboard.next();
			inputpwd = inputpwd.trim();
			if (inputpwd.matches("[^a-zA-Z0-9_]"))//RegEx to ensure only letters
			{
				System.out.println("You have entered an invalid selection, (" + inputpwd + "), this contains other characters than letters, please try again.");
				pwdRepeat = true;
			}
			else if (savedpwd.equals(inputpwd))//if passwords are equal adds player and indicates so
			{
				System.out.println("Password correct, adding player");
				current.stream().filter(x -> x.getID() == playerID).findFirst().orElse(null).setAuthTrue();//sets authenticated to true
				pwdRepeat = false;
				rightPwd = true;
			}
			else//if password is not equal
			{
				System.out.println("You have entered an invalid password, (" + inputpwd + "), please try again.");
				wrongCT = wrongCT + 1;
				pwdRepeat = true;
			}
		} while (pwdRepeat == true);
		return rightPwd;
	}
	static int countPlayers (ArrayList <Player> loadedPlayers)//Gets player count
	{
		int input = 0;
		String inputString = "";
		boolean numCheck = false;//Used to rerun the input until valid values are selected
		Scanner keyboard = new Scanner(System.in);
		String playerNum = "1";
		int playerCT = 0;
		String existing = "";
		try {
			if (loadedPlayers.stream().anyMatch(p -> p.getPlaying().equals(true)) == true)//If any existing players allows no new players
			{
				playerNum = "0";
				playerCT = -1;
				existing = "This is in addition to any existing players.";
			}
		} catch (Exception e)
		{
			//Catches exception and proceeds
		}
		do	{
			try {
				System.out.println("Please enter a number of players (" + playerNum + " or more). " + existing);
				inputString = keyboard.next();
				input = Integer.parseInt(inputString.trim());//removes excess spaces
				numCheck = false;
				if (input <= playerCT)	{//Checks for zero or lower number
					System.out.println("You entered an invalid number (" + input + ").");
					numCheck = true;
				}
			} catch (Exception e) {//if input is invalid catches error and displays text
				System.out.println("A valid positive integer was not entered.  Please enter a number of 1 or more.");
				System.out.println(e.getCause());
				System.out.println("You entered " + "'" + inputString + "'" + "\n");
				numCheck = true;
			}
		} while (numCheck == true);
		return input;
	}
	static ArrayList <Player> createPlayers (int playerCount, ArrayList <Player> current)//Creates player objects, playercount is passed to determine number of runs
	{
		for (int x = 0; x < playerCount; x++)
		{
			String name = namePlayers(x+1);//Gets player name
			current.add(new Player(name,current));
			//current.get(x).setupRandGame(); old code that was not save prepared
		}
		for (int x = 0; x < current.size(); x++)
		{
			if (current.get(x).getPlaying() == true)//only for playing players
			{
				current.get(x).setupSlots();//generates game
			}
		}
		return current;
	}
	static String namePlayers (int playernum)//creates player name&objects (player.java creates rand at this point)
	{
		String playername = "";
		Boolean nameRepeat = true;
		do	{
			System.out.println("Player " + playernum + " please enter your name:");
			Scanner keyboard = new Scanner(System.in);
			playername = keyboard.next();
			//playername = playername.toUpperCase();//Used previously when Name was primary key
			if (playername.matches("[^a-zA-Z_]"))
			{
				System.out.println("You have entered an invalid selection, (" + playername + "), this contains other characters than letters, please try again.");
				nameRepeat = true;
			}
			else if (playername.length() > 10)//Used to preserve table size
			{
				System.out.println("Your name is too long, (" + playername + "), please enter a name less than 10 characters.");
				nameRepeat = true;
			}
			else
			{
				nameRepeat = false;
			}
		} while (nameRepeat == true);
		return playername;
	}
	static void proceedGame (ArrayList <Player> playerList)//used to play the game, passes player count and player list
	{
		for (int x = 0; x < playerList.size(); x++)
		{			
			if (playerList.get(x).getPlaying() == true /*&& playerList.get(x).getCorrect() == false*/)//only for playing players
			{
				playerList.get(x).showSlots();
				checkSlots(playerList.get(x));
			}
		}
	}
	static void checkSlots (Player gambler)
	{
		if (gambler.getSlotReel().stream().distinct().count() <= 1)
		{
			if (gambler.getSlot(1).equalsIgnoreCase("7"))
			{
				gambler.setPayout(70);
			}
			else if (gambler.getSlot(1).equalsIgnoreCase("A"))
			{
				gambler.setPayout(8);
			}
			else if (gambler.getSlot(1).equalsIgnoreCase("ø"))
			{
				gambler.setPayout(2);
			}
			else if (gambler.getSlot(1).equalsIgnoreCase("§"))
			{
				gambler.setPayout(1.5);
			}
			else
			{
				gambler.setPayout(1.1);
			}
		}
		else if (gambler.getSlotReel().stream().distinct().limit(2).count() <= 2)
		{
			gambler.setPayout(1.05);
		}
		else
		{
			gambler.setPayout(0);
		}
		System.out.println("Your payout is: " + gambler.getPayout() + ". Or in terms of dollars: " + gambler.getPayment());
	}
	static ArrayList <Player> generateResults (ArrayList <Player> playerList) //takes and sorts playerList
	{
		ArrayList <Player> trophyroom = 
				(ArrayList<Player>) playerList.stream()
				.filter(p -> p.getPlaying() == true)  // only keep those who are playing
				.sorted(Comparator.comparing(Player::getPayout)) // sort by payout
				.collect(Collectors.toList());
		if (trophyroom.get(0).getPayout() != 0)
		{
			trophyroom.get(0).increaseWincount();//increases wincount for the winner	
		}
		for (int x = 0, y = 0; x < trophyroom.size(); x++)
		{
			if (trophyroom.get(0).getPayout() == trophyroom.get(1).getPayout())
			{
				trophyroom.get(1).increaseWincount();//increases wincount if there is a tie
			}
		}
		return trophyroom;
	}
	static void provideResults (ArrayList <Player> playerList)
	{
		System.out.println("|–––––––––––––––––––––––––––––––––––––––––––– Players ––––––––––––––––––––––––––––––––––––––––|");
		System.out.println("| Place | ID  | Name      | Slot Reels | Payment |  Win CT | User Wallet | Payment | Borrowed |");
		System.out.println("|–––––––|–––––|–––––––––––|––––––––––––|–––––––––|–––––––––|–––––––––––––|–––––––––|––––––––––|");
		for (int x = 0; x < playerList.size(); x++)
		{
			int y = x + 1;
			System.out.print("| ");
			System.out.format("%-6d", y);//Prints place
			System.out.print("| ");
			System.out.format("%-4d", playerList.get(x).getID());//Prints ID
			System.out.print("| ");
			System.out.format("%-10s", playerList.get(x).getName());//Prints player names
			System.out.print("| ");	
			System.out.format("%-11s", playerList.get(x).getSlotReel());//Prints player names
			System.out.print("| $");
			System.out.format("%-7s", playerList.get(x).getPayment());//Prints player names
			System.out.print("| ");
			System.out.format("%-7s", playerList.get(x).getWinCT());//Prints wincount
			System.out.print("| $");
			System.out.format("%-11s", playerList.get(x).getUsrBal());//Prints user ballance
			System.out.print("| $");
			System.out.format("%-7s", playerList.get(x).getPayment());//Prints user payment
			System.out.print("| $");
			System.out.format("%-8s", playerList.get(x).getposBankBal());//Prints bank balance
			System.out.println("|");
		}
		System.out.println("|–––––––|–––––|–––––––––––|––––––––––––|–––––––––|–––––––––|–––––––––––––|–––––––––|––––––––––|");
	}
	static void reset (ArrayList <Player> current)//resets playing value to false so that players can be readded on reply, but does not reset auth value
	{
		for (int x = 0; x < current.size(); x++)
		{
			current.get(x).setPlayingFalse();
		}
	}
	static Boolean playCheck ()//Checks for play again
	{
		Boolean playResult = false;//Used to bring the value back to the main
		Boolean boundRepeat = true;//Used to run the input repeat
		String playSize = "";
		do	{
			System.out.println("Would you like to play again? (Yes/No)");
			Scanner keyboard = new Scanner(System.in);
			playSize = keyboard.next();
			playSize = playSize.toLowerCase();
			if (playSize.equals("yes")||playSize.equals("no"))
			{
				boundRepeat = false;
				break;
			}
			System.out.println("You have entered an invalid selection, (" + playSize + ") please try again.");
		} while (boundRepeat == true);
		if (playSize.equals("yes"))
		{
			System.out.println("The game will begin again.");
			playResult = true;
		}
		else
		{
			System.out.println("Thank you for playing!");
			System.out.println("The game will now save and end.");
		}
		return playResult;
	}
}