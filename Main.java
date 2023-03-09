// Kenny Tran - KMT170003
// This program reads an input file named "keyfile.txt" which serves as a key for the various representations each specific letter/number combination
// represents in terms of baseball statistics such as hits, walks, outs, strikeouts, etc. The contents of the keyfile will be stored into a hash table
// that uses each letter/number combination as the key and a letter representing the statistics it's supposed to represent as the value for the key.
// The program will create another hash table containing a players' name as the key and the players individual statistics as the value within the hash table.
// The program will read in another input file specified from the user in order to determine the specific statistics for each player, this input file
// will be fully parsed through and the program uses the hash table containing the contents of the keyfile to determine which statistics for which
// player will be incremented. After the hash table containing the players name and players statistic is complete, the program will use the hash table
// in order to transfer the data into two different array lists, each containing either the home team or the away team. The program will display each
// team in alphabetical order. The program will then find the league leaders for various statistics using the same array lists that came from the 
// hash table and display those league leaders. Every output is sent to the output file called "leaders.txt"
import java.io.*;
import java.util.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map;

public class Main {
	public static void main(String[] args) throws IOException {
		// boolean array used to parse through the keyfile into a hash table
		boolean[] nextStat = new boolean[7];
		// boolean variable used to determine whether or not a new player's information is going to be input into the hash table or if 
		// it's an existing player in which case their information will be updated
		boolean playerExists = false;
		// various array lists that contain the data from player hash table, one that contains all away players, another that contains all home players
		// and another containing every player from the input file
		ArrayList<Player> homeTeam = new ArrayList<Player>();
		ArrayList<Player> awayTeam = new ArrayList<Player>();
		ArrayList<Player> allPlayers = new ArrayList<Player>();
		// hash table/map containing the information from the keyfile input file, used to determine which stat belongs to which when reading the other
		// input file
		HashMap<String, String> keyTable = new HashMap<>();
		// hash table/map containing the information of the players' names and their respective individual statistics
		HashMap<String, Player> playerTable = new HashMap<>();
		// various string variables used for various things
		String fileName, temp, playerName, statisticVal = "";
		// reads in the keyfile input file
		File keyFile = new File("keyfile.txt");
		Scanner keyInFile = new Scanner(keyFile);
		// while loop that parses through the entirety of the keyfile until everything has been read
		while (keyInFile.hasNext()) 
		{
			// stores each line within the keyfile file into a temp string
			temp = keyInFile.nextLine();
			// a long series of if/else if statements that is used to correctly parse through each letter/statistic combination into its correct
			// statistical category
			if (temp.equals("## OUTS ##")) {
				nextStat[0] = true;
			}
			// if the current line being read in the keyfile is this then that means the program has parsed through every letter/number combination
			// pertaining to the outs category
			else if (temp.equals("## STRIKEOUT ##")) { 
				nextStat[1] = true;
			}
			// if the program hasn't reached to that checkpoint yet, then continue to mark that letter/number combination within the outs category
			else if (nextStat[1] == false) {
				keyTable.put(temp, "O");
			}
			// if the current line being read in the keyfile is this then that means the program has parsed through every letter/number combination
			// pertaining to the outs category
			else if (temp.equals("## HITS ##")) {
				nextStat[2] = true;
			}
			// if the program hasn't reached to that checkpoint yet, then continue to mark that letter/number combination within the strikeouts category
			else if (nextStat[2] == false) {
				keyTable.put(temp, "K");
			}
			// if the current line being read in the keyfile is this then that means the program has parsed through every letter/number combination
			// pertaining to the outs category
			else if (temp.equals("## WALK ##")) {
				nextStat[3] = true;
			}
			// if the program hasn't reached to that checkpoint yet, then continue to mark that letter/number combination within the hits category
			else if (nextStat[3] == false) {
				keyTable.put(temp, "H");
			}
			// if the current line being read in the keyfile is this then that means the program has parsed through every letter/number combination
			// pertaining to the outs category
			else if (temp.equals("## SACRIFICE ##")) {
				nextStat[4] = true;
			}
			// if the program hasn't reached to that checkpoint yet, then continue to mark that letter/number combination within the walks category
			else if (nextStat[4] == false) {
				keyTable.put(temp, "W");
			}
			// if the current line being read in the keyfile is this then that means the program has parsed through every letter/number combination
			// pertaining to the outs category
			else if (temp.equals("## HIT BY PITCH ##")) {
				nextStat[5] = true;
			}
			// if the program hasn't reached to that checkpoint yet, then continue to mark that letter/number combination within the sacrifices category
			else if (nextStat[5] == false) {
				keyTable.put(temp, "S");
			}
			// if the current line being read in the keyfile is this then that means the program has parsed through every letter/number combination
			// pertaining to the outs category
			else if (temp.equals("## ERRORS ##")) {
				nextStat[6] = true;
			}
			// if the program hasn't reached to that checkpoint yet, then continue to mark that letter/number combination within the hit by pitch category
			else if (nextStat[6] == false) {
				keyTable.put(temp, "P");
			}
			// otherwise the letter/number combination is an error
			else {
				keyTable.put(temp, "E");
			}
		}
		// close keyinfile scanner
		keyInFile.close();
		// scanner used to read in the user's input file
		Scanner input = new Scanner(System.in);
		fileName = input.nextLine();
				
		// closes the scanner input
		input.close();
		
		// opens input file
		File inputFile = new File(fileName);
		Scanner inFile = new Scanner(inputFile);
		// opens output file, printwriter used to be able to output to the output file
		File outputFile = new File("leaders.txt");
		PrintWriter output = new PrintWriter(outputFile);
		// checks if the inputfile can be read or exists
		if (inputFile.canRead() && inputFile.exists()) 
		{
			// checks until end of file
			while (inFile.hasNext()) 
			{
				// creates new player object
				Player player = new Player();
				// first word/letter in the input file will be a single character either an A or a H representing away or home
				player.setTeam(inFile.next());
				// next word/letter will be the player's name, store into a string as well as in the player object itself
				playerName = inFile.next();
				player.setName(playerName);
				// temporary string to hold the letter/number combination
				temp = inFile.next();
				// checks if said key exists within the hashtable containing the contents of the keyfile, if so store whatever letter statistic category into
				// the string
				if (keyTable.containsKey(temp)) {
					statisticVal = keyTable.get(temp);
				}
				// switch statement that checks for every single statistical category
				switch (statisticVal) {
					// if the letter/number combo is an O, check whether or not it's an existing player, if so update that player's information within the hash table
					// if not create a new key/value within the hash table with the data
					case "O": if (playerTable.containsKey(playerName)) {
						playerTable.get(playerName).incrementOuts();
						playerExists = true;
					}
					else {
						player.incrementOuts();
					}
					break;
					// if the letter/number combo is an K, check whether or not it's an existing player, if so update that player's information within the hash table
					// if not create a new key/value within the hash table with the data
					case "K": if (playerTable.containsKey(playerName)) {
						playerTable.get(playerName).incrementstrikeOut();
						playerExists = true;
					}
					else {
						player.incrementstrikeOut();
					}
					break;
					// if the letter/number combo is an H, check whether or not it's an existing player, if so update that player's information within the hash table
					// if not create a new key/value within the hash table with the data
					case "H": if (playerTable.containsKey(playerName)) {
						playerTable.get(playerName).incrementHits();
						playerExists = true;
					}
					else {
						player.incrementHits();
					}
					break;
					// if the letter/number combo is an W, check whether or not it's an existing player, if so update that player's information within the hash table
					// if not create a new key/value within the hash table with the data
					case "W": if (playerTable.containsKey(playerName)) {
						playerTable.get(playerName).incrementWalks();
						playerExists = true;
					}
					else {
						player.incrementWalks();
					}
					break;
					// if the letter/number combo is an S, check whether or not it's an existing player, if so update that player's information within the hash table
					// if not create a new key/value within the hash table with the data
					case "S": if (playerTable.containsKey(playerName)) {
						playerTable.get(playerName).incrementSacrifice();
						playerExists = true;
					}
					else {
						player.incrementSacrifice();
					}
					break;
					// if the letter/number combo is an P, check whether or not it's an existing player, if so update that player's information within the hash table
					// if not create a new key/value within the hash table with the data
					case "P": if (playerTable.containsKey(playerName)) {
						playerTable.get(playerName).incrementHitByPitch();
						playerExists = true;
					}
					else {
						player.incrementHitByPitch();
					}
					break;
					// if the letter/number combo is an E, check whether or not it's an existing player, if so update that player's information within the hash table
					// if not create a new key/value within the hash table with the data
					case "E": if (playerTable.containsKey(playerName)) {
						playerTable.get(playerName).incrementErrors();
						playerExists = true;
					}
					else {
						player.incrementErrors();
					}
					break;
				}
				// if the current player being parsed through isn't existing within the hash table, put that player's name and data within the hash table
				if (!playerExists) {
					playerTable.put(playerName, player);
				}
				// reset boolean value to false for the player
				playerExists = false;
			}
		}
		// prints out error message if file doesn't exist/doesn't contain anything
		else 
		{
			System.out.println("The file \"" + fileName + "\" does not exist or was unable to be read.");
		}
		// for loop that loops through every single value within the hash table and starts to store its contents within 2 different player arraylist
		for (Player players : playerTable.values()) {
			// if the player has an H as their team variable, they are apart of home team
			if (players.getTeam().equals("H")) {
				homeTeam.add(players);
			}
			// if the player has an A as their team variable, they are apart of away team
			else if (players.getTeam().equals("A")) {
				awayTeam.add(players);
			}
			// adds every player object into another array list that will contain every player within the hash table
			allPlayers.add(players);
		}
		// sorts both home team and away team alphabetically
		Collections.sort(homeTeam, Comparator.comparing(Player::getName));
		Collections.sort(awayTeam, Comparator.comparing(Player::getName));
		// outputs away message indicating the information after it represents the away team
		output.println("AWAY");
		// prints every player on the away team and their individual statistics
		for (Player players : awayTeam) {
			output.println(players.toString());
		}
		// outputs home message indicating the information after it represents the home team
		output.println("\n" + "HOME");
		// prints every player on the home team and their individual statistics
		for (Player players : homeTeam) {
			output.println(players.toString());
		}
		// prints out the league leaders for 6 different statistic categories
		output.println("\nLEAGUE LEADERS" + "\n" + "BATTING AVERAGE");
		displayBatAvgLeaders(allPlayers, output);
		output.println("\n" + "ON-BASE PERCENTAGE");
		displayOnBaseLeaders(allPlayers, output);
		output.println("\n" + "HITS");
		displayHitsLeaders(allPlayers, output);
		output.println("\n" + "WALKS");
		displayWalksLeaders(allPlayers, output);
		output.println("\n" + "STRIKEOUTS");
		displayStrikeOutLeaders(allPlayers, output);
		output.println("\n" + "HIT BY PITCH");
		displayHBPLeaders(allPlayers, output);
		output.print("\n");
		// close both output and input file scanners
		output.close();
		inFile.close();
	}
	
	/* NOTE: The code and logic for each of the display league leader statistic categories is pretty much the same other than the difference
	   being that each array list of players is being sorted by the corresponding statistic categories, so basically only 9 or so words within
	   each function differ from each other, other than strike out leaders which is a little different because the league leaders for that
	   statistic category is the lowest instead of the highest value. I will type the comments for displayBatAvgLeaders and leave the other
	   functions without comments for this reason.
	*/
	
	// Definition of the function displayBatAvgLeaders
	// finds the league leaders of the batting averages, this takes into account all players regardless of teams, however, the order in which
	// there's a tie, away players will be listed in alphabetical order before home players are listed. The function also finds the leaders
	// for second place and third place but will only output those if certain conditions are met.
	public static void displayBatAvgLeaders(ArrayList<Player> allPlayers, PrintWriter output) {
		// various double variables, leaderStat holds the current highest number for that stat, numLeaders & numLeaders2 are used to determine
		// whether or not 2nd or 3rd place league leaders will be displayed
		double leaderStat = 0, numLeaders = 0, numLeaders2 = 0;
		// string that holds a number representing the league leader stat along with the names that obtained that stat
		String displayLeader = "";
		// player array list that hold the names of the league leaders
		ArrayList<Player> leaderNames = new ArrayList<Player>();
		// player array list that copies and holds the league leaders names
		ArrayList<Player> tempHold = new ArrayList<Player>();
		// sorts every player in the array list by batting average from least to greatest
		Collections.sort(allPlayers, Comparator.comparing(Player::calculateBatAvg));
		// determines what is the highest number for that statistic
		if (allPlayers != null && !allPlayers.isEmpty()) {
			leaderStat = allPlayers.get(allPlayers.size()-1).calculateBatAvg();
		}
		// for loop that loops through every single player within the array list to determine which players hold the highest number for that stat
		// the loop will store those players within a new array list, also keeps a counter for every player that holds said stat
		for (Player players : allPlayers) {
			if (players.calculateBatAvg() == leaderStat) {
				leaderNames.add(players);
				numLeaders++;
			}
		}
		// stores the highest number for that stat along with a tab space
		displayLeader += String.format("%.3f", leaderStat) + "\t";
		// sorts the league leaders by away team and alphabetically
		Collections.sort(leaderNames, Comparator.comparing(Player::getTeam).thenComparing(Player::getName));
		// stores the sorted league leaders array list within the string that will be used to display the league leaders
		for (Player players : leaderNames) {
			displayLeader += players.getName() + ", ";
		}
		// trims the displayLeader string to remove the excess space and comma from the ending of it
		displayLeader = displayLeader.substring(0, displayLeader.lastIndexOf(','));
		// displays league leaders
		output.println(displayLeader);
		// for loop that loops through every player that doesn't hold the highest number for that stat and stores those players to another array list
		// also creates a copy of that array list, this will be used to find the 3rd place league leaders
		for (Player players : allPlayers) {
			if (players.calculateBatAvg() != leaderStat) {
				leaderNames.add(players);
				tempHold.add(players);
			}
		}
		// determines what is the second highest number for that statistic
		if (leaderNames != null && !leaderNames.isEmpty()) {
			leaderStat = leaderNames.get(leaderNames.size()-1).calculateBatAvg();
		}
		// clear the names within that array list, and clears the previous string containing the 1st place league leaders
		leaderNames.clear();
		displayLeader = "";
		// for loop that finds all the players that has the 2nd highest number for that stat, keeps a counter of players who hold this number
		for (Player players : allPlayers) {
			if (players.calculateBatAvg() == leaderStat) {
				leaderNames.add(players);
				numLeaders2++;
			}
		}
		// stores the second highest number for that stat along with a tab space
		displayLeader += String.format("%.3f", leaderStat) + "\t";
		// sorts the league leaders by away team and alphabetically
		Collections.sort(leaderNames, Comparator.comparing(Player::getTeam).thenComparing(Player::getName));
		// stores the sorted league leaders array list within the string that will be used to display the second league leaders
		for (Player players : leaderNames) {
			displayLeader += players.getName() + ", ";
		}
		// trims the displayLeader string to remove the excess space and comma from the ending of it
		displayLeader = displayLeader.substring(0, displayLeader.lastIndexOf(','));
		// if there are 3 or more people tied for first place, don't output 2nd or 3rd place league leaders, if not output second place leaders
		if (numLeaders < 3) {
			output.println(displayLeader);
		}
		// for loop that loops through every player that doesn't hold the highest number or second highest number for that stat and stores those
		//players to another array list
		for (Player players: tempHold) {
			if (players.calculateBatAvg() != leaderStat) {
				leaderNames.add(players);
			}
		}
		// determines what is the third highest number for that statistic
		if (leaderNames != null && !leaderNames.isEmpty()) {
			leaderStat = leaderNames.get(leaderNames.size()-1).calculateBatAvg();
		}
		// clear the names from the array list along with clear the previous 2nd place league leaders
		leaderNames.clear();
		displayLeader = "";
		// for loop that finds all the players that has the 3rd highest number for that stat, keeps a counter of players who hold this number
		for (Player players : allPlayers) {
			if (players.calculateBatAvg() == leaderStat) {
				leaderNames.add(players);
			}
		}
		// stores the 3rd highest number for that stat within the string
		displayLeader += String.format("%.3f", leaderStat) + "\t";
		// sorts the league leaders by away team and alphabetically
		Collections.sort(leaderNames, Comparator.comparing(Player::getTeam).thenComparing(Player::getName));
		// stores the sorted league leaders array list within the string that will be used to display the third league leaders
		for (Player players : leaderNames) {
			displayLeader += players.getName() + ", ";
		}
		// trims the displayLeader string to remove the excess space and comma from the ending of it
		displayLeader = displayLeader.substring(0, displayLeader.lastIndexOf(','));
		// if there is no tie in both first place and second place league leaders, output the 3rd place league leaders
		if (numLeaders == 1 && numLeaders2 == 1) {
			output.println(displayLeader);
		}
	}
	
	
	// Definition of the function displayOnBaseLeaders
	// finds the league leaders of the on base percentage, this takes into account all players regardless of teams, however, the order in which
	// there's a tie, away players will be listed in alphabetical order before home players are listed. The function also finds the leaders
	// for second place and third place but will only output those if certain conditions are met.
	public static void displayOnBaseLeaders(ArrayList<Player> allPlayers, PrintWriter output) {
		double leaderStat = 0, numLeaders = 0, numLeaders2 = 0;
		String displayLeader = "";
		ArrayList<Player> leaderNames = new ArrayList<Player>();
		ArrayList<Player> tempHold = new ArrayList<Player>();
		
		Collections.sort(allPlayers, Comparator.comparing(Player::calculateOnBase));
		//System.out.println(allPlayers);
		
		if (allPlayers != null && !allPlayers.isEmpty()) {
			leaderStat = allPlayers.get(allPlayers.size()-1).calculateOnBase();
		}
		for (Player players : allPlayers) {
			if (players.calculateOnBase() == leaderStat) {
				leaderNames.add(players);
				numLeaders++;
			}
		}
		//String.format("%.3f", leaderStat);
		displayLeader += String.format("%.3f", leaderStat) + "\t";
		
		Collections.sort(leaderNames, Comparator.comparing(Player::getTeam).thenComparing(Player::getName));
		for (Player players : leaderNames) {
			displayLeader += players.getName() + ", ";
		}
		displayLeader = displayLeader.substring(0, displayLeader.lastIndexOf(','));
		output.println(displayLeader);
		
		for (Player players : allPlayers) {
			if (players.calculateOnBase() != leaderStat) {
				leaderNames.add(players);
				tempHold.add(players);
			}
		}
		if (leaderNames != null && !leaderNames.isEmpty()) {
			leaderStat = leaderNames.get(leaderNames.size()-1).calculateOnBase();
		}
		leaderNames.clear();
		displayLeader = "";
		
		for (Player players : allPlayers) {
			if (players.calculateOnBase() == leaderStat) {
				leaderNames.add(players);
				numLeaders2++;
			}
		}
		displayLeader += String.format("%.3f", leaderStat) + "\t";
		
		Collections.sort(leaderNames, Comparator.comparing(Player::getTeam).thenComparing(Player::getName));
		for (Player players : leaderNames) {
			displayLeader += players.getName() + ", ";
		}
		displayLeader = displayLeader.substring(0, displayLeader.lastIndexOf(','));
		if (numLeaders < 3) {
			output.println(displayLeader);
		}
		for (Player players: tempHold) {
			if (players.calculateOnBase() != leaderStat) {
				leaderNames.add(players);
			}
		}
		if (leaderNames != null && !leaderNames.isEmpty()) {
			leaderStat = leaderNames.get(leaderNames.size()-1).calculateOnBase();
		}
		leaderNames.clear();
		displayLeader = "";
		
		for (Player players : allPlayers) {
			if (players.calculateOnBase() == leaderStat) {
				leaderNames.add(players);
			}
		}
		displayLeader += String.format("%.3f", leaderStat) + "\t";
		
		Collections.sort(leaderNames, Comparator.comparing(Player::getTeam).thenComparing(Player::getName));
		for (Player players : leaderNames) {
			displayLeader += players.getName() + ", ";
		}
		displayLeader = displayLeader.substring(0, displayLeader.lastIndexOf(','));
		if (numLeaders == 1 && numLeaders2 == 1) {
			output.println(displayLeader);
		}
	}
	
	// Definition of the function displayHitsLeaders
	// finds the league leaders of hits, this takes into account all players regardless of teams, however, the order in which
	// there's a tie, away players will be listed in alphabetical order before home players are listed. The function also finds the leaders
	// for second place and third place but will only output those if certain conditions are met.
	public static void displayHitsLeaders(ArrayList<Player> allPlayers, PrintWriter output) {
		double leaderStat = 0, numLeaders = 0, numLeaders2 = 0;
		String displayLeader = "";
		ArrayList<Player> leaderNames = new ArrayList<Player>();
		ArrayList<Player> tempHold = new ArrayList<Player>();
		
		Collections.sort(allPlayers, Comparator.comparing(Player::getHits));
		//System.out.println(allPlayers);
		
		if (allPlayers != null && !allPlayers.isEmpty()) {
			leaderStat = allPlayers.get(allPlayers.size()-1).getHits();
		}
		for (Player players : allPlayers) {
			if (players.getHits() == leaderStat) {
				leaderNames.add(players);
				numLeaders++;
			}
		}
		//String.format("%.3f", leaderStat);
		displayLeader += String.format("%.0f", leaderStat) + "\t";
		
		Collections.sort(leaderNames, Comparator.comparing(Player::getTeam).thenComparing(Player::getName));
		for (Player players : leaderNames) {
			displayLeader += players.getName() + ", ";
		}
		displayLeader = displayLeader.substring(0, displayLeader.lastIndexOf(','));
		output.println(displayLeader);
		
		for (Player players : allPlayers) {
			if (players.getHits() != leaderStat) {
				leaderNames.add(players);
				tempHold.add(players);
			}
		}
		if (leaderNames != null && !leaderNames.isEmpty()) {
			leaderStat = leaderNames.get(leaderNames.size()-1).getHits();
		}
		leaderNames.clear();
		displayLeader = "";
		
		for (Player players : allPlayers) {
			if (players.getHits() == leaderStat) {
				leaderNames.add(players);
				numLeaders2++;
			}
		}
		displayLeader += String.format("%.0f", leaderStat) + "\t";
		
		Collections.sort(leaderNames, Comparator.comparing(Player::getTeam).thenComparing(Player::getName));
		for (Player players : leaderNames) {
			displayLeader += players.getName() + ", ";
		}
		displayLeader = displayLeader.substring(0, displayLeader.lastIndexOf(','));
		if (numLeaders < 3) {
			output.println(displayLeader);
		}
		for (Player players: tempHold) {
			if (players.getHits() != leaderStat) {
				leaderNames.add(players);
			}
		}
		if (leaderNames != null && !leaderNames.isEmpty()) {
			leaderStat = leaderNames.get(leaderNames.size()-1).getHits();
		}
		leaderNames.clear();
		displayLeader = "";
		
		for (Player players : allPlayers) {
			if (players.getHits() == leaderStat) {
				leaderNames.add(players);
			}
		}
		displayLeader += String.format("%.0f", leaderStat) + "\t";
		
		Collections.sort(leaderNames, Comparator.comparing(Player::getTeam).thenComparing(Player::getName));
		for (Player players : leaderNames) {
			displayLeader += players.getName() + ", ";
		}
		displayLeader = displayLeader.substring(0, displayLeader.lastIndexOf(','));
		if (numLeaders == 1 && numLeaders2 == 1) {
			output.println(displayLeader);
		}
	}
	
	// Definition of the function displayWalksLeaders
	// finds the league leaders of walks, this takes into account all players regardless of teams, however, the order in which
	// there's a tie, away players will be listed in alphabetical order before home players are listed. The function also finds the leaders
	// for second place and third place but will only output those if certain conditions are met.
	
	public static void displayWalksLeaders(ArrayList<Player> allPlayers, PrintWriter output) {
		double leaderStat = 0, numLeaders = 0, numLeaders2 = 0;
		String displayLeader = "";
		ArrayList<Player> leaderNames = new ArrayList<Player>();
		ArrayList<Player> tempHold = new ArrayList<Player>();
		
		Collections.sort(allPlayers, Comparator.comparing(Player::getWalks));
		//System.out.println(allPlayers);
		
		if (allPlayers != null && !allPlayers.isEmpty()) {
			leaderStat = allPlayers.get(allPlayers.size()-1).getWalks();
		}
		for (Player players : allPlayers) {
			if (players.getWalks() == leaderStat) {
				leaderNames.add(players);
				numLeaders++;
			}
		}
		//String.format("%.3f", leaderStat);
		displayLeader += String.format("%.0f", leaderStat) + "\t";
		
		Collections.sort(leaderNames, Comparator.comparing(Player::getTeam).thenComparing(Player::getName));
		for (Player players : leaderNames) {
			displayLeader += players.getName() + ", ";
		}
		displayLeader = displayLeader.substring(0, displayLeader.lastIndexOf(','));
		output.println(displayLeader);
		
		for (Player players : allPlayers) {
			if (players.getWalks() != leaderStat) {
				leaderNames.add(players);
				tempHold.add(players);
			}
		}
		if (leaderNames != null && !leaderNames.isEmpty()) {
			leaderStat = leaderNames.get(leaderNames.size()-1).getWalks();
		}
		leaderNames.clear();
		displayLeader = "";
		
		for (Player players : allPlayers) {
			if (players.getWalks() == leaderStat) {
				leaderNames.add(players);
				numLeaders2++;
			}
		}
		displayLeader += String.format("%.0f", leaderStat) + "\t";
		
		Collections.sort(leaderNames, Comparator.comparing(Player::getTeam).thenComparing(Player::getName));
		for (Player players : leaderNames) {
			displayLeader += players.getName() + ", ";
		}
		displayLeader = displayLeader.substring(0, displayLeader.lastIndexOf(','));
		if (numLeaders < 3) {
			output.println(displayLeader);
		}
		for (Player players: tempHold) {
			if (players.getWalks() != leaderStat) {
				leaderNames.add(players);
			}
		}
		if (leaderNames != null && !leaderNames.isEmpty()) {
			leaderStat = leaderNames.get(leaderNames.size()-1).getWalks();
		}
		leaderNames.clear();
		displayLeader = "";
		
		for (Player players : allPlayers) {
			if (players.getWalks() == leaderStat) {
				leaderNames.add(players);
			}
		}
		displayLeader += String.format("%.0f", leaderStat) + "\t";
		
		Collections.sort(leaderNames, Comparator.comparing(Player::getTeam).thenComparing(Player::getName));
		for (Player players : leaderNames) {
			displayLeader += players.getName() + ", ";
		}
		displayLeader = displayLeader.substring(0, displayLeader.lastIndexOf(','));
		if (numLeaders == 1 && numLeaders2 == 1) {
			output.println(displayLeader);
		}
	}
	
	// Definition of the function displayStrikeOutLeaders
	// finds the league leaders of strike outs, this takes into account all players regardless of teams, however, the order in which
	// there's a tie, away players will be listed in alphabetical order before home players are listed. The function also finds the leaders
	// for second place and third place but will only output those if certain conditions are met.
	public static void displayStrikeOutLeaders(ArrayList<Player> allPlayers, PrintWriter output) {
		double leaderStat = 0, numLeaders = 0, numLeaders2 = 0;
		String displayLeader = "";
		ArrayList<Player> leaderNames = new ArrayList<Player>();
		ArrayList<Player> tempHold = new ArrayList<Player>();
		
		Collections.sort(allPlayers, Comparator.comparing(Player::getStrikeOuts));
		// takes the index of the player at the start of the sorted array list, as the league leader for strike outs represents the lowest number
		// instead of the highest
		if (allPlayers != null && !allPlayers.isEmpty()) {
			leaderStat = allPlayers.get(0).getStrikeOuts();
		}
		for (Player players : allPlayers) {
			if (players.getStrikeOuts() == leaderStat) {
				leaderNames.add(players);
				numLeaders++;
			}
		}
		displayLeader += String.format("%.0f", leaderStat) + "\t";
		
		Collections.sort(leaderNames, Comparator.comparing(Player::getTeam).thenComparing(Player::getName));
		for (Player players : leaderNames) {
			displayLeader += players.getName() + ", ";
		}
		displayLeader = displayLeader.substring(0, displayLeader.lastIndexOf(','));
		output.println(displayLeader);
		
		for (Player players : allPlayers) {
			if (players.getStrikeOuts() != leaderStat) {
				leaderNames.add(players);
				tempHold.add(players);
			}
		}
		// takes the index of the player at the start of the sorted array list, as the league leader for strike outs represents the lowest number
		// instead of the highest
		if (leaderNames != null && !leaderNames.isEmpty()) {
			leaderStat = leaderNames.get(0).getStrikeOuts();
		}
		leaderNames.clear();
		displayLeader = "";
		
		for (Player players : allPlayers) {
			if (players.getStrikeOuts() == leaderStat) {
				leaderNames.add(players);
				numLeaders2++;
			}
		}
		displayLeader += String.format("%.0f", leaderStat) + "\t";
		
		Collections.sort(leaderNames, Comparator.comparing(Player::getTeam).thenComparing(Player::getName));
		for (Player players : leaderNames) {
			displayLeader += players.getName() + ", ";
		}
		displayLeader = displayLeader.substring(0, displayLeader.lastIndexOf(','));
		if (numLeaders < 3) {
			output.println(displayLeader);
		}
		for (Player players: tempHold) {
			if (players.getStrikeOuts() != leaderStat) {
				leaderNames.add(players);
			}
		}
		// takes the index of the player at the start of the sorted array list, as the league leader for strike outs represents the lowest number
		// instead of the highest
		if (leaderNames != null && !leaderNames.isEmpty()) {
			leaderStat = leaderNames.get(0).getStrikeOuts();
		}
		leaderNames.clear();
		displayLeader = "";
		
		for (Player players : allPlayers) {
			if (players.getStrikeOuts() == leaderStat) {
				leaderNames.add(players);
			}
		}
		displayLeader += String.format("%.0f", leaderStat) + "\t";
		
		Collections.sort(leaderNames, Comparator.comparing(Player::getTeam).thenComparing(Player::getName));
		for (Player players : leaderNames) {
			displayLeader += players.getName() + ", ";
		}
		displayLeader = displayLeader.substring(0, displayLeader.lastIndexOf(','));
		if (numLeaders == 1 && numLeaders2 == 1) {
			output.println(displayLeader);
		}
	}
	
	// Definition of the function displayHBPLeaders
	// finds the league leaders of hits, this takes into account all players regardless of teams, however, the order in which
	// there's a tie, away players will be listed in alphabetical order before home players are listed. The function also finds the leaders
	// for second place and third place but will only output those if certain conditions are met.
	public static void displayHBPLeaders(ArrayList<Player> allPlayers, PrintWriter output) {
		double leaderStat = 0, numLeaders = 0, numLeaders2 = 0;
		String displayLeader = "";
		ArrayList<Player> leaderNames = new ArrayList<Player>();
		ArrayList<Player> tempHold = new ArrayList<Player>();
		
		Collections.sort(allPlayers, Comparator.comparing(Player::getHitByPitch));
		//System.out.println(allPlayers);
		
		if (allPlayers != null && !allPlayers.isEmpty()) {
			leaderStat = allPlayers.get(allPlayers.size()-1).getHitByPitch();
		}
		for (Player players : allPlayers) {
			if (players.getHitByPitch() == leaderStat) {
				leaderNames.add(players);
				numLeaders++;
			}
		}
		//String.format("%.3f", leaderStat);
		displayLeader += String.format("%.0f", leaderStat) + "\t";
		
		Collections.sort(leaderNames, Comparator.comparing(Player::getTeam).thenComparing(Player::getName));
		for (Player players : leaderNames) {
			displayLeader += players.getName() + ", ";
		}
		displayLeader = displayLeader.substring(0, displayLeader.lastIndexOf(','));
		output.println(displayLeader);
		
		for (Player players : allPlayers) {
			if (players.getHitByPitch() != leaderStat) {
				leaderNames.add(players);
				tempHold.add(players);
			}
		}
		if (leaderNames != null && !leaderNames.isEmpty()) {
			leaderStat = leaderNames.get(leaderNames.size()-1).getHitByPitch();
		}
		leaderNames.clear();
		displayLeader = "";
		
		for (Player players : allPlayers) {
			if (players.getHitByPitch() == leaderStat) {
				leaderNames.add(players);
				numLeaders2++;
			}
		}
		displayLeader += String.format("%.0f", leaderStat) + "\t";
		
		Collections.sort(leaderNames, Comparator.comparing(Player::getTeam).thenComparing(Player::getName));
		for (Player players : leaderNames) {
			displayLeader += players.getName() + ", ";
		}
		displayLeader = displayLeader.substring(0, displayLeader.lastIndexOf(','));
		if (numLeaders < 3) {
			output.println(displayLeader);
		}
		for (Player players: tempHold) {
			if (players.getHitByPitch() != leaderStat) {
				leaderNames.add(players);
			}
		}
		if (leaderNames != null && !leaderNames.isEmpty()) {
			leaderStat = leaderNames.get(leaderNames.size()-1).getHitByPitch();
		}
		leaderNames.clear();
		displayLeader = "";
		
		for (Player players : allPlayers) {
			if (players.getHitByPitch() == leaderStat) {
				leaderNames.add(players);
			}
		}
		displayLeader += String.format("%.0f", leaderStat) + "\t";
		
		Collections.sort(leaderNames, Comparator.comparing(Player::getTeam).thenComparing(Player::getName));
		for (Player players : leaderNames) {
			displayLeader += players.getName() + ", ";
		}
		displayLeader = displayLeader.substring(0, displayLeader.lastIndexOf(','));
		if (numLeaders == 1 && numLeaders2 == 1) {
			output.println(displayLeader);
		}
	}
}


