package mypackage;

public class CashDrawer {
	private int Ones;
	private int Fives;
	private int Tens;
	private int Twentys;
	private int Hundreds;
	private int Total;

	public CashDrawer() {
		RestockDrawer();
	} // end constructor

	public void RestockDrawer() {
		Ones = Configuration.ONES_INIT;
		Fives = Configuration.FIVES_INIT;
		Tens = Configuration.TENS_INIT;
		Twentys = Configuration.TWENTYS_INIT;
		Hundreds = Configuration.HUNDREDS_INIT;
		Total = 1 * Ones + 5 * Fives + 10 * Tens + 20 * Twentys + 100 * Hundreds;
	} // end of re-stock drawer

	public void Display() {
		System.out.println("Invetory:");
		System.out.println("$1," + Ones);
		System.out.println("$5," + Fives);
		System.out.println("$10," + Tens);
		System.out.println("$20," + Twentys);
		System.out.println("$100," + Hundreds);

	}

	public int pay(String horseName,  int payout) {
		// remove the amount from the drawer using the minimum amount of bills
		// if there isn't enough in the till, return error
		int oneOut=0,fiveOut=0, tenOut=0, twentyOut=0,hundredOut=0;
		int cOnes = Ones;
		int cFives = Fives;
		int cTens = Tens;
		int cTwentys = Twentys;
		int cHundres = Hundreds;
		int tempTotal=payout;

		if (Total < payout) {
			System.out.println("Insuficient Funds : " + payout);
			return 0;
		}
		
		while ((tempTotal >= 100) && (cHundres > 0)) {
			hundredOut++;
			cHundres--;
			tempTotal -= 100;
		}
		while ((tempTotal >= 20) && (cTwentys > 0)) {
			twentyOut++;
			cTwentys--;
			tempTotal -= 20;
		}
		while ((tempTotal >= 10) && (cTens > 0)) {
			tenOut++;
			cTens--;
			tempTotal -= 10;
		}
		while ((tempTotal >= 5) && (cFives > 0)) {
			fiveOut++;
			cFives--;
			tempTotal -= 5;
		}
		while ((tempTotal >= 1) && (cOnes > 0)) {
			oneOut++;
			cOnes--;
			tempTotal -= 1;
		}


		if (0 == tempTotal) {
    	   //  We can pay it out
    	   Total -= payout;

    	   System.out.println("Payout : " + horseName + "," + payout);
    	   System.out.println("Dispensing");
    	   System.out.println("$1,"+oneOut);
    	   System.out.println("$5,"+fiveOut);
    	   System.out.println("$10,"+tenOut);
    	   System.out.println("$20," +twentyOut);
    	   System.out.println("$100,"+hundredOut); 
    	   
    	   Hundreds-=hundredOut;
    	   Twentys-=twentyOut;
    	   Tens-=tenOut;
    	   Fives-=fiveOut;
    	   Ones-=oneOut;
       }
       else {
    	   //  We CAN'T pay it out
    	   System.out.println("Insufficient Funds : " + payout);
       }
		return 1;
	}// end pay

}
