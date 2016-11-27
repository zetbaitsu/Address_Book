package com.zelory.kace.adressbook;

import com.zelory.kace.adressbook.data.model.AddressBook;
import com.zelory.kace.adressbook.data.model.Person;
import com.zelory.kace.adressbook.ui.AddressBookFrame;
import com.zelory.kace.adressbook.util.RandomUtil;

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
            person.setZip(RandomUtil.getInstance().randomInt(1000, 9999) + "");
            addressBook.getPersons().add(person);
        }
        new AddressBookFrame(addressBook).setVisible(true);
    }
}
