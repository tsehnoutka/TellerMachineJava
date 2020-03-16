package mypackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class Horse {
	private int number;
	private String name;
	private int odds;
	private String won;

	public Horse(int n, String sName, int o, String w) {
		number = n;
		name = sName;
		odds = o;
		if ( w .contentEquals("won") || w.contentEquals("lost"))
			won = w;
	}

	public void print() {
		System.out.println(number + "," + name + "," + odds + "," + won);
	} // end print

	public int getNumber() {
		return number;
	}

	public String getName() {
		return name;
	}

	public int getOdds() {
		return odds;
	}

	public String Won() {
		return won;
	}

	public void setWon(String b) {
		won = b;
	}

}// end of Horse class

public class Configuration {
	public static int ONES_INIT = 0;
	public static int FIVES_INIT = 0;
	public static int TENS_INIT = 0;
	public static int TWENTYS_INIT = 0;
	public static int HUNDREDS_INIT = 0;
	public static Hashtable<Integer, Horse> aHorses = new Hashtable<Integer, Horse>();
	private int currentWinner;

	public void printHorses() {
		int size = aHorses.size();
		System.out.println("Horses:");
		// starting at one cause the Horse numbers start at 1
		for (int i = 1; i <= size; i++) {
			Horse temp = aHorses.get(i);
			temp.print();
		} // end while

	}// end print horses

	public int getCurrentWinner() {
		return currentWinner;
	}
	public void setCurrentWinner(int w) {
		currentWinner=w;
	}
	
	private void readXML() {
		// Populate the horses ( XML file)
		try {
			File fXmlFile = new File("Horses.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("Horse");

			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					int iHorseNumber = Integer.parseInt(eElement.getAttribute("Number"));
					String sHorseName = eElement.getElementsByTagName("Name").item(0).getTextContent();
					int iHorseOdds = Integer.parseInt(eElement.getElementsByTagName("Odds").item(0).getTextContent());
					String bHorseWon = eElement.getElementsByTagName("Won").item(0).getTextContent();
					if (bHorseWon.contentEquals("won"))
							setCurrentWinner(iHorseNumber);
				
					Horse tempHorse = new Horse(iHorseNumber, sHorseName, iHorseOdds, bHorseWon);

					aHorses.put(iHorseNumber, tempHorse);
				} // end if
			} // end for
		} // end try
		catch (Exception e) {
			e.printStackTrace();
		} // end catch
	} // end read XML

	private void readFile() {
		// Read Input file
		FileInputStream fstream;
		try {
			fstream = new FileInputStream("Input.txt");

			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

			// Read File Line By Line
			String inLine = br.readLine(); // read inventory line
			String inLine1 = br.readLine(); // read One line
			String inLine5 = br.readLine(); // read One line
			String inLine10 = br.readLine(); // read One line
			String inLine20 = br.readLine(); // read One line
			String inLine100 = br.readLine(); // read One line

			ONES_INIT = Integer.parseInt(inLine1.substring(inLine1.lastIndexOf(",") + 1)); // read $1 line
			FIVES_INIT = Integer.parseInt(inLine5.substring(inLine5.lastIndexOf(",") + 1)); // read $5 line
			TENS_INIT = Integer.parseInt(inLine10.substring(inLine10.lastIndexOf(",") + 1)); // read $10 line
			TWENTYS_INIT = Integer.parseInt(inLine20.substring(inLine20.lastIndexOf(",") + 1)); // read $20 line
			HUNDREDS_INIT = Integer.parseInt(inLine100.substring(inLine100.lastIndexOf(",") + 1)); // read $100 line

			// Close the input stream
			fstream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}// end readFile

	public Configuration() {
		// open and read files
		// populate the Configuration class
		currentWinner=0;
		readXML();
		readFile();

	}
}
