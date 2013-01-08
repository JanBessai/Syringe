/*
 */
package edu.udo.cs.ls14.syringe.term;

import java.util.Set;

/**
 *
 * @author Jan
 */
public interface Term {
    public Set<Variable> variables();
    public Set<Variable> freeVariables();
}
