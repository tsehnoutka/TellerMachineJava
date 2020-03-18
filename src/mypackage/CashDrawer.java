package mypackage;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class CashDrawer {
	private int Total;
	Map<Integer, Integer> drawer = new HashMap<Integer, Integer>();

	public CashDrawer() {
		RestockDrawer();
	} // end constructor

	public void RestockDrawer() {
		Total = 0;

		for (int x = 0; x < Configuration.denominations.size(); x++) {
			int denom = Configuration.denominations.get(x);
			drawer.put(denom, Configuration.InitialQty.get(denom));
			Total += denom * Configuration.InitialQty.get(denom);
		}
	} // end of re-stock drawer

	public void Display() {
		System.out.println("Invetory:");
		Collections.sort(Configuration.denominations);
		for (int x = 0; x < Configuration.denominations.size(); x++) {
			int denom = Configuration.denominations.get(x);
			System.out.println("$" + denom + "," + drawer.get(denom));
		}

	}

	public int pay(String horseName, int payout) {
		// remove the amount from the drawer using the minimum amount of bills
		// if there isn't enough in the till, return error
		int numOfDenominations = Configuration.denominations.size();
		Map<Integer, Integer> toPayOut = new HashMap<Integer, Integer>();
		int tempTotal = payout;

		if (Total < payout) {
			System.out.println("Insuficient Funds (Total): " + payout);
			return 0;
		}

		Iterator<Entry<Integer, Integer>> it = drawer.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, Integer> pair = (Map.Entry<Integer, Integer>) it.next();
			toPayOut.put((Integer) (pair.getKey()), (Integer) (pair.getValue()));
		}

		Collections.sort(Configuration.denominations, Collections.reverseOrder());
		for (int x = 0; x < numOfDenominations; x++) {
			int denom = Configuration.denominations.get(x);
			int DenominationLeft = drawer.get(denom);
			int cashOut = 0;
			while ((tempTotal >= denom) && (DenominationLeft > 0)) {
				cashOut++;
				DenominationLeft--;
				tempTotal -= denom;
			}
			toPayOut.put(denom, cashOut);
		}

		if (0 == tempTotal) {
			// We can pay it out
			Total -= payout;

			System.out.println("Payout : " + horseName + "," + payout);
			System.out.println("Dispensing");
			Collections.sort(Configuration.denominations);
			for (int x = 0; x < numOfDenominations; x++) {
				int denom = Configuration.denominations.get(x);
				System.out.println("$" + denom + "," + toPayOut.get(denom));
				drawer.put(denom, drawer.get(denom) - toPayOut.get(denom));
			}

		} else {
			// We CAN'T pay it out
			System.out.println("Insufficient Funds (Denomination): " + payout);
			return 0;
		}
		return 1;
	}// end pay

}
