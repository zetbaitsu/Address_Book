package com.zelory.kace.adressbook.ui;

import com.zelory.kace.adressbook.AddressBookApps;
import com.zelory.kace.adressbook.data.model.AddressBook;
import com.zelory.kace.adressbook.data.model.Person;
import com.zelory.kace.adressbook.presenter.AddressBookPresenter;
import com.zelory.kace.adressbook.util.RandomUtil;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.WatchEvent;

public class AddressBookFrame extends JFrame implements AddressBookPresenter.View, PersonDetailDialog.SaveListener {

    private JLabel status;
    private JProgressBar progressBar;
    private JList<Person> personList;
    private PersonListModel personListModel;
    private JFileChooser fileChooser;

    private AddressBook addressBook;
    private AddressBookPresenter presenter;

    public AddressBookFrame() {
        this(new AddressBook());
    }

    public AddressBookFrame(AddressBook addressBook) {
        super();
        this.addressBook = addressBook;
        presenter = new AddressBookPresenter(this);

        createMenuBar();
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeAddressBook();
            }
        });
        setTitle(addressBook.getName());
        setSize(600, 480);

        personListModel = new PersonListModel(addressBook.getPersons());
        personList = new JList(personListModel);
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

        status = new JLabel("Address Book v1.0.0");
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setVisible(false);

        JPanel statusPanel = new JPanel(new BorderLayout(4, 4));
        statusPanel.add(status, BorderLayout.WEST);
        statusPanel.add(progressBar, BorderLayout.EAST);

        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new BoxLayout(footerPanel, BoxLayout.Y_AXIS));
        footerPanel.add(actionPanel);
        footerPanel.add(statusPanel);

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        mainPanel.setLayout(new BorderLayout(4, 4));
        mainPanel.add(personListScrollPane, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);

        AddressBookApps.getInstance().incrementFrameCount();
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
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            int x = RandomUtil.getInstance().randomInt(dimension.width - getWidth());
            int y = RandomUtil.getInstance().randomInt(dimension.height - getHeight());
            setLocation(x, y);
        }
        super.setVisible(visible);
    }

    private void initFileChooser() {
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("ZKC File", "zkc"));
    }

    private void createAddressBook() {
        new AddressBookFrame().setVisible(true);
    }

    private void openAddressBook() {
        checkIsAddressBookModified();

        if (fileChooser == null) {
            initFileChooser();
        }

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            presenter.loadAddressBook(fileChooser.getSelectedFile());
        }
    }

    private void saveAddressBook() {
        if (addressBook.getPath() == null || addressBook.getPath().isEmpty()) {
            saveAsAddressBook();
        } else {
            presenter.saveAddressBook(addressBook, addressBook.getPath());
        }
    }

    private void saveAsAddressBook() {
        if (fileChooser == null) {
            initFileChooser();
        }

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            presenter.saveAddressBook(addressBook, fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void printAddressBook() {
        presenter.printAddressBook(addressBook);
    }

    private void addPerson() {
        PersonDetailDialog personDetailDialog = new PersonDetailDialog(this, null, this);
        personDetailDialog.setLocationRelativeTo(this);
        personDetailDialog.setVisible(true);
    }

    private void editPerson() {
        if (personList.getSelectedValue() == null) {
            showError("Please select person to edit!");
        } else {
            PersonDetailDialog personDetailDialog = new PersonDetailDialog(this, personList.getSelectedValue(), this);
            personDetailDialog.setLocationRelativeTo(this);
            personDetailDialog.setVisible(true);
        }
    }

    private void deletePerson() {
        Person person = personList.getSelectedValue();
        if (person == null) {
            showError("Please select person to delete!");
        } else {
            int confirmed = JOptionPane.showConfirmDialog(this,
                    "Are you sure want to delete " + person + "?", "Save", JOptionPane.YES_NO_OPTION);

            if (confirmed == JOptionPane.YES_OPTION) {
                addressBook.removePerson(person);
                showAddressBook(addressBook);
            }
        }
    }

    private void sortByName() {
        presenter.sortPersonsByName(addressBook);
    }

    private void sortByZip() {
        presenter.sortPersonsByZip(addressBook);
    }

    @Override
    public void showAddressBook(AddressBook addressBook) {
        this.addressBook = addressBook;
        setTitle(this.addressBook.getName());
        personListModel.refreshWith(this.addressBook.getPersons());
        personList.updateUI();
    }

    @Override
    public void onAddressBookModified(WatchEvent<?> event) {
        addressBook.setModified(true);
        int confirmed = JOptionPane.showConfirmDialog(this,
                "Address book file have been changed, reload it?", "Reload File", JOptionPane.YES_NO_OPTION);

        if (confirmed == JOptionPane.YES_OPTION) {
            presenter.loadAddressBook(fileChooser.getSelectedFile());
        }
    }

    @Override
    public void onPersonSaved(Person person) {
        int result = addressBook.addOrUpdatePerson(person);
        showAddressBook(addressBook);
        if (result == AddressBook.PERSON_ADDED) {
            personList.ensureIndexIsVisible(personListModel.size() - 1);
        }
    }

    @Override
    public void showLoading() {
        status.setText("Loading...");
        progressBar.setVisible(true);
    }

    @Override
    public void dismissLoading() {
        status.setText("Address Book v1.0.0");
        progressBar.setVisible(false);
    }

    @Override
    public void showError(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void checkIsAddressBookDeleted() {
        if (addressBook.getPath() != null) {
            if (!new File(addressBook.getPath()).exists()) {
                int confirmed = JOptionPane.showConfirmDialog(this,
                        "Address book have been deleted, save as new file?", "Save", JOptionPane.YES_NO_OPTION);

                if (confirmed == JOptionPane.YES_OPTION) {
                    saveAsAddressBook();
                }
            }
        }
    }

    private void checkIsAddressBookModified() {
        if (addressBook.isModified()) {
            int confirmed = JOptionPane.showConfirmDialog(this,
                    "Do you want to save modified contact?", "Save", JOptionPane.YES_NO_OPTION);

            if (confirmed == JOptionPane.YES_OPTION) {
                saveAddressBook();
            }
        }
    }

    private void closeAddressBook() {
        checkIsAddressBookDeleted();
        checkIsAddressBookModified();
        setVisible(false);
        dispose();

        presenter.detachView();

        AddressBookApps.getInstance().decrementFrameCount();
        if (AddressBookApps.getInstance().getFrameCount() <= 0) {
            quitFromApps();
        }
    }

    private void quitFromApps() {
        System.exit(0);
    }
}
