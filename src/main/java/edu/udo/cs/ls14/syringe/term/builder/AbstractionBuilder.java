/*
 */
package edu.udo.cs.ls14.syringe.term.builder;

import edu.udo.cs.ls14.syringe.term.Abstraction;
import edu.udo.cs.ls14.syringe.term.Term;
import edu.udo.cs.ls14.syringe.term.Variable;
import edu.udo.cs.ls14.syringe.term.util.VariableSupply;

/**
 *
 * @author Jan
 */
public class AbstractionBuilder<T extends Abstraction> extends TermBuilder<T> {
    
    AbstractionBuilder(TermFactory factory, VariableSupply variableSupply, T abstraction) {
        super(factory, variableSupply, abstraction);
    }
    
    @Override
    public <TT extends Term> TermBuilder<? extends Term> substitute(Variable variable, TT by) {
        Variable termVariable = term.getVariable();
        Term termBody = term.getBody();
        
        if (variable.equals(termVariable)) {
            return this;
        } else if (!by.freeVariables().contains(termVariable)
                   && !termBody.freeVariables().contains(variable)) {
            return builderFor(termBody)
                    .substitute(variable, by)
                    .abstractOver(termVariable);
        } else {
            Variable freshVariable = factory.variable(variableSupply.next()).get();
            return builderFor(termBody)
                    .substitute(termVariable, freshVariable)
                    .substitute(variable, by)
                    .abstractOver(freshVariable);
        }
    }

    @Override
    public TermBuilder<? extends Term> toNormalForm() {
        Variable termVariable = term.getVariable();
        Term termBody = term.getBody();
        return builderFor(termBody).toNormalForm().abstractOver(termVariable);
    }
}
