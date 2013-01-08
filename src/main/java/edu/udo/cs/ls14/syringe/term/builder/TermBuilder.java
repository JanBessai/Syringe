/*
 */
package edu.udo.cs.ls14.syringe.term.builder;

import edu.udo.cs.ls14.syringe.term.Abstraction;
import edu.udo.cs.ls14.syringe.term.Application;
import edu.udo.cs.ls14.syringe.term.Term;
import edu.udo.cs.ls14.syringe.term.Variable;
import edu.udo.cs.ls14.syringe.term.util.VariableSupply;
import javax.inject.Provider;

/**
 *
 * @author Jan
 */
public abstract class TermBuilder<T extends Term> implements Provider<T> {
    protected final TermFactory factory;
    protected final VariableSupply variableSupply;
    protected final T term;
    
    TermBuilder(TermFactory factory, VariableSupply variableSupply, T term) {
        this.factory = factory;
        this.variableSupply = variableSupply;
        this.term = term;
    }
    
    public T get() {
        return term;
    }
    
    protected <TT extends Term> TermBuilder<TT> builderFor(TT term) {
        if (term instanceof Variable) {
            return new VariableBuilder(factory, variableSupply, (Variable)term);
        } else if (term instanceof Application) {
            return new ApplicationBuilder(factory, variableSupply, (Application)term);
        } else if (term instanceof Abstraction) {
            return new AbstractionBuilder(factory, variableSupply, (Abstraction)term);
        }
        
        throw new IllegalArgumentException(String.format("No builder for terms of type %s present", term.getClass().toString()));
    }
    
    public TermBuilder<Abstraction> abstractOver(Variable variable) {
        return new AbstractionBuilder(factory, variableSupply, new Abstraction(variable, term));
    }
    
    public TermBuilder<Application>applyTo(Term term) {
        return new ApplicationBuilder(factory, variableSupply, new Application(this.term, term));
    }
    
    public TermBuilder<? extends Term> betaReduce() {
        return this;
    }
    
    public abstract <TT extends Term> TermBuilder<? extends Term> substitute(Variable variable, TT by);
    public abstract TermBuilder<? extends Term> toNormalForm();    
}
