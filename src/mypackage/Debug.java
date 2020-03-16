package mypackage;

public class Debug {
	public static final int LOW = 0;
	int level = 1;  //  levels 0-2; 2 being High
	enum Severity{
		LOW, MEDIUM, HIGH;
    private static Severity[] allValues = values();
    public static Severity fromOrdinal(int n) {return allValues[n];}
	}
	
	public void print(int l, String s) {
		Severity sev = null;
		if (l <= level)
			System.out.println("DEBUG(" + sev.fromOrdinal(l) + ") : " + s);
	} //  end print 

}
