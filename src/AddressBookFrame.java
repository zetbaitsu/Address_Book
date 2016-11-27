import javax.swing.*;

public class AddressBookFrame extends JFrame {

    public AddressBookFrame() {
        super();
        createMenuBar();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Adress Book");
        setSize(480, 240);
        setResizable(false);
    }

    private void createMenuBar() {
        //New, Open, Close, Save, Save As ..., Print, and Quit
        JMenuBar menuBar = new JMenuBar(); // Instansiasi MenuBar
        JMenu fileMenu = new JMenu("File"); // Buat sebuah menu dangan nama File
        JMenuItem newItemMenu = new JMenuItem("New"); // Buat sebuah item menu dengan nama New
        JMenuItem openItemMenu = new JMenuItem("Open");
        JMenuItem closeItemMenu = new JMenuItem("Close");
        JMenuItem saveItemMenu = new JMenuItem("Save");
        JMenuItem saveAsItemMenu = new JMenuItem("Save as");
        JMenuItem printItemMenu = new JMenuItem("Print");
        JMenuItem quitItemMenu = new JMenuItem("Quit");

        fileMenu.add(newItemMenu); // Tambahkan item menu New ke dalam menu File
        fileMenu.add(openItemMenu);
        fileMenu.add(closeItemMenu);
        fileMenu.add(saveItemMenu);
        fileMenu.add(saveAsItemMenu);
        fileMenu.add(printItemMenu);
        fileMenu.add(quitItemMenu);

        menuBar.add(fileMenu); // Tambahkan menu File kedalam MenuBar
        setJMenuBar(menuBar); // Set frame untuk menggunkan MenuBar yang telah dibuat
    }
}
