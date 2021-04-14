public class ElderTraveller extends Traveller {
    private int age; //(60-115]
    private double p = 0.05;

    /**
     * sets the age of the traveller
     * @param age the age of the traveller
     */
    public void setAge(int age) {
        if (age > 60 && age <= 115) {
            this.age = age;
        } else {
            System.err.println("Age must be between 60 and 115 years old!");
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
}
