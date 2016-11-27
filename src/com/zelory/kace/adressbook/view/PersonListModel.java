package com.zelory.kace.adressbook.view;

import com.zelory.kace.adressbook.data.model.Person;

import javax.swing.*;
import java.util.List;

public class PersonListModel extends DefaultListModel<Person> {

    public PersonListModel(List<Person> persons) {
        super();
        persons.parallelStream().forEach(this::addElement);
    }
}
