
import java.util.*;

public class City {

    private static String name;
    private static int terms_vector[];
    private static int geodesic_vector[];

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

    public static int[] getTerms_vector() {
        return terms_vector;
    }

    public static void setTerms_vector(int[] terms_vector) {
        City.terms_vector = terms_vector;
    }

    public static int[] getGeodesic_vector() {
        return geodesic_vector;
    }

    public static void setGeodesic_vector(int[] geodesic_vector) {
        City.geodesic_vector = geodesic_vector;
    }


    int findTheTermsForTheCity(String name, int terms_vector[], int geodesic_vector[]){

        return 0;
    }


    /*Connect 2 projects : https://stackoverflow.com/questions/31342689/how-do-i-use-classes-from-another-project-in-intellij-idea*/


}