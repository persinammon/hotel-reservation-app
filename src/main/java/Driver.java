import model.*;
public class Driver {

    public static void main(String[] args) {
        Customer customer = new Customer("first", "second", "j@domain.com");
        System.out.println(customer.toString());
        //customer = new Customer("one", "two", "yoyoma");
        //System.out.println(customer.toString());
    }
}
