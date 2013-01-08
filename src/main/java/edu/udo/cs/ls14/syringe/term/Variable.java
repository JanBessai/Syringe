package edu.udo.cs.ls14.syringe.term;

import java.util.Collections;
import java.util.Set;

/**
 *
 * @author Jan
 */
public class Variable implements Term {
    private final String name;
        
    public Variable(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Variable)) {
            return false;
        }
        Variable v = (Variable)o;
        return v.name.equals(name);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    public Set<Variable> variables() {
        return Collections.singleton(this);
    }

    public Set<Variable> freeVariables() {
        return variables();
    }
}
