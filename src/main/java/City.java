
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class City {

    private static String name;
    private static int terms_vector;
    private static double geodesic_vector[] = new double[2];

    //ArrayList for the City Object
    List<City> cityObject = new ArrayList<>();

    //Constructor
    public City(){

    }


    //Getter and Setter
    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        City.name = name;
    }

    public static int getTerms_vector() {
        return terms_vector;
    }

    public static void setTerms_vector(int terms_vector) {
        City.terms_vector = terms_vector;
    }

    public static double[] getGeodesic_vector() {
        return geodesic_vector;

    }

    public static void setGeodesic_vector(double lan, double lon) {
        geodesic_vector[0] = lan;
        geodesic_vector[1] = lon;
    }


    public int findTheTermsForTheCity() throws IOException {

        //API KEY for the API
        String appid ="dd507317accd64c8447a7fadba863c9d";

        String city = "Rome";
        String country = "it";

        //Call the method and take the data from OpenWeatherMap and Wikipedia
        OpenData.RetrieveData(city,country,appid);

        ///Set the name of the city witch user choose
        setName(city);

        //System.out.println(geodesic_vector[1]);

        return 0;
    }


    /*Connect 2 projects : https://stackoverflow.com/questions/31342689/how-do-i-use-classes-from-another-project-in-intellij-idea*/


}