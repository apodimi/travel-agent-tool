import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class Traveller {
	private int[] termsVector; //degree for each term
	private double[] geodesicVector; //latitude and longitude
	private City city; //city that the Traveller is in
	protected final int maxdist = 15317; //Athens to Sydney

	/**
	 * sets the city that the traveller is in
	 * @param city the city the traveller is in
	 */
	public void setCity(City city) {
		this.city = city;
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

	/**
	 * calculates the similarity for the city the user wants to travel to
	 * @param city the city the user wants to travel to
	 * @return the similarity
	 */
	public abstract double calculateSimilarity(City city);

	/**
	 * compares the similarities between the cities
	 * @param cities an arraylist containing all the cities
	 * @return the city with the biggest similarity
	 */
	public City compareCities(ArrayList<City> cities) {
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
	}

	/**
	 * calculates the similarities of each city
	 * then returns the x biggest similarities in ascending order
	 * @param cities array list containing the cities
	 * @param x number of biggest similarities to return
	 * @return array list containing the x number of biggest similarities in ascending order
	 */
	public ArrayList<City> compareCities(ArrayList<City> cities, int x) {
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
	}

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
}
