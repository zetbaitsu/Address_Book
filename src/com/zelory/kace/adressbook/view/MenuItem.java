package com.zelory.kace.adressbook.view;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MenuItem extends JMenuItem {
    public MenuItem(String text, ActionListener actionListener) {
        super(text);
        addActionListener(actionListener);
    }
}
