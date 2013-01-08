/*
 */
package edu.udo.cs.ls14.syringe.interpreter;

import edu.udo.cs.ls14.syringe.term.Application;
import edu.udo.cs.ls14.syringe.term.Term;
import edu.udo.cs.ls14.syringe.term.Variable;
import java.util.Map;

/**
 *
 * @author Jan
 */
public class InterpreterFactory {
    private final Map<String, Object> context;
    
    public InterpreterFactory(Map<String, Object> context) {
        this.context = context;
    }
    
    <RR> Interpreter<? extends Variable, RR> interpreter(Variable variable) {
        return new VariableInterpreter(this, context, variable);
    }
    
    <RR> Interpreter<? extends Application, RR> interpreter(Application application) {
        return new ApplicationInterpreter<Application, RR>(this, context, application);
    }
    
    public <RR> Interpreter<? extends Term, RR> interpreter(Term term) {
        if (term instanceof Variable) {
            return interpreter((Variable)term);
        } else if (term instanceof Application) {
            return interpreter((Application)term);
        }
        throw new IllegalArgumentException(String.format("No builder for terms of type %s present", term.getClass().toString()));
    }
}
