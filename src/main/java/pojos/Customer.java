package pojos;

import lombok.*;

import java.io.Serializable;

@Getter @Setter @NoArgsConstructor
public class Customer implements Serializable {
    private int id;
    private String firstName;
    private String lastName;

    private Address address;
    private String phoneNumber;
    private String ssn;

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = new Address("Main Street 123", "Nothingville", "TX", "34543");
        this.phoneNumber = "+1 800 555-5555";
        this.ssn = "123 456 789";
    }
}
