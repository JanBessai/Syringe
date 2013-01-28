/*
 */
package edu.udo.cs.ls14.syringe.demo;

import edu.udo.cs.ls14.syringe.demo.components.Button;
import edu.udo.cs.ls14.syringe.demo.components.Ciao;
import edu.udo.cs.ls14.syringe.demo.components.Hello;
import edu.udo.cs.ls14.syringe.demo.components.Label;
import edu.udo.cs.ls14.syringe.demo.components.MessageBox;
import edu.udo.cs.ls14.syringe.demo.components.Panel;
import edu.udo.cs.ls14.syringe.interpreter.InterpreterFactory;
import edu.udo.cs.ls14.syringe.repository.inject.spring.SpringBeanInjectionRepository;
import edu.udo.cs.ls14.syringe.repository.inject.spring.xml.XMLBeanBuilder;
import edu.udo.cs.ls14.syringe.repository.inject.spring.xml.XMLBeansDocumentFactory;
import edu.udo.cs.ls14.syringe.term.builder.TermFactory;
import edu.udo.cs.ls14.syringe.term.parser.LambdaLexer;
import edu.udo.cs.ls14.syringe.term.parser.LambdaParser;
import edu.udo.cs.ls14.syringe.term.parser.TermVisitor;
import edu.udo.cs.ls14.syringe.term.util.VariableSupply;
import edu.udo.cs.ls14.syringe.util.DefaultXMLRessourceFactoryConfiguration;
import edu.udo.cs.ls14.syringe.util.XMLRessourceFactory;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import javax.swing.JTextArea;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;

/**
 *
 * @author Jan
 */
@Configuration
@Import({DefaultXMLRessourceFactoryConfiguration.class})
public class SyringeDemoConfiguration {
    
    public @Autowired XMLRessourceFactory xmlRessourceFactory;
    
    public @Bean VariableSupply variableSupply() {
        return new VariableSupply(new HashSet<String>());
    }
    
    public @Bean TermFactory termFactory() {
        return new TermFactory(variableSupply());
    }
    
    public @Bean LambdaLexer lexer() {
        LambdaLexer lexer = new LambdaLexer(new ANTLRInputStream());
        lexer.removeErrorListeners();
        lexer.addErrorListener(errorReporter());
        return lexer;
    }
    
    public @Bean LambdaParser parser() {
        LambdaParser parser = new LambdaParser(new BufferedTokenStream(lexer()));
        parser.removeErrorListeners();
        parser.addErrorListener(errorReporter());
        return parser;
    }
    
    public @Bean TermVisitor visitor() {
        return new TermVisitor(termFactory());
    }
    
    public @Bean JTextArea errorArea() {
        return new JTextArea();
    }
    
    public @Bean TextAreaErrorReporter errorReporter() {
        return new TextAreaErrorReporter(errorArea());
    }
    
    public @Bean SyringeDemoWindow syringeDemoWindow() {
        return new SyringeDemoWindow(errorArea(), lexer(), parser(), visitor(), interpreterFactory(), xmlRessourceFactory, context(), genericXmlApplicationContext());
    }
    
    public @Bean DOMImplementation domBuilder() {
        try {
            return DOMImplementationRegistry.newInstance().getDOMImplementation("XML 3.0");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public @Bean XMLBeansDocumentFactory xmlBeansDocumentFactory() {
        return new XMLBeansDocumentFactory(domBuilder());
    }
      
    public @Bean XMLBeanBuilder xmlBeanBuilder() {
        return xmlBeansDocumentFactory().buildBeansDocument();
    }
    
    public @Bean Map<String, Object> context() {
        return new HashMap<String, Object>();
    }
    
    public @Bean SpringBeanInjectionRepository springBeanInjectionRepository() {
        SpringBeanInjectionRepository repository = new SpringBeanInjectionRepository(xmlBeanBuilder(), context());
        repository.registerBeanConstructor(Panel.class.getConstructors()[0], "panel");
        repository.registerBeanConstructor(Label.class.getConstructors()[0], "label");
        repository.registerBeanConstructor(Button.class.getConstructors()[0], "button");
        repository.registerBeanConstructor(Hello.class.getConstructors()[0], "hello");
        repository.registerBeanConstructor(Ciao.class.getConstructors()[0], "ciao");
        repository.registerBeanConstructor(MessageBox.class.getConstructors()[0], "messageBox");
        return repository;
    }
    
    public @Bean InterpreterFactory interpreterFactory() {
        return new InterpreterFactory(context());
    }
    
    public @Bean GenericXmlApplicationContext genericXmlApplicationContext() {
        return new GenericXmlApplicationContext();
    }
}
