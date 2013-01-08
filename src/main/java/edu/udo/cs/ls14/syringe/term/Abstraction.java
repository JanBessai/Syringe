/*
 */
package edu.udo.cs.ls14.syringe.term;

import com.google.common.collect.Sets;
import java.util.Set;

/**
 *
 * @author Jan
 */
public class Abstraction implements Term {
    private final Variable variable;
    private final Term body;
    
    public Abstraction(Variable variable, Term body) {
        this.variable = variable;
        this.body = body;
    }
    
    /**
     * @return the variable
     */
    public Variable getVariable() {
        return variable;
    }

    /**
     * @return the body
     */
    public Term getBody() {
        return body;
    }


    @Override
    public String toString() {
        return String.format("(\\%s -> %s)", variable.toString(), body.toString());
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Abstraction)) {
            return false;
        }
        Abstraction a = (Abstraction)o;
        return (a.variable.equals(variable) && a.body.equals(body));
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.variable != null ? this.variable.hashCode() : 0);
        hash = 17 * hash + (this.body != null ? this.body.hashCode() : 0);
        return hash;
    }

    public Set<Variable> variables() {
        return Sets.union(body.variables(), variable.variables());
    }

    public Set<Variable> freeVariables() {
        return Sets.difference(body.freeVariables(), variable.variables());
    }
}
