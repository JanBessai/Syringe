package edu.udo.cs.ls14.syringe;

import edu.udo.cs.ls14.syringe.interpreter.Interpreter;
import edu.udo.cs.ls14.syringe.interpreter.InterpreterFactory;
import edu.udo.cs.ls14.syringe.repository.inject.spring.SpringBeanInjectionRepository;
import edu.udo.cs.ls14.syringe.repository.inject.spring.xml.XMLBeanBuilder;
import edu.udo.cs.ls14.syringe.repository.inject.spring.xml.XMLBeansDocumentFactory;
import edu.udo.cs.ls14.syringe.term.builder.TermBuilder;
import edu.udo.cs.ls14.syringe.term.builder.TermFactory;
import edu.udo.cs.ls14.syringe.term.parser.LambdaLexer;
import edu.udo.cs.ls14.syringe.term.parser.LambdaParser;
import edu.udo.cs.ls14.syringe.term.util.VariableSupply;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import javax.inject.Provider;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;

/**
 * Hello world!
 *
 */
public class App 
{
    public static final class Foo {
        private Bar bar;
        private Baz baz;
        public Foo(Bar bar, Baz baz) {
            this.bar = bar;
            this.baz = baz;
        }
        void print() {
            System.out.println("Foo!");
            bar.print();
            baz.print();
        }
    }
    public static final class Bar {
        void print() {
            System.out.println("Bar!");
        }
    }
    public static final class Baz {
        void print() {
            System.out.println("Baz!");
        }
    }    
    
    public static void main( String[] args ) {
        System.out.println( "Magic.." );
        try {
            DOMImplementation domBuilder = DOMImplementationRegistry.newInstance().getDOMImplementation("XML 3.0");
            Map<String, Object> context = new HashMap<String, Object>();
            XMLBeanBuilder builder = new XMLBeansDocumentFactory(domBuilder).buildBeansDocument();
            SpringBeanInjectionRepository repository = new SpringBeanInjectionRepository(builder, context);
            repository.registerBeanConstructor(Foo.class.getConstructors()[0], "foo");
            repository.registerBeanConstructor(Bar.class.getConstructors()[0], "bar");
            repository.registerBeanConstructor(Baz.class.getConstructors()[0], "baz");
            /*Function fooBuilder = (Function)context.get("foo");
            fooBuilder = (Function)fooBuilder.apply(((Provider<String>)context.get("bar")).get());
            String result = (String)fooBuilder.apply(((Provider<String>)context.get("baz")).get());
            System.out.printf("Result: %s\n", result);*/
            
            
            
            VariableSupply variableSupply = new VariableSupply(new HashSet<String>());
            TermFactory termFactory = new TermFactory(variableSupply);
            TermBuilder tb = termFactory.variable("foo")
                                .applyTo(termFactory.variable("x").get())
                                .abstractOver(termFactory.variable("x").get())
                                .applyTo(termFactory.variable("bar").get())
                                .applyTo(termFactory.variable("baz")
                                            .abstractOver(termFactory.variable("x").get())
                                            .applyTo(termFactory.variable("y").get())
                                            .get());
            System.out.printf("Term: %s\nNormal Form: %s\n", tb.get(), tb.toNormalForm().get());
            
            InterpreterFactory interpreterFactory = new InterpreterFactory(context);
            Interpreter interpreter = interpreterFactory.interpreter(tb.toNormalForm().get(), Provider.class);
            interpreter.get();
            
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Source source = new DOMSource(builder.get());
            ByteArrayOutputStream xmlByteOut = new ByteArrayOutputStream();
            Result output = new StreamResult(new OutputStreamWriter(xmlByteOut, "UTF-8"));
            /*transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");*/
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(source, output);
            
            ByteArrayResource xmlRessource = new ByteArrayResource(xmlByteOut.toByteArray());
            GenericXmlApplicationContext appContext = new GenericXmlApplicationContext();
            appContext.setValidating(true);
            appContext.load(xmlRessource);
            Foo foo = appContext.getBean("foo", Foo.class);
            foo.print();
            
            /*ANTLRStringStream in = new ANTLRStringStream("(\\ x -> z) (\\ x y z -> x y)");//"((\\x -> (foo) x) bar) ((\\x -> baz) y)");
            Lexer lexer = new LambdaLexer(in);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            LambdaParser parser = new LambdaParser(tokens);
            parser.setTermFactory(termFactory);
            System.out.println(parser.parse().get());*/
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }        
    }
}
