import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class Main {

	public static void main(String[] args) throws IOException {

		ArrayList<City> cities = new ArrayList<>();
		ArrayList<Traveller> travellers = new ArrayList<>();

		try {

			City athens = new City();
			athens.findTheTermsForTheCity("Athens", "GR", athens);
			cities.add(athens);

			City rome = new City();
			rome.findTheTermsForTheCity("Rome", "IT", rome);
			cities.add(rome);

			YoungTraveller youngTraveller = new YoungTraveller();
			youngTraveller.setAge(19);
			youngTraveller.setCity(cities.get(0));
			youngTraveller.setGeodesicVector(youngTraveller.getCity().getGeodesic_vector());
			int[] youngTermsVector = {4, 5, 9, 6, 5, 7, 3, 2, 4, 10};
			youngTraveller.setTermsVector(youngTermsVector);
			travellers.add(youngTraveller);
			System.out.println("YoungTraveller: " + youngTraveller.getCity().getName() + " -> " + cities.get(1).getName() + ": " + youngTraveller.calculateSimilarity(cities.get(1)));

			MiddleTraveller middleTraveller = new MiddleTraveller();
			middleTraveller.setCity(cities.get(1));
			middleTraveller.setAge(45);
			middleTraveller.setGeodesicVector(middleTraveller.getCity().getGeodesic_vector());
			int[] middleTermsVector = {10, 5, 9, 4, 7, 3, 8, 1, 6, 8};
			middleTraveller.setTermsVector(middleTermsVector);
			travellers.add(middleTraveller);
			System.out.println("MiddleTraveller: " + middleTraveller.getCity().getName() + " -> " + cities.get(0).getName() + ": " + middleTraveller.calculateSimilarity(cities.get(0)));

			ElderTraveller elderTraveller = new ElderTraveller();
			elderTraveller.setAge(65);
			elderTraveller.setCity(cities.get(0));
			elderTraveller.setGeodesicVector(elderTraveller.getCity().getGeodesic_vector());
			int[] elderTermsVector = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
			elderTraveller.setTermsVector(elderTermsVector);
			travellers.add(elderTraveller);
			System.out.println("ElderTraveller: " + elderTraveller.getCity().getName() + " -> " + cities.get(1).getName() + ": " + elderTraveller.calculateSimilarity(cities.get(1)));

		}catch (Exception e){
			System.out.println("There is something wrong! Please try again.");
		}





	}

}
