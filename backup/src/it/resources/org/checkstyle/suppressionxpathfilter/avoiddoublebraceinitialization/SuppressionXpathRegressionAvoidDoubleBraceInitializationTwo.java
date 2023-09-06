package org.checkstyle.suppressionxpathfilter.avoiddoublebraceinitialization;

import java.util.HashSet;

public class SuppressionXpathRegressionAvoidDoubleBraceInitializationTwo {
    public void test() {
        new HashSet<String>() {{ /** warn */
           add("foo");
           add("bar");
        }};
    }

    enum InnerEnum {
        ;
        {}
    }
}
