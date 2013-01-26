/*
 */
package edu.udo.cs.ls14.syringe.term.builder;

import edu.udo.cs.ls14.syringe.term.Term;
import edu.udo.cs.ls14.syringe.term.Variable;
import edu.udo.cs.ls14.syringe.term.util.VariableSupply;

/**
 *
 * @author Jan
 * @apiviz.uses edu.udo.cs.ls14.syringe.term.Variable
 */
public class VariableBuilder<T extends Variable> extends TermBuilder<T> {

    VariableBuilder(TermFactory factory, VariableSupply variableSupply, T variable) {
        super(factory, variableSupply, variable);
    }
    
    @Override
    public <TT extends Term> TermBuilder<? extends Term> substitute(Variable variable, TT by) {
        if (variable.equals(term)) {
            return builderFor(by);
        } else {
            return this;
        }
    } 

    @Override
    public TermBuilder<? extends Term> toNormalForm() {
        return this;
    }
}
