/*
 */
package edu.udo.cs.ls14.syringe.interpreter;

import edu.udo.cs.ls14.syringe.term.Variable;
import java.util.Map;
import javax.inject.Provider;

/**
 *
 * @author Jan
 */
public class VariableInterpreter<T extends Variable, R> extends Interpreter<T, R> {
    
    VariableInterpreter(InterpreterFactory factory, Map<String, Object> context, T variable) {
        super(factory, context, variable);
    }
    
    public R get() {
        Object result = context.get(term.toString());
        if (result instanceof Provider) {
            return ((Provider<R>)result).get();
        }
        return (R)result;
    }
    
}
