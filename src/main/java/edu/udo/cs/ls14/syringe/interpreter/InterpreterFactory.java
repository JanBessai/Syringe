/*
 */
package edu.udo.cs.ls14.syringe.interpreter;

import edu.udo.cs.ls14.syringe.term.Application;
import edu.udo.cs.ls14.syringe.term.Term;
import edu.udo.cs.ls14.syringe.term.Variable;
import java.util.Map;
import javax.inject.Provider;

/**
 *
 * @author Jan
 */
public class InterpreterFactory {
    private final Map<String, Object> context;
    
    public InterpreterFactory(Map<String, Object> context) {
        this.context = context;
    }
    
    <RR> Interpreter<? extends Variable, RR> interpreter(Variable variable, Class<RR> resultType) {
        return new VariableInterpreter(this, context, variable, resultType);
    }
    
    <RR> Interpreter<? extends Application, RR> interpreter(Application application, Class<RR> resultType) {
        return new ApplicationInterpreter<Application, RR>(this, context, application, resultType);
    }
    
    public <RR> Interpreter<? extends Term, RR> interpreter(Term term, Class<RR> resultType) {
        if (term instanceof Variable) {
            return interpreter((Variable)term, resultType);
        } else if (term instanceof Application) {
            return interpreter((Application)term, resultType);
        }
        throw new IllegalArgumentException(String.format("No builder for terms of type %s present", term.getClass().getSimpleName()));
    }
}
