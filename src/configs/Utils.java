package configs;

public class Utils {

	public static int savedAttempts = 0;
	public static int lastCounter = 1;

	public static java.sql.Date getCurrentDate() {
		
		java.util.Date today = new java.util.Date();
		
		return new java.sql.Date(today.getTime());
	}
}
