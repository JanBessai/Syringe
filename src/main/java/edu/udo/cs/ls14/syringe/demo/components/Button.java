/*
 */
package edu.udo.cs.ls14.syringe.demo.components;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author Jan
 */
public class Button extends JButton {
    public Button(Text text, ActionListener callback) {
        super.setFont(text.getFont());
        super.setText(text.getText());
        super.addActionListener(callback);
    }
}
