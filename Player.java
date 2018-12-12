package teamgames;
import java.util.*;
import java.lang.Boolean;
import java.math.BigDecimal;
import java.math.RoundingMode;
public class Player {
	protected String name;
	private String password;
	private int rand;
	private int totalguesscount;
	private int validguesscount;
	private Boolean correct;
	private Boolean[] bounds;
	private int lower;
	private int upper;
	private int lastguess;
	private ArrayList<Integer> guesslist;
	private String guesslistRouletteNumber;
	private String guesslistRouletteColor;
	private int wincount;
	private int id;
	private boolean playing;
	private boolean auth;
	private int games;
	private ArrayList<String> slotreels;
	private double userbalance;
	private double bankbalance;
	private double bet;
	private double payout;
	private double payment;
	private int placedNumber;
	private String board;
	private String Colorboard;
	private boolean colortrue = false;
	private boolean numtrue = false;
	public Player(String playerName, ArrayList<Player> current)// Constructor for new players
	{
		id = setID(current);
		name = playerName;
		password = setPwd(id, name);
		wincount = 0;
		playing = true;
		auth = true;
		games = 1;
		userbalance = 50.0;
		bankbalance = 0;
	}

	public Player(int Id, String name, String pwd, int gamesplayed, int wincounter, double userbal, double bankbal)// Constructor
	// for
	// existing
	// players
	{
		this.id = Id;
		this.name = name;
		this.password = pwd;
		this.games = gamesplayed;
		this.wincount = wincounter;
		this.userbalance = userbal;
		this.bankbalance = bankbal;
		playing = false;
	}

	public String getName()// returns player name
	{
		return name;
	}

	public int getRand()// returns random number
	{
		return rand;
	}

	public int getUpper()// returns upper bound
	{
		return upper;
	}

	public int getLower()// returns lower bound
	{
		return lower;
	}

	public int getTotGuess()// returns total guess count
	{
		return totalguesscount;
	}

	public int getValGuess()// returns valid guess count
	{
		return validguesscount;
	}

	public ArrayList<Integer> getGuessList()// returns guesslist
	{
		return guesslist;
	}

	public Boolean getCorrect()// returns value of correct
	{
		return correct;
	}

	public int getWinCT() {
		return wincount;
	}

	public String getPwd()// returns player name
	{
		return password;
	}

	public Integer getID() {
		return id;
	}

	public Boolean getPlaying() {
		return playing;
	}

	public void increaseWincount() {
		wincount = wincount + 1;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getAuth() {
		return auth;
	}

	public void setAuthTrue() {
		auth = true;
	}

	public void setAuthFalse() {
		auth = false;
	}

	public void setPlayingFalse() {
		playing = false;
		bet = 0;
		payout = 0;
		payment = 0;
	}

	public int getGames() {
		return games;
	}

	public void playedGame() {
		games = games + 1;
	}

	public String getSlot(int x) {
		String slot = slotreels.get(x);
		return slot;
	}

	public double getUsrBal() {
		BigDecimal bd = new BigDecimal(userbalance);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public double getBankBal() {
		BigDecimal bd = new BigDecimal(bankbalance);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public double getposBankBal() {
		BigDecimal bd = new BigDecimal(bankbalance);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		double posBankBal = Math.abs(bd.doubleValue());
		return posBankBal;
	}

	public void setPayout(double x) {
		payout = x;
	}

	public ArrayList<String> getSlotReel() {
		return slotreels;
	}

	public double getPayout() {
		return payout;
	}

	public double getPayment() {
		return payment;
	}

	public int setID(ArrayList<Player> current)// used to find lowest unused playerID
	{
		int playerID = 0;
		Collections.sort(current, (player1, player2) -> player2.getID() - player1.getID());// sets based on descending
		// tbh don't need this
		// because it goes based on
		// size
		int highestID = current.size();
		playerID = highestID + 1;
		return playerID;
	}

	private String setPwd(int pId, String name) {
		String pwd = "";
		Boolean pwdRepeat = true;
		do {
			System.out.println(
					"Player ID: " + id + " Name: " + name + " please enter your password for use in future games.");
			Scanner keyboard = new Scanner(System.in);
			pwd = keyboard.next();
			pwd = pwd.trim();
			if (pwd.matches("[^a-zA-Z0-9_]")) {
				System.out.println("You have entered an invalid selection, (" + pwd
						+ "), this contains other characters than letters, please try again.");
				pwdRepeat = true;
			} else {
				pwdRepeat = false;
			}
		} while (pwdRepeat == true);
		return pwd;
	}

	public void setPlayingTrue() {
		playing = true;

	}

	public double getBet() {
		return bet;
	}

	public boolean setBet(double playerbet) {
		boolean response = false;
		System.out.println("This game requires a bet of: " + playerbet);
		if (userbalance < playerbet) {
			System.out.println("Your wallet is " + userbalance + " this is below the " + bet + "value.");
			System.out.println("Would you like to borrow $50? If you do not borrow money you cannot play");
			boolean validResponse = false;
			Scanner keyboard = new Scanner(System.in);
			do {// sub do ensures valid yes/no input is entered
				String answer = "";
				try {
					System.out.println("Would you like to borrow from the bank? (Yes/No)");
					answer = keyboard.next();
					answer = answer.toLowerCase();
					if (answer.equals("yes")) {
						bankbalance = bankbalance - 50;
						userbalance = userbalance + 50;
						bet = playerbet;
						userbalance = userbalance - bet;
						response = true;
						return response;
					} else if (answer.equals("no")) {
						System.out.println("Ok, you have not borrowed any money, you cannot play the game");
						response = false;
						return response;
					} else {
						System.out
						.println("You have entered an invalid selection, (" + response + ") please try again.");
					}
					validResponse = false;
				} catch (Exception e) {
					System.out.println("You have entered an invalid selection, (" + response + ") please try again.\n");
					System.out.println(e.getCause());
				}
			} while (validResponse == true);
		} else {
			bet = playerbet;
			System.out.println(
					"Your wallet is " + userbalance + " you have enough money to bet, withdrawing " + bet + ".");
			userbalance = userbalance - bet;
			response = true;
			return response;
		}
		return response;
	}

	public void setRandomPayment(double fund) {
		payment = payout * fund;
		adjustBal();
	}

	public void setPayment() {
		payment = payout * bet;
		if (payment > 0) {
			adjustBal();
			System.out.println("Adjusting wallet with winnings.");
		}
	}

	public void adjustBal() {
		double baltotal = bankbalance + payment;
		if (bankbalance < 0 && baltotal < 0) {
			bankbalance = payment + bankbalance;
		} else if (bankbalance < 0 && baltotal > 0) {
			double partialbal = payment + bankbalance;
			bankbalance = 0;
			userbalance = userbalance + partialbal;
		} else if (bankbalance >= 0) {
			userbalance = userbalance + payment;
		}
	}

	public void setupRandGame()// Used to generate variables needed for random number game
	{
		System.out.println("\nPlayer ID: " + id + " Name: " + name);
		bounds = boundCheck();
		Boolean boundRepeat = true;
		do {
			lower = inputBound(bounds[0], "lower");// Generates lower bound or standard value
			upper = inputBound(bounds[1], "upper");// Generates upper bound or standard value
			if (lower > upper) {
				boundRepeat = true;
				System.out.println("Your lower bound is greater than your upper bound, please try again");
			} else if (lower + 1 >= upper || upper - 1 <= lower) {
				boundRepeat = true;
				System.out.println("There is no valid number between the bounds, please try again");
			} else {
				boundRepeat = false;
				break;
			}
		} while (boundRepeat == true);
		rand = randomnumber(lower, upper);
		lastguess = 0;
		totalguesscount = 0;
		validguesscount = 0;
		guesslist = new ArrayList<Integer>();
		correct = false;
	}

	public void setupSlots() {
		slotreels = new ArrayList<String>();
		String[] reels = { "7", "§", "A", "ø" };
		for (int x = 0; x < 3; x++) {
			int reelpos = randomnumber(0, 3);
			String val = reels[reelpos];
			slotreels.add(val);
		}
	}

	public void showSlots() {
		System.out.println("Spinning the slot machine for " + name + "!");
		System.out.println("–––––––––––––––");
		for (int x = 0; x < 3; x++) {
			System.out.print("| ");
			System.out.print(slotreels.get(x));
			System.out.print(" |");
		}
		System.out.println("\n–––––––––––––––");
	}

	public void setupRoulette() {
		String[] boardShow = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32" };
		String[] colorShow = { "Red", "Black" };
		int rollspot = randomnumber(0, 32);
		//System.out.println(rollspot);checking
		int color = randomnumber(0, 1);
		//System.out.println(color);
		board = boardShow[rollspot];
		Colorboard = colorShow[color];
		guesslistRouletteNumber = getRouletteNumber(boardShow);
		guesslistRouletteColor  = getRouletteColor(colorShow);
		if (guesslistRouletteNumber.equalsIgnoreCase(board))
		{
			numtrue = true;
		}
		if (guesslistRouletteColor.equalsIgnoreCase(Colorboard))
		{
			colortrue = true;
		}
	}
	public String getRouletteNumber (String [] boardShow)
	{
		Scanner keyboard = new Scanner(System.in);
		int guessNumber = 0;
		String Strnum = "";
		boolean numCheck = true;
		do {
			try {
				System.out.println("Player ID: " + id + " name: " + name + ", what number (1-32) would you like to bet on?");
				guessNumber = keyboard.nextInt();
				Strnum = String.valueOf(guessNumber);
				if (guessNumber > 0 && guessNumber <= 32)
					{
						numCheck = false;
						return Strnum;
					}
				else
				{
					System.out.println("You have entered a number (" + guessNumber + ") outside the acceptable range.");
				}
			} catch (Exception e) {
				System.out.println("A valid number was not entered.");
				System.out.println(e.getCause());
				System.out.println("You entered " + "'" + guessNumber + "'" + "\n");
				numCheck = true;
			}
		} while (numCheck == true);
		return Strnum;
	}
	public String getRouletteColor (String[] colorShow)
	{
		String guessColor = "";
		Scanner keyboard = new Scanner(System.in);
		boolean numCheck = true;
		do {
			try {
				System.out.println("Player ID: " + id + " name: " + name + ", what color would you like to place on (red or black)?");
				guessColor = keyboard.next();
				guessColor = guessColor.toLowerCase();
				if (guessColor.equalsIgnoreCase("red") || guessColor.equalsIgnoreCase("black"))
					{
						numCheck = false;
						return guessColor;
					}
				else
				{
					System.out.println("You have entered a color '" + guessColor + "' that is not red or black.");
				}
			} catch (Exception e) {
				System.out.println("A valid color was not entered.");
				System.out.println(e.getCause());
				System.out.println("You entered " + "'" + guessColor + "'" + "\n");
				numCheck = true;
			}
		} while (numCheck == true);
		return guessColor;
	}
	public void showRoulette() {
		System.out.println("Spinning the ball for " + name + "!");
		System.out.println("...and it lands on");
		System.out.println("–––––––––––––––");
		System.out.print("| ");
		System.out.print(Colorboard + " ");
		System.out.print(board);
		System.out.print(" |");
		System.out.println("\n–––––––––––––––");
	}


	static Boolean[] boundCheck() // Calculates if bounds are selectable
	{
		Boolean boundRepeat = true;
		String boundSize = "";
		Boolean[] ans = new Boolean[2];
		do {
			System.out.println("Would you like to select an upper and/or lower bound, neither, or both?");
			System.out.println("The default is one (1) through ten (10).");
			Scanner keyboard = new Scanner(System.in);
			boundSize = keyboard.next();
			boundSize = boundSize.toLowerCase();
			if (boundSize.equals("upper") || boundSize.equals("lower") || boundSize.equals("neither")
					|| boundSize.equals("both")) {
				boundRepeat = false;
				break;
			}
			System.out.println("You have entered an invalid selection, (" + boundSize + ") please try again.");
		} while (boundRepeat == true);
		if (boundSize.equals("upper")) {
			ans[0] = false;
			ans[1] = true;
		} else if (boundSize.equals("lower")) {
			ans[0] = true;
			ans[1] = false;
		} else if (boundSize.equals("neither")) {
			ans[0] = false;
			ans[1] = false;
		} else if (boundSize.equals("both")) {
			ans[0] = true;
			ans[1] = true;
		}
		return ans;
	}

	static int inputBound(Boolean a, String bound) // Calculates bound values
	{
		int input = 0;
		if (a == true) // when user wants to select an input
		{
			String inputString = "";
			boolean numCheck = false;// Used to rerun the input until valid values are selected
			Scanner keyboard = new Scanner(System.in);
			do {
				try {
					System.out.println("Please enter a positive integer to be used as the " + bound + " bound");
					inputString = keyboard.next();
					input = Integer.parseInt(inputString.trim());
					numCheck = false;
					if (input < 0) {
						System.out.println("You entered a negative number.");
						numCheck = true;
					}
				} catch (Exception e) {
					System.out.println("A valid number was not entered.");
					System.out.println(e.getCause());
					System.out.println("You entered " + "'" + inputString + "'" + "\n");
					numCheck = true;
				}
			} while (numCheck == true);
		} else if (a == false && bound == "lower") {
			input = 1;
		} else if (a == false && bound == "upper") {
			input = 10;
		}
		return input;
	}

	static int randomnumber(int a, int b)// Generates random number
	{
		int randomized = 0;
		Random selectedGenerator = new Random();
		randomized = selectedGenerator.nextInt(b - a) + a;// Random number generator inclusive of all upper bounds but
		// not including zero
		return randomized;
	}

	public void guessNumber()// TEST FOR PLAYERS

	{
		System.out.println("-----");
		if (correct == true) {
			System.out.println("Player: " + name + ", you have already guessed correctly, you are being skipped.");
		} else {
			Boolean numCheck = false;
			String inputString = "";
			Scanner keyboard = new Scanner(System.in);
			if (lastguess == 0) {
				System.out.println("Welcome player: " + name);
			} else if (lastguess > rand) {
				System.out.println("Player " + name + " it is your turn!");
				System.out.println("Your last guess (" + lastguess + ") was too high!");
			} else if (lastguess < rand) {
				System.out.println("Player " + name + " it is your turn!");
				System.out.println("Your last guess (" + lastguess + ") was too low!");
			}
			do {
				try {
					System.out.println(
							"Please guess the number, it is between " + lower + " and " + upper + "(inclusive).");
					inputString = keyboard.next();
					lastguess = Integer.parseInt(inputString.trim());
					numCheck = false;
					if (lastguess < lower) {
						System.out.println("You entered an invalid number below the lower bound");
						totalguesscount = totalguesscount + 1;
						numCheck = true;
					}
					if (lastguess > upper) {
						System.out.println("You entered an invalid number above the upper bound");
						totalguesscount = totalguesscount + 1;
						numCheck = true;
					}
				} catch (Exception e) {
					System.out.println("A valid number was not entered.");
					System.out.println(e.getCause());
					System.out.println("You entered " + "'" + inputString + "'" + "\n");
					totalguesscount = totalguesscount + 1;
					numCheck = true;
				}
			} while (numCheck == true);
			guesslist.add(lastguess);
			if (lastguess > rand)// High guess
			{
				System.out.println("You have guessed too high!");
				validguesscount = validguesscount + 1;
				totalguesscount = totalguesscount + 1;
			} else if (lastguess < rand)// Low guess
			{
				System.out.println("You have guessed too low!");
				validguesscount = validguesscount + 1;
				totalguesscount = totalguesscount + 1;
			} else// True guess
			{
				validguesscount = validguesscount + 1;
				totalguesscount = totalguesscount + 1;
				System.out.println("You have guessed correct, congrats!!");
				correct = true;
			}
			return;
		}
	}
	public String getBoard(int x) {
		String number = board;
		return number;
	}

	public int getPlacedNumber() {
		return placedNumber;
	}
	public void setPlacedNumber(int x) {
		placedNumber = x;

	}
	public String getcolornum ()
	{
		String colornum = (guesslistRouletteNumber + " " + guesslistRouletteColor);
		return colornum;
	}
	public String getguesslistRouletteNumber()// returns guesslist
	{
		return guesslistRouletteNumber;
	}

	public String getguesslistRouletteColor()// returns guesslist
	{
		return guesslistRouletteColor;
	}
	public boolean getcolortrue ()
	{
		return colortrue;
	}
	public boolean getnumtrue()
	{
		return numtrue;
	}

}