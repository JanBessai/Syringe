/*
 */
package edu.udo.cs.ls14.syringe.repository.inject.spring.xml;

import javax.inject.Provider;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Jan
 */
public class XMLBeanPropertyBuilder implements Provider<Document> {
    private final Document document;
    private final Element beanElement;
    
    XMLBeanPropertyBuilder(Document document, Element beanElement) {
        this.document = document;
        this.beanElement = beanElement;
    }
    
    private boolean hasIndexAttribute(Element element, int number) {
        String index = element.getAttribute("index");
        return (index != null && 
                index.trim().equals(Integer.toString(number)));
    }
    
    private Element findChildNodeForNumber(int number) {
        NodeList childNodes = beanElement.getElementsByTagName("constructor-arg");
        for (int i = 0; i < childNodes.getLength(); ++i) {
            Element child = (Element)childNodes.item(i);
            if (hasIndexAttribute(child, number)) {
                return child;
            }
        }
        return null;
    }
    
    private Element addNewConstructorArgument(int number) {
        Element constructorArgument = document.createElement("constructor-arg");
        constructorArgument.setAttribute("index", Integer.toString(number));
        beanElement.appendChild(constructorArgument);
        return constructorArgument;
    }
    
    public XMLBeanPropertyBuilder addConstructorArgument(int number, String reference) {
        Element constructorArgument = findChildNodeForNumber(number);
        if (constructorArgument == null) {
            constructorArgument = addNewConstructorArgument(number);
        }
        constructorArgument.setAttribute("ref", reference);
        return this;
    }

    public Document get() {
        return document;
    }
}
