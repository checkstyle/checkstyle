package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodExtraThrows {

    /**
     * Extra throws in javadoc it is ok.
     * @param properties some value
     * @throws IllegalArgumentException when argument is wrong // ok
     * @throws NullPointerException indicates null was passed // ok
     */
    public InputJavadocMethodExtraThrows(String properties) {
        if (properties.charAt(0) == 0) {
            throw new IllegalArgumentException("cannot have char with code 0");
        }
        // here is NPE possible
        properties.isEmpty();
    }

    /**
     * Extra throws in javadoc it is ok
     * @param properties some value
     * @throws java.lang.IllegalArgumentException when argument is wrong // ok
     * @throws java.lang.NullPointerException indicates null was passed // ok
     */
    public void doSomething(String properties) throws IllegalArgumentException {
        if (properties.charAt(0) == 0) {
            throw new IllegalArgumentException("cannot have char with code 0");
        }
        // here is NPE possible
        properties.isEmpty();
    }

    /**
     * declared exception in method signature is missed in javadoc // violation
     * @param properties some value
     * @throws java.lang.IllegalArgumentException when argument is wrong // ok
     * @throws java.lang.NullPointerException indicates null was passed // ok
     */
    public void doSomething2(String properties) throws IllegalStateException {
        if (properties.charAt(0) == 0) {
            throw new IllegalArgumentException("cannot have char with code 0");
        }
        // here is NPE possible
        properties.isEmpty();
    }
}
