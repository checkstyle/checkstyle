package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

/**
 * Config: default
 */
public class InputJavadocMethodLoadErrors
{
    /**
     * aasdf
     * @throws InvalidExceptionName exception that cannot be loaded
     */
    void method() {} // ok
}
