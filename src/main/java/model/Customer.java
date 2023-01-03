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
        return "<Customer: firstName=" + this.firstName + ", lastName="
                + this.lastName + ", email=" + this.email + ">";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if ((obj == null) || !(obj instanceof Customer))
            return false;
        Customer custObj = (Customer) obj;
        return (this.firstName == custObj.firstName) &&
                (this.lastName == custObj.lastName) &&
                (this.email == custObj.email);
    }

    @Override
    public int hashCode() {
        String blob = this.firstName + this.lastName + this.email;
        return blob.hashCode();
    }
}
