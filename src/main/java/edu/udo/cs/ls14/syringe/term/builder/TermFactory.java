/*
 */
package edu.udo.cs.ls14.syringe.term.builder;

import edu.udo.cs.ls14.syringe.term.Variable;
import edu.udo.cs.ls14.syringe.term.util.VariableSupply;

/**
 *
 * @author Jan
 */
public class TermFactory {
    private final VariableSupply variableSupply;
    
    public TermFactory(VariableSupply variableSupply) {
        this.variableSupply = variableSupply;
    }
    
    public TermBuilder<Variable> variable(String variableName) {
        variableSupply.markUsed(variableName);
        return new VariableBuilder(this, variableSupply, new Variable(variableName));
    }
}
