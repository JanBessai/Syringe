/*
 */
package edu.udo.cs.ls14.syringe.term;

import com.google.common.collect.Sets;
import java.util.Set;

/**
 *
 * @author Jan
 */
public class Application implements Term {
    private final Term abstraction;
    private final Term value;
    
    public Application(Term abstraction, Term value) {
        this.abstraction = abstraction;
        this.value = value;
    }

    /**
     * @return the abstraction
     */
    public Term getAbstraction() {
        return abstraction;
    }

    /**
     * @return the value
     */
    public Term getValue() {
        return value;
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Application)) {
            return false;
        }
        Application a = (Application)o;
        return (a.getAbstraction().equals(getAbstraction()) 
                && a.getValue().equals(getValue()));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.getAbstraction() != null ? this.getAbstraction().hashCode() : 0);
        hash = 89 * hash + (this.getValue() != null ? this.getValue().hashCode() : 0);
        return hash;
    }
    
    @Override
    public String toString() {
        return String.format("(%s %s)", getAbstraction().toString(), getValue().toString());
    }

    public Set<Variable> variables() {
        return Sets.union(abstraction.variables(), value.variables());
    }

    public Set<Variable> freeVariables() {
        return Sets.union(abstraction.freeVariables(), value.freeVariables());
    }
}
