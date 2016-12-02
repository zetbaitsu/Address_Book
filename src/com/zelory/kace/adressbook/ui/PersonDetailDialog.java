package com.zelory.kace.adressbook.ui;

import com.zelory.kace.adressbook.data.model.Person;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.*;

public class PersonDetailDialog extends JDialog {
    private JTextField firsNameField;
    private JTextField lastNameField;
    private JTextField addressField;
    private JTextField cityField;
    private JTextField stateField;
    private JTextField zipField;
    private JTextField phoneField;

    private Person person;
    private SaveListener saveListener;
    private boolean create;

    public PersonDetailDialog(JFrame parent, Person person) {
        this(parent, person, null);
    }

    public PersonDetailDialog(JFrame parent, Person person, SaveListener saveListener) {
        super(parent);
        this.person = person;
        this.saveListener = saveListener;
        create = person == null;

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(320, 240);
        setResizable(false);
        setModalityType(ModalityType.DOCUMENT_MODAL);

        JPanel personDetailPanel = new JPanel();
        GridLayout personDetailLayout = new GridLayout(7, 2);
        personDetailLayout.setHgap(4);
        personDetailLayout.setVgap(4);
        personDetailPanel.setLayout(personDetailLayout);

        firsNameField = new JTextField();
        lastNameField = new JTextField();
        addressField = new JTextField();
        cityField = new JTextField();
        stateField = new JTextField();
        zipField = new JTextField();
        phoneField = new JTextField();

        PlainDocument integerOnly = new PlainDocument();
        integerOnly.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int off, String str, AttributeSet attr)
                    throws BadLocationException
            {
                fb.insertString(off, str.replaceAll("\\D++", ""), attr);  // remove non-digits
            }
            @Override
            public void replace(FilterBypass fb, int off, int len, String str, AttributeSet attr)
                    throws BadLocationException
            {
                fb.replace(off, len, str.replaceAll("\\D++", ""), attr);  // remove non-digits
            }
        });

        phoneField.setDocument(integerOnly);


        personDetailPanel.add(new JLabel("First Name"));
        personDetailPanel.add(firsNameField);
        personDetailPanel.add(new JLabel("Last Name"));
        personDetailPanel.add(lastNameField);
        personDetailPanel.add(new JLabel("Address"));
        personDetailPanel.add(addressField);
        personDetailPanel.add(new JLabel("City"));
        personDetailPanel.add(cityField);
        personDetailPanel.add(new JLabel("State"));
        personDetailPanel.add(stateField);
        personDetailPanel.add(new JLabel("ZIP Code"));
        personDetailPanel.add(zipField);
        personDetailPanel.add(new JLabel("Phone Number"));
        personDetailPanel.add(phoneField);

        JPanel actionPanel = new JPanel();
        GridLayout actionLayout = new GridLayout(1, 2);
        actionLayout.setHgap(4);
        actionLayout.setVgap(4);

        actionPanel.setLayout(actionLayout);
        actionPanel.add(new Button(create ? "Save" : "Update", e -> savePerson()));
        actionPanel.add(new Button(create ? "Cancel" : "Close", e -> close()));

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        mainPanel.setLayout(new BorderLayout(4, 4));
        mainPanel.add(personDetailPanel, BorderLayout.CENTER);
        mainPanel.add(actionPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);

        if (!create) {
            showPersonDetail();
        } else {
            setTitle("Add Person");
        }
    }

    private void showPersonDetail() {
        setTitle(person.getName());
        firsNameField.setText(person.getFirstName());
        lastNameField.setText(person.getLastName());
        addressField.setText(person.getAddress());
        cityField.setText(person.getCity());
        stateField.setText(person.getState());
        zipField.setText(person.getZip());
        phoneField.setText(person.getPhoneNumber());

        firsNameField.setEditable(false);
        lastNameField.setEditable(false);
    }

    private void savePerson() {
        if(validateInput()) {
            JOptionPane.showMessageDialog(this, "Plase provide person name!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (create) {
            person = new Person();
        }
        person.setFirstName(firsNameField.getText());
        person.setLastName(lastNameField.getText());
        person.setAddress(addressField.getText());
        person.setCity(cityField.getText());
        person.setState(stateField.getText());
        person.setZip(zipField.getText());
        person.setPhoneNumber(phoneField.getText());
        if (saveListener != null) {
            saveListener.onPersonSaved(person);
        }
        if (create) {
            close();
        }
    }

    private boolean validateInput() {
        String firstName = firsNameField.getText().trim();
        String lastName = lastNameField.getText().trim();

        return firstName.equals("") || lastName.equals("");
    }

    private void close() {
        setVisible(false);
        dispose();
    }

    public interface SaveListener {
        void onPersonSaved(Person person);
    }
}
