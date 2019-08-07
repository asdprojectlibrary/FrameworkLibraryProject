package model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
 public class Person extends BaseModel {
    private String firstName;
    private String lastName;
    private String telephone;
    private Address address;

    public Person(String f, String l, String t, Address a) {
        firstName = f;
        lastName = l;
        telephone = t;
        address = a;
    }
}

