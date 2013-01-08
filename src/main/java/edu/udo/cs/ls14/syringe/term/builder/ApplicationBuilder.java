package edu.udo.cs.ls14.syringe.term.builder;

import edu.udo.cs.ls14.syringe.term.Abstraction;
import edu.udo.cs.ls14.syringe.term.Application;
import edu.udo.cs.ls14.syringe.term.Term;
import edu.udo.cs.ls14.syringe.term.Variable;
import edu.udo.cs.ls14.syringe.term.util.VariableSupply;

/**
 *
 * @author Jan
 */
public class ApplicationBuilder<T extends Application> extends TermBuilder<T> {

    ApplicationBuilder(TermFactory factory, VariableSupply variableSupply, T application) {
        super(factory, variableSupply, application);
    }
    
    @Override
    public <TT extends Term> TermBuilder<? extends Term> substitute(Variable variable, TT by) {
        TermBuilder abstraction = builderFor(term.getAbstraction()).substitute(variable, by);
        TermBuilder value = builderFor(term.getValue()).substitute(variable, by);
        return abstraction.applyTo(value.get());
    }

    @Override
    public TermBuilder<? extends Term> betaReduce() {
        Term lhs = term.getAbstraction();
        if (!(lhs instanceof Abstraction)) {
            return this;
        }
        Abstraction abstraction = (Abstraction)lhs;
        Variable variable = abstraction.getVariable();
        Term body = abstraction.getBody();
        Term value = term.getValue();
        return builderFor(body).substitute(variable, value);
    }

    @Override
    public TermBuilder<? extends Term> toNormalForm() {
        Term lhs = term.getAbstraction();
        Term value = term.getValue();
        TermBuilder normalized =
                builderFor(lhs)
                .toNormalForm()
                .applyTo(builderFor(value).toNormalForm().get());
        TermBuilder result = normalized.betaReduce();
        if (result == normalized) {
            return result;
        }
        return result;
    }
}
