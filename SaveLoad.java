package Runner;
import java.io.*;
import java.util.*;
import teamgames.Player;
public class SaveLoad {
	String dir = System.getProperty("user.dir") + File.separator + "save.csv";
	public void runSave (ArrayList <Player> saveFile)
	{
		ArrayList <String> saveData = new ArrayList <String>();
		String holder = "";
		for (int i = 0; i < saveFile.size(); i++)
		{
			holder = (saveFile.get(i).getID() + "," + saveFile.get(i).getName() + "," + saveFile.get(i).getPwd() + "," + saveFile.get(i).getGames() + "," + saveFile.get(i).getWinCT());
			saveData.add(holder);
		}
		try
		{
			//System.out.print(System.getProperty("user.dir") + File.separator + fileName);
			//File savedGame = new File ("/Users/najumjunaid/Desktop/TEST/AutoSaveGame.csv");//Local machine save
			File savedGame = new File (dir);//path of file OS independent
			if (!savedGame.exists())
			{
				savedGame.createNewFile();
			}
			FileWriter fw = new FileWriter(savedGame.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			/*bw.append("ID");
			bw.append(',');
			bw.append("Name");
			bw.append(',');
			bw.append("Pwd");
			bw.append(',');
			bw.append("WinCT");
			bw.newLine();*/ //Currently headers breaks the file saving, removed
			for (int i = 0; i < saveFile.size(); i++)
			{
				bw.write(saveData.get(i));
				bw.newLine();
			}
			bw.flush();
			bw.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	public ArrayList <Player> runLoad ()
	{
		ArrayList <Player> loadPlayers = new ArrayList<>();
		String fileName = "save.csv";
		//String csvFile = "/Users/najumjunaid/Desktop/TEST/AutoSaveGame.csv";//local machine load
		BufferedReader br = null;
		String line = "";
		String csvSplit = ",";
		try
		{
			br = new BufferedReader(new FileReader(dir));//Reads file based on loaded location
			line = br.readLine();
			while (line != null)
			{
				String[] playerData = line.split(csvSplit);//Splits lines based on comma for CSV
				Player loadplayerdata = generatePlayer(playerData);//generates player based on data
				loadPlayers.add(loadplayerdata);//adds new loadedplayer to the list
				line = br.readLine();//checks that next line is not null
			}
		}
		catch (FileNotFoundException e) {
			return loadPlayers;//no save file empty ArrayList
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		sortLoaded(loadPlayers);
		return loadPlayers;
	}
	private static Player generatePlayer(String [] loadedData)
	{
		int playerId = Integer.parseInt(loadedData[0]);
		String playerName = loadedData[1];
		String playerPwd = loadedData[2];
		int playerGames = Integer.parseInt(loadedData[3]);
		int winCounter = Integer.parseInt(loadedData[4]);
		return new Player (playerId, playerName, playerPwd, playerGames, winCounter);//creates and returns LoadedPlayer based on constructor
	}
	static ArrayList <Player> sortLoaded (ArrayList <Player> playerList) //takes and sorts playerList
	{
		Collections.sort(
				playerList,
				(player1, player2) -> player2.getID()
				- player1.getID());//runs sorting based on ID decending
		return playerList;
	}
}
