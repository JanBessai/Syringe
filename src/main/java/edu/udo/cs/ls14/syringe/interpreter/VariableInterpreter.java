/*
 */
package edu.udo.cs.ls14.syringe.interpreter;

import edu.udo.cs.ls14.syringe.term.Variable;
import java.util.Map;

/**
 *
 * @author Jan
 */
public class VariableInterpreter<T extends Variable, R> extends Interpreter<T, R> {
    
    VariableInterpreter(InterpreterFactory factory, Map<String, Object> context, T variable, Class<R> resultType) {
        super(factory, context, variable, resultType);
    }
    
    public R get() {
        Object result = context.get(term.toString());
        if (result == null) {
            throw new FreeVariableNotInContextException(term);
        }
        if (!resultType.isAssignableFrom(result.getClass())) {
            throw new TypeError(resultType, result);
        }
        return (R)result;
    }
    
}
