package org.checkstyle.suppressionxpathfilter.separatorwrap;

interface InputXpathSeparatorWrapInterface {

    public void testMethod1(String...
            parameters);

    public void testMethod2(String
            ...parameters); // warn
}
