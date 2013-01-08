/*
 */
package edu.udo.cs.ls14.syringe.interpreter;

import com.google.common.base.Function;
import edu.udo.cs.ls14.syringe.term.Application;
import java.util.Map;

/**
 *
 * @author Jan
 */
public class ApplicationInterpreter<T extends Application, R> extends Interpreter<T, R> {

    public ApplicationInterpreter(InterpreterFactory factory, Map<String, Object> context, T application) {
        super(factory, context, application);
    }
    
    public R get() {
        Function function = (Function)factory.interpreter(term.getAbstraction()).get();
        Object value = factory.interpreter(term.getValue()).get();
        return (R)(function.apply(value));
    }
    
}
