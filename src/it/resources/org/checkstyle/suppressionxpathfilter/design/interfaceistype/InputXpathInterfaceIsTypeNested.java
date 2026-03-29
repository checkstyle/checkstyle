package org.checkstyle.suppressionxpathfilter.design.interfaceistype;

public class InputXpathInterfaceIsTypeNested {
    public interface Foo { // warn
        int VALUE = 1;
    }
}
