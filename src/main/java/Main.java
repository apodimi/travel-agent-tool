import javax.xml.transform.Result;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.HashMap;
import java.sql.*;

public class Main {

	public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {


		HashMap < String, City > map = new HashMap < String, City > ();

		//Driver for the JDBC
		Class.forName("oracle.jdbc.driver.OracleDriver");

		//Create the connection with the database
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@oracle12c.hua.gr:1521:orcl", "IT219138", "IT219138");

		//Create the statement object
		Statement stmt = con.createStatement();

		try {

			ResultSet results = stmt.executeQuery("SELECT * FROM CITY");

			// For each row of
			while (results.next()) {

				City city = new City();

				city.setName(results.getString(1));
				city.setTerms_vector(results.getInt(2),
						results.getInt(3),
						results.getInt(4),
						results.getInt(5),
						results.getInt(6),
						results.getInt(7),
						results.getInt(8),
						results.getInt(9),
						results.getInt(10),
						results.getInt(11));

				city.setGeodesic_vector(results.getDouble(12), results.getDouble(13));

				map.put(city.getName(), city);

			}

			City athens = new City();
			athens.findTheTermsForTheCity("Athens", "GR", athens);
			map.put(athens.getName(), athens);

			City rome = new City();
			rome.findTheTermsForTheCity("Rome", "IT", rome);
			map.put(rome.getName(), rome);

			boolean continueLoop = false;
		} catch (Exception e) {
			System.out.println("There is something wrong:" + e + "");
		}

		try {

			//Create the query to database
			String sql = "insert into CITY values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement db = con.prepareStatement(sql);

			// iterate to all the keys stored on our hashmap and add them to the database

			for (String s: map.keySet()) {
				int arrWithTerms[] = map.get(s).getTerms_vector();
				double arrWithGeo[] = map.get(s).getGeodesic_vector();
				db.setString(1, map.get(s).getName());
				db.setInt(2, arrWithTerms[0]);
				db.setInt(3, arrWithTerms[1]);
				db.setInt(4, arrWithTerms[2]);
				db.setInt(5, arrWithTerms[3]);
				db.setInt(6, arrWithTerms[4]);
				db.setInt(7, arrWithTerms[5]);
				db.setInt(8, arrWithTerms[6]);
				db.setInt(9, arrWithTerms[7]);
				db.setInt(10, arrWithTerms[8]);
				db.setInt(11, arrWithTerms[9]);
				db.setDouble(12, arrWithGeo[0]);
				db.setDouble(13, arrWithGeo[1]);
				db.executeUpdate();

				//OR THIS ONE
        		/*db.setString(1, map.get(s).getName());
        		for (int i =0; i< arrWithTerms.length; i++){
        			db.setInt((i+2), arrWithTerms[i]);
        		}
        		for (int i = 0; i <arrWithGeo.length; i++){
        			db.setDouble((i+12), arrWithGeo[0]);
        		}*/
			}

		} catch (Exception e) {
			System.out.println("There is something wrong: " + e + "");
		}

	}

}
