import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class SizeFinder {

	private final static int BUST = 1;
	private final static int WAIST = 2;
	private final static int HIP = 3;
	
	private static Connection con = null;
	private static String url = "jdbc:mysql://198.199.67.8:3306/sizing";
	private static String user = "dev1";
	private static String password = "golly,abadpassword";

	private static void initialiseConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection(url, user, password);
		System.out.println("Got a connection!");
	}

	private static ResultSet runQuery(String query) throws SQLException {
		Statement s = con.createStatement();
		ResultSet results = s.executeQuery(query);
		return results;
	}

	/**
	 * Returns a list of Measurement objects given a result set generated
	 * from running a "SELECT *" statement on the measurements table
	 * @throws SQLException 
	 */
	private static ArrayList<Measurement> returnMeasurements(ResultSet r) throws SQLException {
		ArrayList<Measurement> measurements = new ArrayList<Measurement>();
		while (r.next()) {
			Measurement m = new Measurement(r.getInt(1), r.getInt(2), 
					r.getString(3), r.getInt(4), r.getInt(5), r.getInt(6), 
					r.getInt(7), r.getInt(8), r.getInt(9), r.getString(10), 
					r.getString(11), r.getInt(12));
			measurements.add(m);
		}
		return measurements;
	}

	/**
	 * Returns the closest match to the indicated metric type (i.e. one of bust,
	 * waist or hip)
	 * @throws SQLException 
	 */
	private static Measurement returnSize(int type, double measurement,
			int brandID, boolean overfit, double delta) throws SQLException {
		String measurementName;
		if (type == BUST) measurementName = "bust";
		else if (type == WAIST) measurementName = "waist";
		else measurementName = "hip";
		String mLow = measurementName + "Lower";
		String mHigh = measurementName + "Higher";
		
		double adjustedMeasurement = 0;
		adjustedMeasurement = 
				overfit ? Math.round(measurement + delta) : 
					Math.round(measurement - delta);
				
		String query = 
			"SELECT * FROM measurements " +
			"WHERE brandID = " + brandID + " " +
			"AND (" + mLow + " = " + adjustedMeasurement + " " +
			"OR (" + mLow + " < " + adjustedMeasurement + " " +
			"AND " + adjustedMeasurement + " <= " + mHigh  + "))";
				ArrayList<Measurement> mCandidate = returnMeasurements(
						runQuery(query));
				if (mCandidate.size() == 0) {
					return new Measurement();
				} else return mCandidate.get(0);
	}
	
	/**
	 * Returns the closest match to the bust, waist hip measurements given
	 * a brand and the user's size data. 'Overfit' is the preference indicating
	 * that the user would rather have over-sized clothes as opposed to tight.
	 * Starting from index 0, bust, waist then hip are stored.
	 * @throws SQLException
	 */
	static Measurement[] returnSizes (double bust, double waist,
			double hip, int brandID, boolean overfit) throws SQLException {
		boolean bustFound = false, waistFound = false, hipFound = false;
		double delta = 0;
		Measurement[] measurements = new Measurement[3];
		for (int i = 0; i < measurements.length; i++) {
			measurements[i] = new Measurement();
		}
		while ((!bustFound || !waistFound || !hipFound) && delta != 10) {
			if (!bustFound) {
				Measurement m = returnSize(BUST, bust, brandID, overfit, delta);
				if (m.getId() != -1) {
					measurements[0] = m;
					bustFound = true;
				}
			}
			if (!waistFound) {
				Measurement m = returnSize(WAIST, waist, brandID, overfit, delta);
				if (m.getId() != -1) {
					measurements[1] = m;
					waistFound = true;
				}
			}
			if (!hipFound) {
				Measurement m = returnSize(HIP, hip, brandID, overfit, delta);
				if (m.getId() != -1) {
					measurements[2] = m;
					hipFound = true;
				}
			}
			delta++;
		}
		return measurements;
	}
	
	static String returnBrandName(int brandID) throws SQLException {
		ResultSet name = runQuery("SELECT name FROM brands WHERE id = " + brandID);
		name.next();
		return name.getString(1);
	}
	
	static double returnFitness(int type, double measurement, Measurement given) throws SQLException {
		double l = 0, h = 0;
		
		String typeString = "";
		if (type == BUST) typeString = "bust";
		else if (type == WAIST) typeString = "waist";
		else typeString = "hip";
		String typeStringL = typeString + "Lower";
		String typeStringR = typeString + "Higher";
		
		String query = 
				"SELECT " + typeStringL + ", " + typeStringR +
				" FROM measurements WHERE brandID = " + given.getBrandID() + 
				" ORDER BY " + typeStringL;
		
		System.out.println(query);
		
		ResultSet range = runQuery(query);
		range.next();
		l = range.getDouble(1);
		while (range.next()) {
			double lower = range.getDouble(1);
			double higher = range.getDouble(2);
			if (lower > higher) h = lower;
			else h = higher;
		}
		
		System.out.println("Lower: " + l + "\tHigher: " + h);
		
		if (type == BUST) {
			if (measurement > given.getBustLower()) return 1;
			else return (1 - ((given.getBustLower() - measurement) / (h - l)));
		}
		
		else if (type == WAIST) {
			if (measurement > given.getWaistLower()) return 1;
			else return (1 - ((given.getWaistLower() - measurement) / (h - l)));
		}
		
		else {
			if (measurement > given.getHipLower()) return 1;
			else return (1 - ((given.getHipLower() - measurement) / (h - l)));
		}
	}
		
	public static void main (String[] args) throws ClassNotFoundException, SQLException {

		initialiseConnection();
		
//		Measurement given = returnSize(BUST, 91, 1, true, 0); 
//		System.out.println(given);
//		System.out.println(returnFitness(BUST, 87, given));
//		
//		System.out.println(returnBrandName(16));
//		
//		for (int i = 0; i < 20; i++) {
//			Measurement[] sizes = returnSizes(87.5, 76, 94, i+1, true);
//			for (Measurement m : sizes) if (m.getId() != -1) System.out.println(m);			
//		}
		
		Scanner input = new Scanner(System.in);
		System.out.print("Bust: ");
		double bust = input.nextDouble();
		System.out.print("Waist: ");
		double waist = input.nextDouble();
		System.out.print("Hip: ");
		double hip = input.nextDouble();
		input.close();
		
		// fix hardcoded brandID limit
		for (int brandID = 1; brandID <= 20; brandID++) {
			System.out.println(returnBrandName(brandID));
			Measurement[] sizes = new Measurement[3];
			sizes = returnSizes(bust, waist, hip, brandID, true);
			for (int i = 0; i < 3; i++) {
				if (i == 0) System.out.print("Bust: ");
				else if (i == 1) System.out.print("Waist: ");
				else System.out.print("Hip: ");
				if (sizes[i].getId() == -1) {
					System.out.print("no size found\n");
				}
				else System.out.print(sizes[i].getUKsize() + "\t" + sizes[i].getSizeLabel() + "\n");
			}
			System.out.println();
		}
	}
}
