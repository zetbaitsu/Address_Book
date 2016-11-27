package com.zelory.kace.adressbook.data.model;

import lombok.Getter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
public class AddressBook {
    private String name;
    private List<Person> persons;

    public static AddressBook loadFromFile(File file) {
        return new AddressBook();
    }

    public AddressBook() {
        this("Untitled", new ArrayList<>());
    }

    public AddressBook(String name, List<Person> persons) {
        this.name = name;
        this.persons = persons;
    }
}
