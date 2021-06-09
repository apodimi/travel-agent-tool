import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.HashMap;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

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

			gui(travellers, map);


			boolean continueLoop = false;
		} catch (Exception e) {
			System.out.println("There is something wrong:" + e + "");
		}
	}

	/**
	 * Sorts the travellers based on the timestamp and removing the duplicates
	 * @param travellers an arrayList containing all the travellers
	 * @return an arrayList with travellers sorted and without duplicates
	 */
	public static ArrayList<Traveller> sortTravellers(ArrayList<Traveller> travellers) {
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

	private static void gui(ArrayList<Traveller> travellers, HashMap<String, City> cities){
		// Create and set up a frame window
		JFrame frame = new JFrame("New Submit");

		//Close the program when user click the exit button
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Panel with the inputs
		JPanel panel1 = new JPanel();
		panel1.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
		panel1.setLayout(new GridLayout(0, 2,30,30));
		JLabel cityName = new JLabel("City name");
		JTextField cityNameInput = new JTextField(20);
		JLabel countryCode = new JLabel("Country code");
		JTextField countryCodeInput = new JTextField(20);
		JLabel travellerName = new JLabel("Traveller name");
		JTextField travellerNameInput = new JTextField(20);
		JLabel travellerAge = new JLabel("Traveller age");
		JTextField travellerAgeInput = new JTextField(20);
		JLabel travellerCity = new JLabel("Traveller city");
		JTextField travellerCityInput = new JTextField(20);
		JLabel travellerCountry = new JLabel("Traveller country");
		JTextField travellerCountryInput = new JTextField(20);

		SpinnerModel spinnerModel1 = new SpinnerNumberModel(5, 0, 10, 1);
		SpinnerModel spinnerModel2 = new SpinnerNumberModel(5, 0, 10, 1);
		SpinnerModel spinnerModel3 = new SpinnerNumberModel(5, 0, 10, 1);
		SpinnerModel spinnerModel4 = new SpinnerNumberModel(5, 0, 10, 1);
		SpinnerModel spinnerModel5 = new SpinnerNumberModel(5, 0, 10, 1);
		SpinnerModel spinnerModel6 = new SpinnerNumberModel(5, 0, 10, 1);
		SpinnerModel spinnerModel7 = new SpinnerNumberModel(5, 0, 10, 1);
		SpinnerModel spinnerModel8 = new SpinnerNumberModel(5, 0, 10, 1);
		SpinnerModel spinnerModel9 = new SpinnerNumberModel(5, 0, 10, 1);
		SpinnerModel spinnerModel10 = new SpinnerNumberModel(5, 0, 10, 1);
		SpinnerModel spinnerModel11 =new SpinnerNumberModel(3, 2, 5, 1);


		JLabel numberOfCitiesToCompare = new JLabel("Number of the cities to compare");
		JSpinner numberOfCitiesToCompareSlider = new JSpinner(spinnerModel11);
		JLabel cafe = new JLabel("Cafe");
		JSpinner cafeSlider = new JSpinner(spinnerModel1);
		JLabel sea = new JLabel("Sea");
		JSpinner seaSlider = new JSpinner(spinnerModel2);
		JLabel museums = new JLabel("Museums");
		JSpinner museumsSlider = new JSpinner(spinnerModel3);
		JLabel restaurant = new JLabel("Restaurant");
		JSpinner restaurantSlider = new JSpinner(spinnerModel4);
		JLabel stadium = new JLabel("Stadium");
		JSpinner stadiumSlider = new JSpinner(spinnerModel5);
		JLabel park = new JLabel("Park");
		JSpinner parkSlider = new JSpinner(spinnerModel6);
		JLabel club = new JLabel("Club");
		JSpinner clubSlider = new JSpinner(spinnerModel7);
		JLabel festival = new JLabel("Festival");
		JSpinner festivalSlider = new JSpinner(spinnerModel8);
		JLabel hospital = new JLabel("Hospital");
		JSpinner hospitalSlider = new JSpinner(spinnerModel9);
		JLabel theater = new JLabel("Theater");
		JSpinner theaterSlider = new JSpinner(spinnerModel10);

		//Submit button
		JButton submit = new JButton("Submit");

		//Add the elements to the panel
		panel1.add(travellerName);
		panel1.add(travellerNameInput);
		panel1.add(travellerAge);
		panel1.add(travellerAgeInput);
		panel1.add(travellerCity);
		panel1.add(travellerCityInput);
		panel1.add(travellerCountry);
		panel1.add(travellerCountryInput);
		panel1.add(cityName);
		panel1.add(cityNameInput);
		panel1.add(countryCode);
		panel1.add(countryCodeInput);
		panel1.add(numberOfCitiesToCompare);
		panel1.add(numberOfCitiesToCompareSlider);
		panel1.add(cafe);
		panel1.add(cafeSlider);
		panel1.add(sea);
		panel1.add(seaSlider);
		panel1.add(museums);
		panel1.add(museumsSlider);
		panel1.add(restaurant);
		panel1.add(restaurantSlider);
		panel1.add(stadium);
		panel1.add(stadiumSlider);
		panel1.add(park);
		panel1.add(parkSlider);
		panel1.add(club);
		panel1.add(clubSlider);
		panel1.add(festival);
		panel1.add(festivalSlider);
		panel1.add(hospital);
		panel1.add(hospitalSlider);
		panel1.add(theater);
		panel1.add(theaterSlider);
		panel1.add(submit);

		//Add scroll bar
		JScrollPane pane = new JScrollPane(panel1);

		//Add the panel to the frame
		frame.add(pane);
		frame.setBounds(0,0,700,400);

		frame.setVisible(true);

		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int age;

				try {
					age = Integer.parseInt(travellerAgeInput.getText());

					if (age >= 16 && age <= 25) {
						//traveller
						YoungTraveller traveller = new YoungTraveller();
						traveller.setAge(age);
						traveller.setName(travellerNameInput.getText());
						try {
							traveller.setCity(travellerCityInput.getText(), travellerCountryInput.getText(), cities);
						} catch (IOException ioException) {
							ioException.printStackTrace();
						}
						traveller.setGeodesicVector(traveller.getCity().getGeodesic_vector());

						int[] terms = new int[10];
						terms[0] = (int) seaSlider.getValue();
						terms[1] = (int) cafeSlider.getValue();
						terms[2] = (int) museumsSlider.getValue();
						terms[3] = (int) restaurantSlider.getValue();
						terms[4] = (int) stadiumSlider.getValue();
						terms[5] = (int) parkSlider.getValue();
						terms[6] = (int) clubSlider.getValue();
						terms[7] = (int) festivalSlider.getValue();
						terms[8] = (int) hospitalSlider.getValue();
						terms[9] = (int) theaterSlider.getValue();
						traveller.setTermsVector(terms);

						traveller.freeTicketEligibility(travellers, cities.get("Rome"), cities);

						travellers.add(traveller);
					} else if (age > 25 && age <= 60) {
						//traveller
						MiddleTraveller traveller = new MiddleTraveller();
						traveller.setAge(age);
						traveller.setName(travellerNameInput.getText());
						try {
							traveller.setCity(travellerCityInput.getText(), travellerCountryInput.getText(), cities);
						} catch (IOException ioException) {
							ioException.printStackTrace();
						}
						traveller.setGeodesicVector(traveller.getCity().getGeodesic_vector());

						int[] terms = new int[10];
						terms[0] = (int) seaSlider.getValue();
						terms[1] = (int) cafeSlider.getValue();
						terms[2] = (int) museumsSlider.getValue();
						terms[3] = (int) restaurantSlider.getValue();
						terms[4] = (int) stadiumSlider.getValue();
						terms[5] = (int) parkSlider.getValue();
						terms[6] = (int) clubSlider.getValue();
						terms[7] = (int) festivalSlider.getValue();
						terms[8] = (int) hospitalSlider.getValue();
						terms[9] = (int) theaterSlider.getValue();
						traveller.setTermsVector(terms);

						traveller.freeTicketEligibility(travellers, cities.get("Rome"), cities);

						travellers.add(traveller);
					} else if (age > 60 && age <= 115) {
						//traveller
						ElderTraveller traveller = new ElderTraveller();
						traveller.setAge(age);
						traveller.setName(travellerNameInput.getText());
						try {
							traveller.setCity(travellerCityInput.getText(), travellerCountryInput.getText(), cities);
						} catch (IOException ioException) {
							ioException.printStackTrace();
						}
						traveller.setGeodesicVector(traveller.getCity().getGeodesic_vector());

						int[] terms = new int[10];
						terms[0] = (int) seaSlider.getValue();
						terms[1] = (int) cafeSlider.getValue();
						terms[2] = (int) museumsSlider.getValue();
						terms[3] = (int) restaurantSlider.getValue();
						terms[4] = (int) stadiumSlider.getValue();
						terms[5] = (int) parkSlider.getValue();
						terms[6] = (int) clubSlider.getValue();
						terms[7] = (int) festivalSlider.getValue();
						terms[8] = (int) hospitalSlider.getValue();
						terms[9] = (int) theaterSlider.getValue();
						traveller.setTermsVector(terms);

						traveller.freeTicketEligibility(travellers, cities.get("Rome"), cities);

						travellers.add(traveller);
					}

					//City
					String cityName = cityNameInput.getText();
					String countryCode = countryCodeInput.getText();
					int numberOfCitiesToCompare = (int) numberOfCitiesToCompareSlider.getValue();


					JFrame result = new JFrame("Result");

					JPanel panel2 = new JPanel();
					panel2.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
					panel2.setLayout(new GridLayout(0, 1,30,30));

					JLabel similarity = new JLabel();
					similarity.setText("Similarity: "+ String.valueOf(travellers.get(travellers.size()-1).calculateSimilarity(cityName,countryCode,cities,travellers)));

					JLabel compareCities1 = new JLabel();
					compareCities1.setText("First Compare: "+ String.valueOf(travellers.get(travellers.size()-1).compareCities(cities,travellers).getName()));

					JLabel compareCities2 = new JLabel();
					ArrayList<City> cityTemp = travellers.get(travellers.size()-1).compareCities(cities,numberOfCitiesToCompare,travellers);
					String s = "";
					for (City city : cityTemp) {
						 s = s.concat(city.getName()+ ", ");
					}
					compareCities2.setText("Second Compare: "+ String.valueOf(s));

					JLabel sortedTravellerLabel = new JLabel();
					ArrayList<Traveller> sortedTraveller = sortTravellers(travellers);
					String sort = "";
					for (Traveller traveller : sortedTraveller) {
						sort = sort.concat(traveller.getName()+ ",");
					}
					sortedTravellerLabel.setText("Sorted Travellers: "+ String.valueOf(sort));

					City newCity = new City();
					newCity.findTheTermsForTheCity(cityName, countryCode, newCity);
					double[] tempVectors = newCity.getGeodesic_vector();
					if (tempVectors[0] == 0 && tempVectors[1] == 0){
						cities.remove(newCity.getName());
						throw new incorrectObjectException();
					}
					cities.put(newCity.getName(), newCity);

					panel2.add(similarity);
					panel2.add(compareCities1);
					panel2.add(compareCities2);
					panel2.add(sortedTravellerLabel);

					JScrollPane pane1 = new JScrollPane(panel2);

					result.add(pane1);
					result.setBounds(30,30,500,500);
					result.setVisible(true);

				} catch (incorrectObjectException | IOException incorrectObjectException) {
					JFrame error = new JFrame();
					JOptionPane.showMessageDialog(error, "Something went wrong. Check the inputs and try again");
				}


				try {
					writeJSON(travellers);
					updateDb(cities);
				} catch (IOException | SQLException ioException) {
					ioException.printStackTrace();
				}
			}
		});
	}

	private static void updateDb(HashMap<String, City> map) throws SQLException {

		//Create the connection with the database
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@oracle12c.hua.gr:1521:orcl", "IT219138", "IT219138");

		//Create the statement object
		Statement stmt = con.createStatement();
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
				//If the city is not in the database then is inserted
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
}}