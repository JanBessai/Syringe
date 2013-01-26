/*
 */
package edu.udo.cs.ls14.syringe.demo.components;

import java.awt.Font;

/**
 *
 * @author Jan
 */
public class Hello implements Text {

    public Font getFont() {
        return new Font("Arial", Font.PLAIN, 12);
    }

    public String getText() {
        return "Hello World!";
    }
    
}
