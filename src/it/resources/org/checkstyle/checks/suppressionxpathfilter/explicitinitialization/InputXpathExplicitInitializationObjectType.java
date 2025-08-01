package org.checkstyle.checks.suppressionxpathfilter.explicitinitialization;

public class InputXpathExplicitInitializationObjectType {
    private int a;

    private Object bar = null; //warn
}
