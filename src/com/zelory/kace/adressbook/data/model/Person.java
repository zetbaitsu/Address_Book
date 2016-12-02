package com.zelory.kace.adressbook.data.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Person {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String phoneNumber;

    public String getName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return getName();
    }
}
