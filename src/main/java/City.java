
import java.io.IOException;
import java.util.*;


public class City {

    private static String name;
    private static int terms_vector[] = new int[10];
    private static double geodesic_vector[] = new double[2];

    //ArrayList for the City Object
    static ArrayList<City> cityObject = new ArrayList<>();

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
        return terms_vector.length;
    }

    public static void setTerms_vector(int theCafeNumber,int  theSeaNumber,int theMuseumsNumber,int theRestaurantNumber,int theStadiumNumber,int theParkNumber,int theClubNumber,int theFestivalNumber,int theHospitalNumber,int theTheaterNumber) {
        terms_vector[0] = theCafeNumber;
        terms_vector[1] = theSeaNumber;
        terms_vector[2] = theMuseumsNumber;
        terms_vector[3] = theRestaurantNumber;
        terms_vector[4] = theStadiumNumber;
        terms_vector[5] = theParkNumber;
        terms_vector[6] = theClubNumber;
        terms_vector[7] = theFestivalNumber;
        terms_vector[8] = theHospitalNumber;
        terms_vector[9] = theTheaterNumber;

    }

    public static double[] getGeodesic_vector() {
        return geodesic_vector;

    }

    public static void setGeodesic_vector(double lan, double lon) {
        geodesic_vector[0] = lan;
        geodesic_vector[1] = lon;
    }


    public static int findTheTermsForTheCity() throws IOException {

        City newCity = new City();

        //API KEY for the API
        String appid ="dd507317accd64c8447a7fadba863c9d";
        String city = "Rome";
        String country = "it";

        //Call the method and take the data from OpenWeatherMap and Wikipedia
        String theWikipediaText = OpenData.RetrieveData(city,country,appid);

        //Set the name of the city witch user choose
        setName(city);

        int theCafeNumber = CountWords.countCriterionfCity(theWikipediaText,"cafe");
        int theSeaNumber = CountWords.countCriterionfCity(theWikipediaText,"sea");
        int theMuseumsNumber = CountWords.countCriterionfCity(theWikipediaText,"museums");
        int theRestaurantNumber = CountWords.countCriterionfCity(theWikipediaText,"restaurant");
        int theStadiumNumber = CountWords.countCriterionfCity(theWikipediaText,"stadium");
        int theParkNumber = CountWords.countCriterionfCity(theWikipediaText,"park");
        int theClubNumber = CountWords.countCriterionfCity(theWikipediaText,"club");
        int theFestivalNumber = CountWords.countCriterionfCity(theWikipediaText,"festival");
        int theHospitalNumber = CountWords.countCriterionfCity(theWikipediaText,"hospital");
        int theTheaterNumber = CountWords.countCriterionfCity(theWikipediaText,"theater");

        setTerms_vector(theCafeNumber,theSeaNumber,theMuseumsNumber,theRestaurantNumber,theStadiumNumber,theParkNumber,theClubNumber,theFestivalNumber,theHospitalNumber,theTheaterNumber);

        cityObject.add(newCity);

        //Tester
        //System.out.println(cityObject.get(0).getName());



        return 0;
    }




}