package Runner;
import java.util.*;
import java.util.stream.Collectors;
import teamgames.Player;
public class Menu
{
	public static void main (String[] args)
	{
		SaveLoad newSave = new SaveLoad();
		RunGame runrand = new RunGame();
		boolean continueGame = false;
		start();
		do {
			String [] options = {"Random Number Game", "Slots", "option3"};
			String game = selectGame(options);
			switch (game)
			{
			case "random number game":
				runrand.runRandom();
				break;
			case "slots":
				runrand.runRandom();
				break;
			case "option3":
				runrand.runRandom();
				break;
			}
		} while (continueGame == true);
		/*SaveLoad newSave = new SaveLoad();
		ArrayList <Player> existingPlayers = newSave.runLoad();
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
		} while (continueGame == true);*/
	}
	static void start ()//Displays startup text
	{
		System.out.println("Welcome to our game player!\n");
		System.out.print("By: Matt Benitez, Naj Junaid, and Justin Thompson.");
	}
	public static String selectGame (String[] opts)
	{
		System.out.println("Please select the game you would like to play");
		System.out.println("Your options are: " + opts[0] + ", " + opts[1] + ", " + opts[2]);
		String input = "";
		String inputString = "";
		boolean inputCheck = false;//Used to rerun the input until valid values are selected
		Scanner keyboard = new Scanner(System.in);
		do	{
			try
			{
				input = keyboard.next();
				inputString = input.toLowerCase();
				inputCheck = false;
				if (inputString == opts[0].toLowerCase() || inputString == opts[1].toLowerCase() || inputString == opts[2].toLowerCase())
				{
					System.out.println("Thank you for selecting:" + input);
					System.out.println("Your game will now begin.");
				}
				else
				{
					System.out.println("A valid game was not entered.");
					System.out.println("You entered " + "'" + input + "'" + "\n");
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
}
