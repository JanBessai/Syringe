/*
 */
package edu.udo.cs.ls14.syringe.demo.components;

import java.awt.Font;

/**
 *
 * @author Jan
 */
public class Ciao implements Text {
    public Font getFont() {
        return new Font("Comic Sans MS", Font.ITALIC, 16);
    }
    
    public String getText() {
        return "Ciao!";
    }
}
