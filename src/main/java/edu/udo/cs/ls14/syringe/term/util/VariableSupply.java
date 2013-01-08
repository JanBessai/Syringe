/*
 */
package edu.udo.cs.ls14.syringe.term.util;

import java.util.Iterator;
import java.util.Set;

/**
 * Supplies fresh variables.
 * @author Jan
 */
public class VariableSupply implements Iterator<String> {
    private final Set<String> usedNames;
    private int variableNumber;
    
    
    public VariableSupply(Set<String> usedNames, int startAt) {
        this.usedNames = usedNames;
        this.variableNumber = startAt;
    }
    
    public VariableSupply(Set<String> usedNames) {
        this.usedNames = usedNames;
        this.variableNumber = 0;
    }

    public boolean hasNext() {
        return true;
    }

    public String next() {
        String variableName;
        do {
            variableName = String.format("%c%d", 'a' + variableNumber % 26, variableNumber / 26);
            ++variableNumber;
        } while (usedNames.contains(variableName));
        return variableName;
    }
    
    public void markUsed(String variableName) {
        usedNames.add(variableName);
    }

    public void remove() {
        throw new UnsupportedOperationException("Cannot 'unuse' variables.");
    }
    
}
