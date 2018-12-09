package teamgames;
import java.util.*;
import java.lang.Boolean;
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
	private ArrayList <Integer> guesslist;
	private int wincount;
	private int id;
	private boolean playing;
	private boolean auth;
	private int games;
	public Player (String playerName, ArrayList <Player> current)//Constructor for new players
	{
		id = setID(current);
		name = playerName;
		password = setPwd(id, name);
		wincount = 0;
		playing = true;
		auth = true;
		games = 1;
	}
	public Player (int Id, String name, String pwd, int gamesplayed, int wincounter)//Constructor for existing players
	{
		this.id = Id;
		this.name = name;
		this.password = pwd;
		this.games = gamesplayed;
		this.wincount = wincounter;
		playing = false;
	}
	public String getName ()//returns player name
	{
		return name;
	}
	public int getRand ()//returns random number
	{
		return rand;
	}
	public int getUpper ()//returns upper bound
	{
		return upper;
	}
	public int getLower ()//returns lower bound
	{
		return lower;
	}
	public int getTotGuess ()//returns total guess count
	{
		return totalguesscount;
	}
	public int getValGuess ()//returns valid guess count
	{
		return validguesscount;
	}
	public ArrayList<Integer> getGuessList ()//returns guesslist
	{
		return guesslist;
	}
	public Boolean getCorrect()//returns value of correct
	{
		return correct;
	}
	public int getWinCT()
	{
		return wincount;
	}
	public String getPwd ()//returns player name
	{
		return password;
	}
	public Integer getID ()
	{
		return id;
	}
	public Boolean getPlaying ()
	{
		return playing;
	}
	public void increaseWincount()
	{
		wincount = wincount+1;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public boolean getAuth ()
	{
		return auth;
	}
	public void setAuthTrue ()
	{
		auth = true;
	}
	public void setAuthFalse ()
	{
		auth = false;
	}
	public void setPlayingFalse ()
	{
		playing = false;
	}
	public int getGames ()
	{
		return games;
	}
	public void playedGame ()
	{
		games = games + 1;
	}
	public int setID (ArrayList <Player> current)
	{
		int playerID = 0;
		Collections.sort(
				current,
				(player1, player2) -> player2.getID()
				- player1.getID());
		int highestID = current.size();
		playerID = highestID + 1;
		return playerID;
	}
	private String setPwd (int pId, String name)
	{
		String pwd = "";
		Boolean pwdRepeat = true;
		do	{
			System.out.println("Player ID: " + id + " Name: "+ name + " please enter your password for use in future games.");
			Scanner keyboard = new Scanner(System.in);
			pwd = keyboard.next();
			pwd = pwd.trim();
			if (pwd.matches("[^a-zA-Z_]"))
			{
				System.out.println("You have entered an invalid selection, (" + pwd + "), this contains other characters than letters, please try again.");
				pwdRepeat = true;
			}
			else
			{
				pwdRepeat = false;
			}
		} while (pwdRepeat == true);
		return pwd;
	}
	public void setPlayingTrue ()
	{
		playing = true;

	}
	public void setupRandGame ()//Used to generate variables needed for random number game
	{
		System.out.println("Player ID: " + id + " Name: " + name);
		bounds = boundCheck();
		Boolean boundRepeat = true;
		do {
			lower = inputBound(bounds[0], "lower");//Generates lower bound or standard value
			upper = inputBound(bounds[1], "upper");//Generates upper bound or standard value
			if (lower > upper)
			{
				boundRepeat = true;
				System.out.println("Your lower bound is greater than your upper bound, please try again");
			}
			else if (lower + 1 >= upper || upper - 1 <= lower)
			{
				boundRepeat = true;
				System.out.println("There is no valid number between the bounds, please try again");
			}
			else
			{
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
	static Boolean[] boundCheck () //Calculates if bounds are selectable
	{
		Boolean boundRepeat = true;
		String boundSize = "";
		Boolean[] ans = new Boolean[2];
		do	{
			System.out.println("Would you like to select an upper and/or lower bound, neither, or both?");
			System.out.println("The default is one (1) through ten (10).");
			Scanner keyboard = new Scanner(System.in);
			boundSize = keyboard.next();
			boundSize = boundSize.toLowerCase();
			if (boundSize.equals("upper")||boundSize.equals("lower")||boundSize.equals("neither")||boundSize.equals("both"))
			{
				boundRepeat = false;
				break;
			}
			System.out.println("You have entered an invalid selection, (" + boundSize + ") please try again.");
		} while (boundRepeat == true);
		if (boundSize.equals("upper"))
		{
			ans[0] = false;
			ans[1] = true;
		}
		else if (boundSize.equals("lower"))
		{
			ans[0] = true;
			ans[1] = false;
		}
		else if (boundSize.equals("neither"))
		{
			ans[0] = false;
			ans[1] = false;
		}
		else if (boundSize.equals("both"))
		{
			ans[0] = true;
			ans[1] = true;
		}
		return ans;
	}
	static int inputBound(Boolean a,String bound) //Calculates bound values
	{
		int input = 0;
		if (a == true) //when user wants to select an input
		{
			String inputString = "";
			boolean numCheck = false;//Used to rerun the input until valid values are selected
			Scanner keyboard = new Scanner(System.in);
			do	{
				try {
					System.out.println("Please enter a positive integer to be used as the " + bound + " bound");
					inputString = keyboard.next();
					input = Integer.parseInt(inputString.trim());
					numCheck = false;
					if (input < 0)	{
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
		}
		else if (a==false && bound=="lower")
		{
			input = 1;
		}
		else if (a==false && bound=="upper")
		{
			input = 10;
		}
		return input;
	}
	static int randomnumber (int a, int b)//Generates random number
	{
		int randomized = 0;
		Random selectedGenerator = new Random();
		randomized = selectedGenerator.nextInt(b-a) + a;//Random number generator inclusive of all upper bounds but not including zero
		return randomized;
	}
	public void guessNumber()//TEST FOR PLAYERS
	{
		System.out.println("-----");
		if (correct == true)
		{
			System.out.println("Player: " + name + ", you have already guessed correctly, you are being skipped.");
		}
		else
		{
			Boolean numCheck = false;
			String inputString = "";
			Scanner keyboard = new Scanner(System.in);
			if (lastguess == 0)
			{
				System.out.println("Welcome player: " + name);
			}
			else if (lastguess > rand)
			{
				System.out.println("Player " + name + " it is your turn!");
				System.out.println("Your last guess (" + lastguess + ") was too high!");
			}
			else if (lastguess < rand)
			{
				System.out.println("Player " + name + " it is your turn!");
				System.out.println("Your last guess (" + lastguess + ") was too low!");
			}
			do
			{
				try {
					System.out.println("Please guess the number, it is between " + lower + " and " + upper + "(inclusive).");
					inputString = keyboard.next();
					lastguess = Integer.parseInt(inputString.trim());
					numCheck = false;
					if (lastguess < lower)	{
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
			}while (numCheck == true);
			guesslist.add(lastguess);
			if (lastguess > rand)//High guess
			{
				System.out.println("You have guessed too high!");
				validguesscount =  validguesscount + 1;
				totalguesscount = totalguesscount + 1;
			}
			else if (lastguess < rand)//Low guess
			{
				System.out.println("You have guessed too low!");
				validguesscount =  validguesscount + 1;
				totalguesscount = totalguesscount + 1;
			}
			else//True guess
			{
				validguesscount =  validguesscount + 1;
				totalguesscount = totalguesscount + 1;
				System.out.println("You have guessed correct, congrats!!");
				correct = true;
			}
			return;
		}
	}
}