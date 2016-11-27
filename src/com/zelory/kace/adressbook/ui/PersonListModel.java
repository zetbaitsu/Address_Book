package com.zelory.kace.adressbook.ui;

import com.zelory.kace.adressbook.data.model.Person;

import javax.swing.*;
import java.util.List;

public class PersonListModel extends DefaultListModel<Person> {

    public PersonListModel(List<Person> persons) {
        super();
        addElements(persons);
    }

    public void refreshWith(List<Person> persons) {
        clear();
        addElements(persons);
    }

    public void addElements(List<Person> persons) {
        persons.forEach(this::addElement);
    }
}
