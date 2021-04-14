public class MiddleTraveller extends Traveller {
    private int age; //(25,60]
    private double p = 0.5;

    /**
     * sets the age of the traveller
     * @param age the age of the traveller
     */
    public void setAge(int age) {
        if (age > 25 && age <= 60) {
            this.age = age;
        } else {
            System.err.println("Age must be between 25 and 60 years old!");
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
}
