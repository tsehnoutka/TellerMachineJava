package mypackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
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
	private Boolean won;

	public Horse(int n, String sName, int o, Boolean w) {
		number = n;
		name = sName;
		odds = o;
		won = w;
	}

	public void print() {
		String wTemp = won ? "won" : "lost";
		System.out.println(number + "," + name + "," + odds + "," + wTemp);
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

	public Boolean Won() {
		return won;
	}

	public void setWon(Boolean b) {
		won = b;
	}

}// end of Horse class

public class Configuration {
	public static ArrayList<Integer> denominations = new ArrayList<>();
	public static Map<Integer, Integer> InitialQty = new HashMap<>();
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
		currentWinner = w;
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
			Boolean won =false;
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				won =false;
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					int iHorseNumber = Integer.parseInt(eElement.getAttribute("Number"));
					String sHorseName = eElement.getElementsByTagName("Name").item(0).getTextContent();
					int iHorseOdds = Integer.parseInt(eElement.getElementsByTagName("Odds").item(0).getTextContent());
					String bHorseWon = eElement.getElementsByTagName("Won").item(0).getTextContent();
					if (bHorseWon.contentEquals("won")) {
						setCurrentWinner(iHorseNumber);
						won =true;
					}

					Horse tempHorse = new Horse(iHorseNumber, sHorseName, iHorseOdds, won);

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
			String inLine;
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			int x=0;
			while ((inLine = br.readLine()) != null) {
				try {
					String[] arrOfStr = inLine.split(",");
					Integer tempD =  Integer.parseInt(arrOfStr[0].substring(1)); // get the denomination and remove the '$'
					denominations.add(x++,tempD);
					InitialQty.put( tempD,Integer.parseInt(arrOfStr[1]));
				} catch( NumberFormatException e) {
					// do nothing - move onto next line
				}
		
			// Close the input stream
			}
			fstream.close();
		} catch ( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}// end readFile

	public Configuration() {
		// open and read files
		// populate the Configuration class
		currentWinner = 0;
		readXML();
		readFile();

	}
}
