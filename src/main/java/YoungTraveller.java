import java.util.Arrays;

public class YoungTraveller extends Traveller {
    private int age; //[16,25]
    private double p = 0.95;
    private Traveller user;

    public YoungTraveller() {

    }

    public YoungTraveller(Traveller user) {
        this.user = user;
    }

    public void setAge(int age) {
        if (age >= 16 && age <= 25) {
            this.age = age;
        } else {
            System.err.println("Age must be between 16 and 25 years old!");
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

    public double similarity_terms_vector(Traveller user, City city) {
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
