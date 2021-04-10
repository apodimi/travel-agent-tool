public class MiddleTraveller extends Traveller {
    private int age; //(25,60]
    private double p = 0.5;
    private Traveller user;

    public MiddleTraveller() {

    }

    public MiddleTraveller(Traveller user) {
        this.user = user;
    }

    public void setAge(int age) {
        if (age > 25 && age <= 60) {
            this.age = age;
        } else {
            System.err.println("Age must be between 25 and 60 years old!");
        }
    }

    public int getAge() {
        return age;
    }

    @Override
    public double calculateSimilarity(City city) {
        return similarity(user, city);
    }

    public double similarity(Traveller user,City city) {
        return p*similarity_terms_vector(user, city)
                + (1-p)*similarityGeodesicVector(user, city);
    }

    private double similarity_terms_vector(Traveller user, City city) {
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
