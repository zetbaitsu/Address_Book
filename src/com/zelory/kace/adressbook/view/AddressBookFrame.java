package com.zelory.kace.adressbook.view;

import com.zelory.kace.adressbook.data.model.AddressBook;
import com.zelory.kace.adressbook.data.model.Person;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class AddressBookFrame extends JFrame {

    public AddressBookFrame() {
        this(new AddressBook());
    }

    public AddressBookFrame(AddressBook addressBook) {
        super();
        createMenuBar();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(addressBook.getName());
        setSize(600, 480);

        JList<Person> personList = new JList(new PersonListModel(addressBook.getPersons()));
        personList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        personList.setSelectedIndex(0);
        JScrollPane personListScrollPane = new JScrollPane(personList);

        JPanel actionPanel = new JPanel();
        GridLayout actionLayout = new GridLayout(1, 5);
        actionLayout.setHgap(4);
        actionLayout.setVgap(4);

        actionPanel.setLayout(actionLayout);
        actionPanel.add(new Button("Add", e -> addPerson()));
        actionPanel.add(new Button("Edit", e -> editPerson()));
        actionPanel.add(new Button("Delete", e -> deletePerson()));
        actionPanel.add(new Button("Sort by Name", e -> sortByName()));
        actionPanel.add(new Button("Sort by ZIP", e -> sortByZip()));

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 0, 4));
        mainPanel.setLayout(new BorderLayout(4, 4));
        mainPanel.add(personListScrollPane, BorderLayout.CENTER);
        mainPanel.add(actionPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar(); // Instansiasi MenuBar
        JMenu fileMenu = new JMenu("File"); // Buat sebuah menu dangan nama File
        fileMenu.add(new MenuItem("New", e -> createAddressBook()));
        fileMenu.add(new MenuItem("Open", e -> openAddressBook()));
        fileMenu.add(new MenuItem("Close", e -> closeAddressBook()));
        fileMenu.add(new MenuItem("Save", e -> saveAddressBook()));
        fileMenu.add(new MenuItem("Save as", e -> saveAsAddressBook()));
        fileMenu.add(new MenuItem("Print", e -> printAddressBook()));
        fileMenu.add(new MenuItem("Quit", e -> quitFromApps()));
        menuBar.add(fileMenu); // Tambahkan menu File kedalam MenuBar
        setJMenuBar(menuBar); // Set frame untuk menggunakan MenuBar yang telah dibuat
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            Random r = new Random();
            Toolkit tk = Toolkit.getDefaultToolkit();
            Dimension d = tk.getScreenSize();
            int x = r.nextInt(d.width - getWidth());
            int y = r.nextInt(d.height - getHeight());
            setLocation(x, y);
        }
        super.setVisible(visible);
    }

    private void createAddressBook() {
        new AddressBookFrame().setVisible(true);
    }

    private void openAddressBook() {

    }

    private void closeAddressBook() {
        setVisible(false);
        dispose();
    }

    private void saveAddressBook() {

    }

    private void saveAsAddressBook() {

    }

    private void printAddressBook() {

    }

    private void quitFromApps() {
        System.exit(0);
    }

    private void addPerson() {

    }

    private void editPerson() {

    }

    private void deletePerson() {

    }

    private void sortByName() {

    }

    private void sortByZip() {

    }
}
