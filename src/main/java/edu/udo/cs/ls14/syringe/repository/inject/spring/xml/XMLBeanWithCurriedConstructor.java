/*
 */
package edu.udo.cs.ls14.syringe.repository.inject.spring.xml;

import com.google.common.base.Function;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Provider;

/**
 *
 * @author Jan
 */
public class XMLBeanWithCurriedConstructor<T> {

    private final XMLBeanBuilder builder;
    private final Constructor<T> beanConstructor;

    private static final class CurriedConstructorFunction<T> implements Function {

        private final XMLBeanBuilder builder;
        private final String beanName;
        private final Class<T> beanClass;
        private final List<String> arguments;
        private final int remainingArgumentCount;

        CurriedConstructorFunction(XMLBeanBuilder builder,
                String beanName,
                Class<T> beanClass,
                List<String> arguments,
                int remainingArgumentCount) {
            this.builder = builder;
            this.beanName = beanName;
            this.beanClass = beanClass;
            this.arguments = arguments;
            this.remainingArgumentCount = remainingArgumentCount;
        }

        public Object apply(Object argument) {
            ArrayList<String> newArguments = new ArrayList(arguments);
            newArguments.add((String) argument);

            if (remainingArgumentCount > 1) {
                return new CurriedConstructorFunction(builder,
                        beanName,
                        beanClass,
                        newArguments,
                        remainingArgumentCount - 1);
            }

            XMLBeanPropertyBuilder propertyBuilder = 
                   builder.addBean(beanName, beanClass);
            int argumentNumber = 0;
            for (String currentArgument : newArguments) {
                propertyBuilder.addConstructorArgument(argumentNumber, currentArgument);
                ++argumentNumber;
            }
            return beanName;
        }
    }
    
    private static final class DefaultConstructorProvider<T> implements Provider<String> {
        private final XMLBeanBuilder builder;
        private final String beanName;
        private final Class<T> beanClass;
        
        DefaultConstructorProvider(XMLBeanBuilder builder,
                String beanName,
                Class<T> beanClass) {
            this.builder = builder;
            this.beanName = beanName;
            this.beanClass = beanClass;  
        }
        
        public String get() {
            builder.addBean(beanName, beanClass);
            return beanName;
        }
        
    }

    XMLBeanWithCurriedConstructor(XMLBeanBuilder builder, Constructor<T> beanConstructor) {
        this.builder = builder;
        this.beanConstructor = beanConstructor;
    }

    public Object xmlBeanAdder(String name) {
        Class beanClass = beanConstructor.getDeclaringClass();
        int argumentCount = beanConstructor.getParameterTypes().length;
        if (argumentCount == 0) {
            return new DefaultConstructorProvider(builder, name, beanClass);
        }
        return new CurriedConstructorFunction(builder,
                    name,
                    beanClass,
                    new ArrayList(),
                    argumentCount);
    }
}