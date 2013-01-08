/*
 */
package edu.udo.cs.ls14.syringe.repository.inject.spring;

import edu.udo.cs.ls14.syringe.repository.inject.spring.xml.XMLBeanBuilder;
import java.lang.reflect.Constructor;
import java.util.Map;
import javax.inject.Provider;

/**
 *
 * @author Jan
 */
public class SpringBeanInjectionRepository implements Provider<Map<String, Object>> {
    private final XMLBeanBuilder builder;
    private final Map<String, Object> context;
    
    public SpringBeanInjectionRepository(XMLBeanBuilder builder, Map<String, Object> context) {
        this.builder = builder;
        this.context = context;
    }
    
    public <T> void registerBeanConstructor(Constructor<T> beanConstructor, String as) {
        Object constructorFunction = builder
                .buildCurriedConstructorFunction(beanConstructor)
                .xmlBeanAdder(as);
        context.put(as, constructorFunction);
        
    }
    
    public Map<String, Object> get() {
        return context;
    }
}
