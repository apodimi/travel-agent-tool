import javax.swing.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MiddleTraveller extends Traveller {
    private int age; //(25,60]
    private double p = 0.5;

    public MiddleTraveller(){}

    public MiddleTraveller(MiddleTraveller middleTraveller) {
        setName(middleTraveller.getName());
        setCity(middleTraveller.getCity());
        setAge(middleTraveller.getAge());
        setVisit(middleTraveller.getVisit());
        setGeodesicVector(middleTraveller.getGeodesicVector());
        setTermsVector(middleTraveller.getTermsVector());
        setTimestamp(middleTraveller.getTimestamp());
    }

    /**
     * sets the age of the traveller
     * @param age the age of the traveller
     */
    public void setAge(int age) {
        try {
            if (age < 26 || age > 60)
                throw new IndexOutOfBoundsException("Age must be in the range of 16 and 25!");
        } catch (Exception e) {
            System.out.println("Age is out of bounds! Will be converted to 26");
        } finally {
            this.age = 26;
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
        travellers.add(new MiddleTraveller(this));
        return similarity(city);
    }

    /**
     * calculate the similarity based on the terms similarity and the geodesic vector similarity
     * @param city the city the user wants to travel to
     * @return the similarity
     */
    public double similarity(City city) {
        return p*similarity_terms_vector(city)
                + (1-p)*similarityGeodesicVector(city);
    }

    /**
     * calculates the terms similarity
     * @param city the city the user wants to travel to
     * @return the similarity based on the terms
     */
    private double similarity_terms_vector(City city) {
        int[] userVector = getTermsVector();
        int[] cityVector = city.getTerms_vector();

        int upperSum = 0;
        int lowerUserVectorSum = 0;
        int lowerCityVectorSum = 0;
        double lowerUserVectorRoot;
        double lowerCityVectorRoot;
        for (int i = 0; i < userVector.length; i++) {
            upperSum += userVector[i] * cityVector[i];
            lowerUserVectorSum += Math.pow(userVector[i], 2);
            lowerCityVectorSum += Math.pow(cityVector[i], 2);
        }

        lowerUserVectorRoot = Math.sqrt(lowerUserVectorSum);
        lowerCityVectorRoot = Math.sqrt(lowerCityVectorSum);

        return upperSum/(lowerUserVectorRoot * lowerCityVectorRoot);
    }

    @Override
    public int compareTo(Traveller o) {
        return getTimestamp().compareTo(o.getTimestamp());
    }

    @Override
    public String toString() {
        return "MiddleTraveller [ name: "+getName()+", age: "+age+", visit: "+getVisit()+", timestamp: "+getTimestamp()+", termsVector: "+ Arrays.toString(getTermsVector())+", city: "+getCity().getName()+", geodesicVector: "+Arrays.toString(getGeodesicVector())+" ]";
    }

    /**
     * checks if the traveller has the biggest similarity for the city in order to win the free ticket
     * @param travellers array list containing all the travellers
     * @param city the city with the free ticket
     * @param cities hash map containing all the cities
     */
    public void freeTicketEligibility(ArrayList<Traveller> travellers, City city, HashMap<String, City> cities) throws IOException {
        ArrayList<Double> similarities = new ArrayList<>();
        ArrayList<Traveller> sortedTravellers = Main.sortTravellers(travellers);

        for (Traveller sortedTraveller : sortedTravellers) {
            similarities.add(sortedTraveller.calculateSimilarity(city.getName(), city.getCountryCode(), cities, travellers));
        }

        double maxSimilarity = calculateSimilarity(city.getName(), city.getCountryCode(), cities, travellers);
        boolean newMaxSimilarity = false;
        for (Double similarity : similarities) {
            if (maxSimilarity < similarity) {
                newMaxSimilarity = true;
                break;
            }
        }

        if (!newMaxSimilarity) {
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame, "Congratulations MiddleTraveller! You've won a FREE ticket to " + city.getName());
        }
    }
}
