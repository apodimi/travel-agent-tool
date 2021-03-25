package HUA.ORG;

import java.util.*;

public abstract class Traveller {
	private int[] termsVector; //degree for each term
	private double[] geographicVector; //latitude and longitude
	private City city; //city that the Traveller is in
	protected final int maxdist = 15317; //Athens to Sydney

	//sets the name of the city the traveller is in
	public void setCity(City city) {
		this.city = city;
	}

	//gets the name of the city the traveller is in
	public City getCity() {
		return city;
	}

	//gets the array with the degrees for each term
	public int[] getTermsVector() {
		return termsVector;
	}

	//sets the array with the degrees for each term
	public void setTermsVector(int[] termsVector) {
		this.termsVector = termsVector;
	}

	//gets the array with the geographic vector
	public double[] getGeographicVector() {
		return geographicVector;
	}

	//sets the array with the geographic vector
	public void setGeographicVector(double[] geographicVector) {
		this.geographicVector = geographicVector;
	}

	abstract double calculateSimilarity(City city);

	//compares the similarity of the cities and returns the city with the biggest similarity
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

	public ArrayList<City> compareCities(ArrayList<City> cities, int x) {
		//calculate all similarities
		//store them in similarities array
		//return the x biggest similarities
		//TODO
		ArrayList<Double> similarities = new ArrayList<>(); //contains the similarity for each city
		ArrayList<City> cityArrayList = new ArrayList<>(); //contains the cities

		for (int i = 0; i < cities.size(); i++) {
			similarities.add(calculateSimilarity(cities.get(i)));
			cityArrayList.add(cities.get(i));
		}

		//TODO
		//sort similarities and cityArrayList

		bubbleSort(similarities, cityArrayList);

		//then return the x number of cities with the biggest

		//cityArrayList.remove(); -> to remove unnecessary cities

		return cityArrayList;
	}

	int similarityGeodesicVector(Traveller user, City city) {
		//TODO distance
		double x = 2 / (2 - (distance(user, city))/maxdist ); //distance found from OpenData, Now using Athens -> Rome
		double log2 = Math.log(x) / Math.log(2); //find log2 of the above equation
		return 0;
	}

	public void bubbleSort(ArrayList<Double> similarities, ArrayList<City> cityArrayList) {
		int n = similarities.size();
		do {
			int reached = 0;
			for (int i = 1; i < similarities.size(); i++) {
				if (similarities.get(i-1).compareTo(similarities.get(i)) > 0) {
					double tmp = similarities.get(i);
					similarities.set(i, similarities.get(i-1));
					similarities.set(i-1, tmp);

					City tmpCity = cityArrayList.get(i);
					cityArrayList.set(i, cityArrayList.get(i-1));
					cityArrayList.set(i-1, tmpCity);

					reached = i;
				}
			}
			n = reached;
		} while (n>1);
	}

	public double distance(Traveller user, City city) {
		return 525.53;
	}
}
