/*
 */
package edu.udo.cs.ls14.syringe.interpreter;

import com.google.common.base.Function;
import edu.udo.cs.ls14.syringe.term.Application;
import java.util.Map;
import javax.inject.Provider;

/**
 *
 * @author Jan
 */
public class ApplicationInterpreter<T extends Application, R> extends Interpreter<T, R> {

    public ApplicationInterpreter(InterpreterFactory factory, Map<String, Object> context, T application, Class<R> resultType) {
        super(factory, context, application, resultType);
    }
    
    public R get() {
        Object lhs = factory.interpreter(term.getAbstraction(), Function.class).get();
        Object value = factory.interpreter(term.getValue(), Object.class).get();
        Function function = (Function)lhs;
        Object result = function.apply(value);
        if (!resultType.isAssignableFrom(result.getClass())) {
            throw new TypeError(resultType, result);
        }
        return (R)result;
    }
    
}
