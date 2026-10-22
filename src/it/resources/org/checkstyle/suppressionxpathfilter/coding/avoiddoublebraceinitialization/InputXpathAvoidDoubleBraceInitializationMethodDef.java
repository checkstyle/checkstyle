/*
AvoidDoubleBraceInitialization


*/

package org.checkstyle.suppressionxpathfilter.coding.avoiddoublebraceinitialization;

import java.util.HashSet;

public class InputXpathAvoidDoubleBraceInitializationMethodDef {
    public void test() {
        new HashSet<String>() {{ // violation 'Avoid double brace initialization.'
           add("foo");
           add("bar");
        }};
    }

    enum InnerEnum {
        ;
        {}
    }
}
