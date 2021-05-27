import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ElderTraveller extends Traveller {
    private int age; //(60-115]
    private double p = 0.05;

    public ElderTraveller(){}

    public ElderTraveller(ElderTraveller elderTraveller) {
        setName(elderTraveller.getName());
        setCity(elderTraveller.getCity());
        setAge(elderTraveller.getAge());
        setVisit(elderTraveller.getVisit());
        setGeodesicVector(elderTraveller.getGeodesicVector());
        setTermsVector(elderTraveller.getTermsVector());
        setTimestamp(elderTraveller.getTimestamp());
    }

    /**
     * sets the age of the traveller
     * @param age the age of the traveller
     */
    public void setAge(int age) {
        try {
            if (age < 61 || age > 115)
                throw new IndexOutOfBoundsException("Age must be in the range of 16 and 25!");
        } catch (Exception e) {
            System.out.println("Age is out of bounds! Will be converted to 61");
        } finally {
            this.age = 61;
        }
    }

    /**
     * gets the age of the traveller
     * @return the age of the traveller
     */
    public int getAge() {
        return age;
    }

    @Override
    public double calculateSimilarity(String cityName, String countryCode, HashMap<String, City> cities, ArrayList<Traveller> travellers) throws IOException {
        City city = checkIfCityExistsInCollection(cityName, countryCode, cities);
        setTimestamp(new Timestamp(System.currentTimeMillis()));
        travellers.add(new ElderTraveller(this));
        return similarity(city);
    }

    /**
     * calculate the similarity based on the terms similarity and the geodesic vector similarity
     * @param city the city the user wants to travel to
     * @return the similarity
     */
    public double similarity(City city) {
        return p*similarity_terms_vector(city) + (1-p)*similarityGeodesicVector(city);
    }

    /**
     * calculates the terms similarity
     * @param city the city the user wants to travel to
     * @return the similarity based on the terms
     */
    private double similarity_terms_vector(City city) {
        int[] userTermsVector = getTermsVector();
        int[] cityTermsVector = city.getTerms_vector();

        double sum = 0;
        double upperPart; //upper part of the division
        double lowerPart; //lower part of the division
        for (int i = 0; i < userTermsVector.length; i++) {
            if (Math.abs(userTermsVector[i]) != 0 && Math.abs(cityTermsVector[i]) != 0) {
                upperPart = 1; //number & number = 1
            } else {
                upperPart = 0; //number & 0 = 0
            }

            lowerPart = Math.abs(userTermsVector[i] + cityTermsVector[i]);
            sum += upperPart/lowerPart;
        }
        return sum;
    }

    @Override
    public int compareTo(Traveller o) {
        return getTimestamp().compareTo(o.getTimestamp());
    }

    @Override
    public String toString() {
        return "ElderTraveller [ name: "+getName()+", age: "+age+", visit: "+getVisit()+", timestamp: "+getTimestamp()+", termsVector: "+ Arrays.toString(getTermsVector())+", city: "+getCity().getName()+", geodesicVector: "+Arrays.toString(getGeodesicVector())+" ]";
    }
}
