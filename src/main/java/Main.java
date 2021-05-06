import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.HashMap;
import java.sql.*;

public class Main {

	public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {

		//reads all the travellers from the JSON file and stores them to the ArrayList
		ArrayList<Traveller> travellers = readJSON();

		HashMap < String, City > map = new HashMap < String, City > ();

		//Driver for the JDBC
		Class.forName("oracle.jdbc.driver.OracleDriver");

		//Create the connection with the database
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@oracle12c.hua.gr:1521:orcl", "IT219138", "IT219138");

		//Create the statement object
		Statement stmt = con.createStatement();

		try {

			//Take all the records from the database
			ResultSet results = stmt.executeQuery("SELECT * FROM CITY");

			// For each row of
			while (results.next()) {

				//Create object for each row and insert the data
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

				//Put yhe object in hashmap
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
				ResultSet results = stmt.executeQuery("SELECT * FROM CITY");
				boolean originalCity = true;
				// For each row of
				//Check for duplicate
				while (results.next()) {
					String city1 =results.getString(1);
					String city2 =map.get(s).getName();
					if (city1.equals(city2)){
						originalCity = false;
					}
				}
				if (originalCity == true){
					int arrWithTerms[] = map.get(s).getTerms_vector();
					double arrWithGeo[] = map.get(s).getGeodesic_vector();
					db.setString(1, map.get(s).getName());
					for (int i =0; i< arrWithTerms.length; i++){
						db.setInt((i+2), arrWithTerms[i]);
					}
					for (int i = 0; i <arrWithGeo.length; i++){
						db.setDouble((i+12), arrWithGeo[i]);
					}
					db.executeUpdate();
				}
			}

			//Close the connection to database
			con.close();

		} catch (Exception e) {
			System.out.println("There is something wrong: " + e + "");
			con.close();
		}

		//Print all the travellers sorted based on the timestamp and remove duplicates
		ArrayList<Traveller> sortedTravellers = sortTravellers(travellers);
		for (Traveller traveller : sortedTravellers) {
			System.out.println(traveller.getName()+" "+traveller.getTimestamp()+" "+traveller.getCity().getName());
		}
		City turin = new City();
		turin.findTheTermsForTheCity("Turin", "IT", turin);
		map.put("Turin", turin);



		//writes all the travellers in a JSON file
		writeJSON(travellers);
	}

	/**
	 * Sorts the travellers based on the timestamp and removing the duplicates
	 * @param travellers an arrayList containing all the travellers
	 * @return an arrayList with travellers sorted and without duplicates
	 */
	private static ArrayList<Traveller> sortTravellers(ArrayList<Traveller> travellers) {
		ArrayList<Traveller> tmp = removeDuplicates(travellers);
		Collections.sort(tmp);
		return tmp;
	}

	/**
	 * Removes all the duplicates in the arrayList
	 * @param travellers an arrayList containing all the travellers
	 * @return an arrayList without the duplicate travellers
	 */
	private static ArrayList<Traveller> removeDuplicates(ArrayList<Traveller> travellers) {

		ArrayList<Traveller> newList = new ArrayList<>(travellers);

		for (int i = 0; i < newList.size(); i++) {
			for (int j = i+1; j < newList.size(); j++) {
				if (newList.get(i).getName().equals(newList.get(j).getName())) {
					newList.remove(j);
				}
			}
		}

		return newList;
	}

	/**
	 * Stores all the travellers in a JSON file (Serialization)
	 * @param travellers an arrayList containing all the travellers
	 */
	private static void writeJSON(ArrayList<Traveller> travellers) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enableDefaultTyping();
		AllTravellers data = new AllTravellers();
		data.setAllTravellers(travellers);
		mapper.writeValue(new File("travellers.json"), data);
	}

	/**
	 * Reads all the travellers from a JSON file (Deserialization)
	 * @return an arrayList containing all the travellers retrieved from the JSON file
	 */
	private static ArrayList<Traveller> readJSON() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enableDefaultTyping();
		AllTravellers data = mapper.readValue(new File("travellers.json"), AllTravellers.class);
		return data.getAllTravellers();
	}
}