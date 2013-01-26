/*
 */
package edu.udo.cs.ls14.syringe.repository.inject.spring.xml;

import com.google.common.base.Function;
import edu.udo.cs.ls14.syringe.interpreter.TypeError;
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
        private final List<DefaultConstructorProvider> arguments;
        private final int remainingArgumentCount;
        private final Constructor beanConstructor;

        CurriedConstructorFunction(XMLBeanBuilder builder,
                String beanName,
                Class<T> beanClass,
                List<DefaultConstructorProvider> arguments,
                int remainingArgumentCount,
                Constructor beanConstructor) {
            this.builder = builder;
            this.beanName = beanName;
            this.beanClass = beanClass;
            this.arguments = arguments;
            this.remainingArgumentCount = remainingArgumentCount;
            this.beanConstructor = beanConstructor;
        }

        public Object apply(Object argument) {
            ArrayList<DefaultConstructorProvider> newArguments = new ArrayList(arguments);
            if (!(argument instanceof DefaultConstructorProvider)) {
                throw new TypeError(beanConstructor.getParameterTypes()[beanConstructor.getParameterTypes().length - remainingArgumentCount], argument);
            }
            newArguments.add((DefaultConstructorProvider)argument);

            if (remainingArgumentCount > 1) {
                return new CurriedConstructorFunction(builder,
                        beanName,
                        beanClass,
                        newArguments,
                        remainingArgumentCount - 1,
                        beanConstructor);
            }
            
            return new DefaultConstructorProvider(builder, beanName, beanClass, newArguments);
        }
        
        @Override
        public String toString() {
            StringBuilder typeBuilder = new StringBuilder();
            for (Class type : beanConstructor.getParameterTypes()) {
                typeBuilder.append(type.getSimpleName());
                typeBuilder.append(" -> ");
            }
            typeBuilder.append(beanClass.getSimpleName());
            return String.format("%s : %s", beanName, typeBuilder.toString());
        }
        
    }
    
    private static final class DefaultConstructorProvider<T> implements Provider<String> {
        private final XMLBeanBuilder builder;
        private final String beanName;
        private final Class<T> beanClass;
        private final ArrayList<DefaultConstructorProvider> defaultArguments;
        private boolean added = false;
        
        DefaultConstructorProvider(XMLBeanBuilder builder,
                String beanName,
                Class<T> beanClass,
                ArrayList<DefaultConstructorProvider> defaultArguments) {
            this.builder = builder;
            this.beanName = beanName;
            this.beanClass = beanClass; 
            this.defaultArguments = defaultArguments;
        }
        
        public String get() {
            if (added) return beanName;
            
            
            XMLBeanPropertyBuilder propertyBuilder = builder.addBean(beanName, beanClass);
            int argumentNumber = 0;
            for (DefaultConstructorProvider currentArgument : defaultArguments) {
                currentArgument.get();
                propertyBuilder.addConstructorArgument(argumentNumber, currentArgument.beanName);
                ++argumentNumber;
            }
            added = true;
            return beanName;
        }
        
        @Override
        public String toString() {
            return String.format("%s : %s", beanName, beanClass.getSimpleName());
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
            return new DefaultConstructorProvider(builder, name, beanClass, new ArrayList<DefaultConstructorProvider>());
        }
        return new CurriedConstructorFunction(builder,
                    name,
                    beanClass,
                    new ArrayList(),
                    argumentCount,
                    beanConstructor);
    }
}