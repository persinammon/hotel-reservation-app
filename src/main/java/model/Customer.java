package model;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Customer {

    private String firstName;
    private String lastName;
    private String email;

    public Customer(String firstName, String lastName, String email) {
        String emailRegex = "^(.+)@(.+).com$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Email invalid.");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Customer with first name " + this.firstName + ", last name "
                + this.lastName + ", email " + this.email;
    }
}
