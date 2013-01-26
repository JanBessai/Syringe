/*
 */
package edu.udo.cs.ls14.syringe.demo.components;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Jan
 */
public class MessageBox implements ActionListener {
    private final Text message;
    
    public MessageBox(Text message) {
        this.message = message;
    }
    
    public void actionPerformed(ActionEvent ae) {
        JOptionPane.showMessageDialog((Component)ae.getSource(), message.getText());
    }

    
}
