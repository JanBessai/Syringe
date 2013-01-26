/*
 */
package edu.udo.cs.ls14.syringe.util;

import edu.udo.cs.ls14.syringe.repository.inject.spring.xml.XMLBeanBuilder;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import javax.inject.Provider;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.w3c.dom.Document;

/**
 *
 * @author Jan
 */
@Configuration
public class DefaultXMLRessourceFactoryConfiguration {
    private @Autowired XMLBeanBuilder documentFactory;
            
    public @Bean Transformer transformer() {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            return transformer;
        } catch (TransformerConfigurationException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public @Bean DOMSource source() {
        return new DOMSource();
    }
    
    public @Bean ByteArrayOutputStream xmlByteOut() {
        return new ByteArrayOutputStream();
    }
    
    public @Bean Result output() {
        try {
            return new StreamResult(new OutputStreamWriter(xmlByteOut(), "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public @Bean XMLRessourceFactory xmlRessourceFactory() {
        return new XMLRessourceFactory(transformer(), source(), xmlByteOut(), output(), documentFactory);
    }
}
