package com.zelory.kace.adressbook;

import com.zelory.kace.adressbook.data.model.AddressBook;
import com.zelory.kace.adressbook.data.model.Person;
import com.zelory.kace.adressbook.ui.AddressBookFrame;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        AddressBook addressBook = new AddressBook();
        for (int i = 0; i < 300; i++) {
            Person person = new Person();
            person.setFirstName("Orang");
            person.setLastName("ke " + (i + 1));
            person.setAddress("Suatu tempat");
            person.setCity("Suatu kota");
            person.setState("Suatu negara");
            person.setZip(new Random().nextInt(9999) + "");
            addressBook.getPersons().add(person);
        }
        new AddressBookFrame(addressBook).setVisible(true);
    }
}
