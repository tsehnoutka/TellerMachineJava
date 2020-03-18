package mypackage;

import java.io.IOException;
import java.util.Scanner;

public class TellerMachine {

	private static Configuration config;
	private static CashDrawer myTill;
	private static Debug myDebug;
	static int currentWinner = 0;
	private static Boolean done = false;

	private static boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			Double.parseDouble(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	private static void handleInputNumber(String[] commandLine) {
		String command = "x";
		int amoutnwagered = 0;
		int winningHorse = 0;

		command = commandLine[0];
		//  check if there are two parameters 
		try {
			@SuppressWarnings("unused")
			String strY= commandLine[1];
			@SuppressWarnings("unused")
			Integer intY = Integer.parseInt(commandLine[1]);
		}
		catch(NumberFormatException | ArrayIndexOutOfBoundsException e){
			System.out.println("Invalid Command : " + command);
			return;
		}

		winningHorse = Integer.parseInt(command);
		Horse tempH = null;
		// test to see if Horse exist
		try {
			tempH = Configuration.aHorses.get(winningHorse);
		} catch (NumberFormatException nfe) {
			System.out.println("Invalid Command : " + command);
		}
		if (tempH == null) {
			System.out.println("Invalid Horse Number : " + winningHorse);
		} else {
			// get the amount wagered
			if (commandLine[1].indexOf('.') != -1) {
				System.out.println("Invalid Bet : " + commandLine[1]);
				return;
			}

			amoutnwagered = Integer.parseInt(commandLine[1]);
			if (amoutnwagered != (int) amoutnwagered) {
				System.out.println("Invalid Bet : " + amoutnwagered);
				return;
			}
			if ((winningHorse == 0) || (winningHorse != currentWinner))
				System.out.println("No Payout : " + tempH.getName());
			else {
				// payout
				int payout = amoutnwagered * tempH.getOdds();
				myTill.pay(tempH.getName(), payout);

			} // end if else
			myTill.Display();
			config.printHorses();
			winningHorse = 0;
		} // end else
		
	}
	private static void handleInput(String[] commandLine) {
		String command = "x";
		command = commandLine[0];
		
		if (isNumeric(command)) {
			handleInputNumber(commandLine);
		} // end if Numeric
		else {
			switch (command.toUpperCase()) {
			case "R":
				// re-stock cash
				myTill.RestockDrawer();
				myTill.Display();
				config.printHorses();
				break;
			case "W":
				// Sets winning horse number
				int winningHorse = Integer.parseInt(commandLine[1]);
				Horse tempW = Configuration.aHorses.get(currentWinner); // get current winning horse
				Horse tempH = Configuration.aHorses.get(winningHorse); // get New winning horse
				if (tempH == null)
					System.out.println("Invalid Horse Number : " + winningHorse);
				else {
					tempW.setWon(false); // make old winning horse Not the winner anymore
					tempH.setWon(true);
					currentWinner = winningHorse;
					myTill.Display();
					config.printHorses();
				}
				break;
			case "Q":
				// Quit
				done = true;
				// System.out.println("Quit");
				break;
			case "\r":
			case "\n":
				// do nothing
				break;
			default:
				System.out.println("Invalid Command : " + command);
			} // end switch
		} // end else
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
		
		Scanner c = new Scanner(System.in);
		while (!done) {
			handleInput(c.nextLine().trim().split(" "));
		}
		c.close();
		myDebug.print(1, "Thanks for playing");
	} // end of main

} // end of class

/*
 * 
 * TEST CASES:
 * 1 55 - payout 275
 * 2 25  - no payout
 * w 4 - set winning horse to 4
 * w 4 - winning horse is still 4
 * 4 10.25 - no payout
 * r - resets till
 * w 2 - sets winning horse to 2
 * 2 100 - payout
 * 2 30 - payout
 * 2 10 - no payout not enough money
 * r - reset till
 * w 4 - set winning horse to 4
 * 4 1 - payout
 * 4 1 - payout
 * 4 1 no payout not enough denomination
 * 10.25 - Invalid command
 * 1 - Invalid command 
 * 10 5 - invalid horse
 * add a space before a command - it should work as expected
 * 
 */
