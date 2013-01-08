/*
 */
package edu.udo.cs.ls14.syringe.repository.inject.spring.xml;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Jan
 */
public class XMLBeansDocumentFactory {
    public final static String NAMESPACE = "http://www.springframework.org/schema/beans";
    public final static String SCHEMA = "http://www.w3.org/2001/XMLSchema-instance";
    public final static String SCHEMA_LOCATION = "http://www.springframework.org/schema/beans "
            + "http://www.springframework.org/schema/beans/spring-beans.xsd";
    
    private final DOMImplementation domImplementation;
    
    public XMLBeansDocumentFactory(DOMImplementation domImplementation) {
        this.domImplementation = domImplementation;
    }
    
    public XMLBeanBuilder buildBeansDocument() {
        Document document = domImplementation.createDocument(NAMESPACE, "beans", null);
        Element root = document.getDocumentElement();
        root.setAttributeNS(SCHEMA, "xsi:schemaLocation", SCHEMA_LOCATION);
        return new XMLBeanBuilder(document, root);
    }
}
