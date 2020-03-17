package mypackage;

import java.io.IOException;
import java.util.Scanner;

public class TellerMachine {

	private static Configuration config;
	private static CashDrawer myTill;
	private static Debug myDebug;
	static int currentWinner = 0;
	
	private static boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        double d = Double.parseDouble(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}	
	private static void handleInput() {
		String  command = "x";
		int amoutnwagered = 0;
		int winningHorse = 0;
		String commandLine[];
		Scanner c = new Scanner(System.in);
		Boolean done = false;

		// ask for input
		while (!done) {
			// Reading data using readLine
			commandLine = c.nextLine().split(" ");
			command = commandLine[0];
			// command = (char) System.in.read();
			if (isNumeric(command)) {
				Horse tempH=null;
				//  test to see if Horse exist
				try {
					tempH = Configuration.aHorses.get(Integer.parseInt(command));
				}
				catch (NumberFormatException nfe) {
					//  do nothing for invalid command
				}
				if ( tempH== null) {
					System.out.println("Invalid Horse Number : " + winningHorse);
				}
				else {
					//get the amount wagered
					if (commandLine[1].indexOf('.') != -1){
						System.out.println("Invalid Bet : " + commandLine[1]);
						continue;
					}
						
					amoutnwagered = Integer.parseInt(commandLine[1]);
					winningHorse  = Integer.parseInt(command);
					
					if (amoutnwagered != (int) amoutnwagered) {
						System.out.println("Invalid Bet : " + amoutnwagered);
						continue;
					}
					Horse temp = Configuration.aHorses.get(winningHorse);
					if ((winningHorse == 0) || (temp.Won().equals("lost")))
						System.out.println("No Payout : " + temp.getName());
					else {
						// payout
						int payout = amoutnwagered * temp.getOdds();
						myTill.pay(temp.getName(), payout);
						
					} // end if else
					myTill.Display();
					config.printHorses();
					winningHorse = 0;
				}  //  end else
			}   // end if Numeric
			else {
				switch (command.toUpperCase()) {
				case "R":
					// re-stock cash
					myTill.RestockDrawer();
					break;
				case "W":
					// Sets winning horse number
					winningHorse = Integer.parseInt(commandLine[1]);
					Horse tempW = Configuration.aHorses.get(currentWinner); //  get current winning horse 
					Horse tempH = Configuration.aHorses.get(winningHorse);  //  get New winning horse
					if ( tempH== null)
						System.out.println("Invalid Horse Number : " + winningHorse);
					else {
						tempW.setWon("lost");  // make old winning horse Not the winner anymore
						tempH.setWon("won");
						currentWinner = winningHorse;
						myTill.Display();
						config.printHorses();
					}
					break;
				case "Q":
					// Quit
					done = true;
					//System.out.println("Quit");
					break;
				case "\r":
				case "\n":
					// do nothing
					break;
				default:
					System.out.println("Invalid Command : " + command);
				} // end switch
			} //  end else
		} // end while read command

		c.close();
		
	}

	public static void main(String args[]) throws IOException {
		// initialize
		config = new Configuration();
		myTill = new CashDrawer();
		myDebug = new Debug();
		
		myDebug.print(1, "Welcome to the Horse Program");
		currentWinner = config.getCurrentWinner();
		
		
		myTill.Display();
		config.printHorses();
		handleInput();
		
		myDebug.print(1, "Thanks for playing");
	} // end of main

} // end of class
