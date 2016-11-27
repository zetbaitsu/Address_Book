package com.zelory.kace.adressbook.ui;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Class Button yang merupakan subclass dari JButton
 * Class ini dubuat untuk menghandle instansiasi button langsung dengan ActionListener nya
 * karena class JButton tidak memiliki konstruktor dengan parameter ActionListener
 */
public class Button extends JButton {

    /**
     * Konstrukor Button dengan text dan ActionListener
     *
     * @param text           Text yang akan ditampilkan oleh button
     * @param actionListener Listener yang akan dipanggil ketika button di klik
     */
    public Button(String text, ActionListener actionListener) {
        super(text);
        addActionListener(actionListener);
    }
}
