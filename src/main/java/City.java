import java.io.IOException;
import java.util.*;


public class City {

    private String name;
    private String countryCode;
    private int[] terms_vector = new int[10];
    private double[] geodesic_vector = new double[2];

    //Constructor
    public City(){

    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * gets the name of the traveller
     * @return the name of the traveller
     */
    public String getName() {
        return name;
    }

    /**
     * set the name of the traveller
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets the array with the terms about the city
     * @return the array with the terms about the city
     */
    public int[] getTerms_vector() {
        return terms_vector;
    }

    /**
     * set the array with the terms about the city
     */
    public void setTerms_vector(int theCafeNumber,int  theSeaNumber,int theMuseumsNumber,int theRestaurantNumber,int theStadiumNumber,int theParkNumber,int theClubNumber,int theFestivalNumber,int theHospitalNumber,int theTheaterNumber) {
        this.terms_vector[0] = theCafeNumber;
        this.terms_vector[1] = theSeaNumber;
        this.terms_vector[2] = theMuseumsNumber;
        this.terms_vector[3] = theRestaurantNumber;
        this.terms_vector[4] = theStadiumNumber;
        this.terms_vector[5] = theParkNumber;
        this.terms_vector[6] = theClubNumber;
        this.terms_vector[7] = theFestivalNumber;
        this.terms_vector[8] = theHospitalNumber;
        this.terms_vector[9] = theTheaterNumber;
    }

    /**
     * gets the array with the latitude and longitude
     * @return the array with the latitude and longitude
     */
    public double[] getGeodesic_vector() {
        return geodesic_vector;
    }

    /**
     * set the array with the latitude and longitude
     */
    public void setGeodesic_vector(double lat, double lon) {
        this.geodesic_vector[0] = lat;
        this.geodesic_vector[1] = lon;
    }

    /**
     * The method which use the API to take the data
     */
    public void findTheTermsForTheCity(String city, String country, City newCity) throws IOException {
        //API KEY for the API
        String appid ="dd507317accd64c8447a7fadba863c9d";

        //Call the method and take the data from OpenWeatherMap and Wikipedia
        String theWikipediaText = OpenData.RetrieveData(city,country,appid, newCity);

        //Set the name of the city witch user choose
        setName(city);

        //Set the code of the country
        setCountryCode(country);

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
    }

}