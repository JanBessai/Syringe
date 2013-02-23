/*
 */
package edu.udo.cs.ls14.syringe.interpreter;

import edu.udo.cs.ls14.syringe.term.Term;
import java.util.Map;
import javax.inject.Provider;

/**
 *
 * @author Jan
 */
public abstract class Interpreter<T extends Term, R> implements Provider<R> {
    protected final InterpreterFactory factory;
    protected final Map<String, Object> context;
    protected final T term;
    protected final Class<R> resultType;
    
    Interpreter(InterpreterFactory factory, Map<String, Object> context, T term, Class<R> resultType) {
        this.factory = factory;
        this.context = context;
        this.term = term;
        this.resultType = resultType;
    }
    
    public abstract R get();
}
