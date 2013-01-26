/*
 */
package edu.udo.cs.ls14.syringe.demo;

import javax.swing.JTextArea;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

/**
 *
 * @author Jan
 */
public class TextAreaErrorReporter extends BaseErrorListener {
    private final JTextArea textArea;
    
    TextAreaErrorReporter(JTextArea textArea) {
        this.textArea = textArea;
    }
    
    @Override
    public void syntaxError(Recognizer<?, ?> recognizer,
            Object offendingSymbol,
            int line,
            int charPositionInLine,
            String msg,
            RecognitionException e) {
        textArea.append(String.format("%s in line %d position %d\n",
                msg, line, charPositionInLine));
    }
}
