package Runner;
import java.util.*;
import teamgames.Player;
public class Menu
{
	public static void main (String[] args)
	{
		SaveLoad newSave = new SaveLoad();
		ArrayList <Player> existingPlayers = newSave.runLoad();
		RunRandom runrand = new RunRandom();
		RunSlots runslot = new RunSlots();
		boolean continueGame = false;
		start();
		do {
			String [] options = {"Random Number", "Slots", "Roulette"};
			String game = selectGame(options, existingPlayers);
			switch (game)
			{
			case "random number":
				runrand.randomGame(existingPlayers);
				break;
			case "slots":
				runslot.slotGame(existingPlayers);
				break;
			case "roulette":
				runrand.randomGame(existingPlayers);
				break;
			}
			continueGame = again();
		} while (continueGame == true);
	}
	static void start ()//Displays startup text
	{
		System.out.println("Welcome to our game player!");
		System.out.println("By: Matt Benitez, Naj Junaid, and Justin Thompson.");
	}
	public static String selectGame (String[] opts, ArrayList<Player> loadedPlayers)
	{
		String input = "";
		String inputString = "";
		boolean inputCheck = true;//Used to rerun the input until valid values are selected
		Scanner keyboard = new Scanner(System.in);
		do	{
			System.out.println("\nPlease select the game you would like to play!");
			System.out.println("Your options are: " + opts[0] + ", " + opts[1] + ", " + opts[2]);
			System.out.println("You may enter 'info' for information on the games, or 'scores' for a list of results.");
			try
			{
				input = keyboard.nextLine();
				inputString = input.toLowerCase();
				if (opts[0].equalsIgnoreCase(inputString) || opts[1].equalsIgnoreCase(inputString) || opts[2].equalsIgnoreCase(inputString))
				{
					System.out.println("Thank you for selecting: " + input);
					System.out.println("Your game will now begin.\n");
					inputCheck = false;
					return inputString;
				}
				else if (inputString.equalsIgnoreCase("info"))
				{
					System.out.println("Random Number is a guessing game to find the number between two bounds before the other players do.");
					System.out.println("Slots is a game where the players spin a slot wheel and hope for matches in the three reels.");
					System.out.println("Roulette is a game where players may bet on colors and numbers based on a ball which spins around a wheel.");
				}
				else if (inputString.equalsIgnoreCase("scores"))
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
							System.out.print("| $");
							System.out.format("%-11s", loadedPlayers.get(x).getUsrBal());//Prints user ballance
							System.out.print("| $");
							System.out.format("%-8s", loadedPlayers.get(x).getposBankBal());//Prints bank balance
							System.out.println("|");
						}
						System.out.println("|–––––|–––––––––––|–––––––|––––––––|–––––––––––––|––––––––––|");
					}
				}
				else
				{
					System.out.println("A valid game or option was not entered.");
					System.out.println("You entered " + "'" + input + "'" + "\n");
					inputCheck = true;
				}
			}
			catch (Exception e)
			{
				System.out.println("A valid input was not entered.");
				System.out.println(e.getCause());
				System.out.println("You entered " + "'" + input + "'" + "\n");
				inputCheck = true;
			}
		} while (inputCheck == true);
		return inputString;
	}
	static Boolean again ()//Checks for play again
	{
		Boolean playResult = false;//Used to bring the value back to the main
		Boolean boundRepeat = true;//Used to run the input repeat
		String playSize = "";
		do	{
			System.out.println("Would you like to play another game? (Yes/No)");
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
			playResult = true;
		}
		else
		{
			System.out.println("Thank you for playing!");
			System.out.println("The games will now save and end.");
		}
		return playResult;
	}
}
