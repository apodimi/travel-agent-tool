public class ElderTraveller extends Traveller {
    private int age; //(60-115]
    private double p = 0.05;
    private Traveller user;

    public ElderTraveller() {}

    public ElderTraveller(Traveller user) {
        this.user = user;
    }

    public void setAge(int age) {
        if (age > 60 && age <= 115) {
            this.age = age;
        } else {
            System.err.println("Age must be between 60 and 115 years old!");
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
        return p*similarity_terms_vector(user, city) + (1-p)*similarityGeodesicVector(user, city);
    }

    private double similarity_terms_vector(Traveller user, City city) {
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
