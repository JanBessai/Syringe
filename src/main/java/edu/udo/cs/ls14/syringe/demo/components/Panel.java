/*
 */
package edu.udo.cs.ls14.syringe.demo.components;

import java.awt.CardLayout;
import java.awt.Component;
import javax.swing.JPanel;

/**
 *
 * @author Jan
 */
public class Panel extends JPanel {
    public Panel(Component component) {
        setLayout(new CardLayout());
        add(component);
        component.setVisible(true);
    }
}
