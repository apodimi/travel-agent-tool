import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Traveller implements Comparable<Traveller>{
	private int[] termsVector; //degree for each term
	private double[] geodesicVector; //latitude and longitude
	private City city; //city that the Traveller is in
	protected final int maxdist = 15317; //Athens to Sydney
	private Timestamp timestamp; //time of search
	private String visit; //name of the city that we recommend

	/**
	 * gets the name of the traveller
	 * @return the name of the traveller
	 */
	public String getName() {
		return name;
	}

	/**
	 * sets the name of the traveller
	 * @param name the name of the traveller
	 */
	public void setName(String name) {
		this.name = name;
	}

	private String name; //the name of the traveller

	/**
	 * gets the name of the city that we recommend
	 * @return the name of the city that we recommend
	 */
	public String getVisit() {
		return visit;
	}

	/**
	 * sets the name of the city that we recommend
	 * @param visit the name of the city that we recommend
	 */
	public void setVisit(String visit) {
		this.visit = visit;
	}

	/**
	 * sets the timestamp of the search
	 * @param timestamp the time and date of the search
	 */
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * gets the timestamp of the search
	 * @return the timestamp of the search
	 */
	public Timestamp getTimestamp() {
		return timestamp;
	}

	/**
	 * sets the city that the traveller is in
	 * @param cityName the name of the city
	 * @param countryCode the code of the country the city is in
	 * @param cities all the cities stored in a HashMap
	 * @throws IOException
	 */
	public void setCity(String cityName, String countryCode, HashMap<String, City> cities) throws IOException {
		this.city = checkIfCityExistsInCollection(cityName, countryCode, cities);
	}

	/**
	 * gets the city the traveller is in
	 * @return the city the traveller is in
	 */
	public City getCity() {
		return city;
	}

	/**
	 * gets the array with the terms
	 * @return the array with the degrees for each term
	 */
	public int[] getTermsVector() {
		return termsVector;
	}

	/**
	 * sets the array with the degrees for each term
	 * @param termsVector the array containing the terms
	 */
	public void setTermsVector(int[] termsVector) {
		this.termsVector = termsVector;
	}

	/**
	 * gets the array with the geodesic vector
	 * @return an array containing the latitude and longitude
	 */
	public double[] getGeodesicVector() {
		return geodesicVector;
	}

	/**
	 * sets the array with the geodesic vector
	 * @param geodesicVector the array containing the latitude and longitude
	 */
	public void setGeodesicVector(double[] geodesicVector) {
		this.geodesicVector = geodesicVector;
	}

	public abstract double calculateSimilarity(String cityName, String countryCode, HashMap<String, City> cities) throws IOException;

	/**
	 * compares the similarities between the cities
	 * @param cities an arraylist containing all the cities
	 * @return the city with the biggest similarity
	 */
	/*public City compareCities(ArrayList<City> cities) {
		double maxSimilarity = calculateSimilarity(cities.get(0)); //assume that the first city has the biggest similarity
		City maxCity = cities.get(0);

		//finds the city with the biggest similarity
		for (int i = 0; i < cities.size(); i++) {
			double tmp = calculateSimilarity(cities.get(i));
			if (tmp > maxSimilarity) {
				maxSimilarity = tmp;
				maxCity = cities.get(i);
			}
		}

		return maxCity;
	}*/
//TODO FIX THIS
	/**
	 * calculates the similarities of each city
	 * then returns the x biggest similarities in ascending order
	 * @param cities array list containing the cities
	 * @param x number of biggest similarities to return
	 * @return array list containing the x number of biggest similarities in ascending order
	 */
	/*public ArrayList<City> compareCities(ArrayList<City> cities, int x) {
		//check if x is in this range [2, 5]
		if (x < 2 || x > 5) {
			System.err.println("Can only calculate 2 to 5 similarities");
		}

		ArrayList<Double> similarities = new ArrayList<>(); //the similarities will be stored here
		ArrayList<City> tmpCities = new ArrayList<>(); //contains the cities
		tmpCities = cities;

		//calculate the similarity for each city and store it in the similarities array list
		for (City value : tmpCities) {
			similarities.add(calculateSimilarity(value));
		}

		//sort similarities and cities so that similarities[1] corresponds to cities[1]
		bubbleSort(similarities, tmpCities);

		//then remove all the unnecessary cities
		while (tmpCities.size() != x) {
			tmpCities.remove(0);
		}

		return tmpCities;
	}*/

	/**
	 * calculates the similarity geodesic vector
	 * @param city the city the user wants to travel to
	 * @return a double representing the similarity geodesic vector
	 */
	double similarityGeodesicVector(City city) {
		double x = 2 / (2 - (distance(city))/maxdist ); //distance found from OpenData, Now using Athens -> Rome
		return Math.log(x) / Math.log(2); //find log2 of the above equation
	}

	/**
	 * sorts in ascending order
	 * @param similarities array list containing the similarities for each city
	 * @param cities array list containing the cities
	 */
	public void bubbleSort(ArrayList<Double> similarities, ArrayList<City> cities) {
		int n = similarities.size();
		do {
			int reached = 0;
			for (int i = 1; i < similarities.size(); i++) {
				if (similarities.get(i-1).compareTo(similarities.get(i)) > 0) {
					double tmp = similarities.get(i);
					similarities.set(i, similarities.get(i-1));
					similarities.set(i-1, tmp);

					City tmpCity = cities.get(i);
					cities.set(i, cities.get(i-1));
					cities.set(i-1, tmpCity);

					reached = i;
				}
			}
			n = reached;
		} while (n>1);
	}

	/**
	 * code from https://www.geodatasource.com/developers/java
	 * distance is measured in kilometers
	 * @param city the city the user is in
	 * @return the geodesic distance between the city the user is in and the city the user wants to travel to
	 */
	private double distance(City city) {
		double[] geodesicVector = getGeodesicVector();
		double[] cityGeodesicVector = city.getGeodesic_vector();
		if ((geodesicVector[0] == cityGeodesicVector[0]) && (geodesicVector[1] == cityGeodesicVector[1])) {
			return 0;
		}
		else {
			double theta = geodesicVector[1] - cityGeodesicVector[1];
			double dist = Math.sin(Math.toRadians(geodesicVector[0])) * Math.sin(Math.toRadians(cityGeodesicVector[1])) + Math.cos(Math.toRadians(geodesicVector[0])) * Math.cos(Math.toRadians(cityGeodesicVector[1])) * Math.cos(Math.toRadians(theta));
			dist = Math.acos(dist);
			dist = Math.toDegrees(dist);
			dist = dist * 60 * 1.1515;
			dist = dist * 1.609344;

			return dist;
		}
	}

	/**
	 * checks if the city you are looking for already exists in the collection
	 * if not it retrieves the data for the city and adds the city to the collection
	 * if the city exists in the collection then it returns the city
	 * @param cityName the name of the city
	 * @param countryCode the code of the country the city is in
	 * @param cities the collection containing all the cities
	 * @return the city we are looking for
	 * @throws IOException
	 */
	public City checkIfCityExistsInCollection(String cityName, String countryCode, HashMap<String, City> cities) throws IOException {
		if (!cities.containsKey(cityName)){
			City newCity = new City();
			newCity.findTheTermsForTheCity(cityName, countryCode, newCity);
			cities.put(cityName, newCity);
		}
		return cities.get(cityName);
	}
}
