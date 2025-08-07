package org.checkstyle.suppressionxpathfilter.coding.illegaltype;

public class InputXpathIllegalTypeOne {
    public <T extends java.util.HashSet> void typeParam(T t) {} // warn
}
