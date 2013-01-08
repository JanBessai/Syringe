/*
 */
package edu.udo.cs.ls14.syringe.repository.inject.spring.xml;

import java.lang.reflect.Constructor;
import javax.inject.Provider;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Jan
 */
public class XMLBeanBuilder implements Provider<Document> {
    private final Document document;
    private final Element beansElement;
    
    XMLBeanBuilder(Document document, Element beansElement) {
        this.document = document;
        this.beansElement = beansElement;
    }
    
    private boolean hasIDAttribute(Element element, String id) {
        String index = element.getAttribute("id");
        return (index != null && 
                index.equals(id));
    }
    
    private Element findChildNodeWithID(String id) {
        NodeList childNodes = beansElement.getElementsByTagName("bean");
        for (int i = 0; i < childNodes.getLength(); ++i) {
            Element child = (Element)childNodes.item(i);
            if (hasIDAttribute(child, id)) {
                return child;
            }
        }
        return null;
    }
    
    private Element addNewBean(String id) {
        Element beanElement = document.createElement("bean");
        beanElement.setAttribute("id", id);
        beansElement.appendChild(beanElement);
        return beanElement;
    }
    
    public XMLBeanPropertyBuilder addBean(String id, Class type) {
        Element beanElement = findChildNodeWithID(id);
        if (beanElement == null) {
            beanElement = addNewBean(id);
        }
        beanElement.setAttribute("type", type.getCanonicalName());
        return new XMLBeanPropertyBuilder(document, beanElement);
    }
    
    public <T> XMLBeanWithCurriedConstructor<T> buildCurriedConstructorFunction(Constructor<T> constructor) {
        return new XMLBeanWithCurriedConstructor<T>(this, constructor);
    }

    public Document get() {
        return document;
    }
}
