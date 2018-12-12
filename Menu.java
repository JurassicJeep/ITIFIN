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
			String [] options = {"Random Number", "Slots", "option3"};
			String game = selectGame(options);
			switch (game)
			{
			case "random number":
				runrand.randomGame(existingPlayers);
				break;
			case "slots":
				runslot.slotGame(existingPlayers);
				break;
			case "option3":
				runrand.randomGame(existingPlayers);
				break;
			}
			continueGame = again();
		} while (continueGame == true);
	}
	static void start ()//Displays startup text
	{
		System.out.println("Welcome to our game player!");
		System.out.println("By: Matt Benitez, Naj Junaid, and Justin Thompson.\n");
	}
	public static String selectGame (String[] opts)
	{
		String input = "";
		String inputString = "";
		boolean inputCheck = true;//Used to rerun the input until valid values are selected
		Scanner keyboard = new Scanner(System.in);
		do	{
			System.out.println("Please select the game you would like to play!");
			System.out.println("Your options are: " + opts[0] + ", " + opts[1] + ", " + opts[2]);
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
				else
				{
					System.out.println("A valid game was not entered.");
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
