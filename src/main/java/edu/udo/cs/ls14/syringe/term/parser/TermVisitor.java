/*
 */
package edu.udo.cs.ls14.syringe.term.parser;

import com.google.common.collect.Lists;
import edu.udo.cs.ls14.syringe.term.Term;
import edu.udo.cs.ls14.syringe.term.builder.TermBuilder;
import edu.udo.cs.ls14.syringe.term.builder.TermFactory;
import edu.udo.cs.ls14.syringe.term.parser.LambdaParser.AbstractionContext;
import edu.udo.cs.ls14.syringe.term.parser.LambdaParser.ApplicationContext;
import edu.udo.cs.ls14.syringe.term.parser.LambdaParser.ParenthesisContext;
import edu.udo.cs.ls14.syringe.term.parser.LambdaParser.PassDownContext;
import edu.udo.cs.ls14.syringe.term.parser.LambdaParser.VariableContext;
import edu.udo.cs.ls14.syringe.term.parser.LambdaParser.VariableLevelContext;
import java.util.Iterator;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 *
 * @author Jan
 */
public class TermVisitor extends LambdaBaseVisitor<TermBuilder<? extends Term>> {
    private final TermFactory termFactory;
    
    public TermVisitor(TermFactory termFactory) {
        this.termFactory = termFactory;
    }
    
    @Override
    public TermBuilder<? extends Term> visitApplication(ApplicationContext ctx) {
        Iterator<VariableLevelContext> applicationIterator = ctx.variableLevel().iterator();
        TermBuilder<? extends Term> result = visit(applicationIterator.next());
        while (applicationIterator.hasNext()) {
            result = result.applyTo(visit(applicationIterator.next()).get());
        }
        return result;
    }

    @Override
    public TermBuilder<? extends Term> visitAbstraction(AbstractionContext ctx) {
        TermBuilder<? extends Term> result = visit(ctx.applicationLevel());
        for (TerminalNode var : Lists.reverse(ctx.ID())) {
            result = result.abstractOver(termFactory.variable(var.getText()).get());
        }
        return result;
    }
    
    @Override
    public TermBuilder<? extends Term> visitPassDown(PassDownContext ctx) {
        return visit(ctx.applicationLevel());
    }

    @Override
    public TermBuilder<? extends Term> visitParenthesis(ParenthesisContext ctx) {
        return visit(ctx.abstractionLevel());
    }

    @Override
    public TermBuilder<? extends Term> visitVariable(VariableContext ctx) {
        return termFactory.variable(ctx.getText());
    }
}