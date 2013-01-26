/*
 */
package edu.udo.cs.ls14.syringe.interpreter;

import edu.udo.cs.ls14.syringe.term.Variable;

/**
 *
 * @author Jan
 */
public class FreeVariableNotInContextException extends RuntimeException {
    private final Variable freeVariable;
    
    FreeVariableNotInContextException(Variable freeVariable) {
        super(String.format("The variable %s is free but not defined in the current context", freeVariable.toString()));
        this.freeVariable = freeVariable;
    }
    
    public Variable getFreeVariable() {
        return freeVariable;
    }
}
