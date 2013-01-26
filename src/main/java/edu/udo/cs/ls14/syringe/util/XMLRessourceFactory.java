/*
 */
package edu.udo.cs.ls14.syringe.util;

import java.io.ByteArrayOutputStream;
import javax.inject.Provider;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import org.springframework.core.io.ByteArrayResource;
import org.w3c.dom.Document;

/**
 *
 * @author Jan
 */
public class XMLRessourceFactory implements Provider<ByteArrayResource> {
    private final Transformer transformer;
    private final DOMSource source;
    private final ByteArrayOutputStream xmlByteOut;
    private final Result output;
    private final Provider<Document> documentFactory;
    
    public XMLRessourceFactory(Transformer transformer, DOMSource source, ByteArrayOutputStream xmlByteOut, Result output, Provider<Document> documentFactory) {
        this.transformer = transformer;
        this.source = source;
        this.xmlByteOut = xmlByteOut;
        this.output = output;
        this.documentFactory = documentFactory;
    }
    
    @Override
    public ByteArrayResource get() {
        source.setNode(documentFactory.get());
        xmlByteOut.reset();
        try {
            transformer.transform(source, output);
        } catch (TransformerException ex) {
            throw new RuntimeException(ex);
        }
        return new ByteArrayResource(xmlByteOut.toByteArray());
    }
}
