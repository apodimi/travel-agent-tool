import java.util.Scanner;

public class YoungTraveller extends Traveller {
    private int age; //[16,25]
    private double p = 0.95;

    /**
     * sets the age of the traveller
     * @param age the age of the traveller
     */
    public void setAge(int age) {
        try {
            if (age < 16 || age > 25)
                throw new IndexOutOfBoundsException("Age must be in the range of 16 and 25!");
        } catch (Exception e) {
            System.out.println("Age is out of bounds! Will be converted to 21");
        } finally {
            this.age = 21;
        }
    }

    /**
     * gets the age of the traveller
     * @return the age of the traveller
     */
    public int getAge() {
        return age;
    }

    /**
     * calculates the similarity for the city the user wants to travel to
     * @param city the city the user wants to travel to
     * @return the similarity
     */
    @Override
    public double calculateSimilarity(City city) {
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
    public double similarity_terms_vector(City city) {
        int[] userTerms = getTermsVector(); //get the user's terms vector
        int[] cityTerms = city.getTerms_vector();
        int sum = 0;
        for (int i = 0; i < getTermsVector().length; i++) { //for each vector
            int subtraction = userTerms[i] - cityTerms[i];  //subtract the user's vector from the city's vector
            sum += Math.pow(subtraction, 2); //calculate the sum of the power
        }

        return 1/(1 + Math.sqrt(sum)); //return the division
    }
}
