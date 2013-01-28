/*
 */
package edu.udo.cs.ls14.syringe.demo.components;

import javax.swing.JLabel;

/**
 *
 * @author Jan
 */
public class Label extends JLabel {
    public Label(Text text) {
        setText(text.getText());
        setFont(text.getFont());
    }
}
