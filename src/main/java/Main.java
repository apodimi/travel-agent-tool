import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class Main {

	public static void main(String[] args) throws IOException {

		ArrayList<City> cityObject = new ArrayList<>();

			try {

				City athens = new City();
				athens.findTheTermsForTheCity("Athens", "GR", athens);
				cityObject.add(athens);

				City rome = new City();
				rome.findTheTermsForTheCity("Rome", "IT", rome);
				cityObject.add(rome);
				boolean continueLoop = false;
			}catch (Exception e){
				System.out.println("There is something wrong! Please try again.");
			}


		/*System.out.println(cityObject.get(0).getName());
		System.out.println(cityObject.get(1).getName());

		System.out.println(Arrays.toString(cityObject.get(0).getGeodesic_vector()));
		System.out.println(Arrays.toString(cityObject.get(1).getGeodesic_vector()));

		System.out.println(Arrays.toString(cityObject.get(0).getTerms_vector()));*/
	}

}
