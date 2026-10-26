/*
InterfaceIsType
allowMarkerInterfaces = (default)true


*/

package org.checkstyle.suppressionxpathfilter.design.interfaceistype;

public class InputXpathInterfaceIsTypeNested {
    // violation below 'interfaces should describe a type and hence have methods.'
    public interface Foo {
        int VALUE = 1;
    }
}
