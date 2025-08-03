package org.checkstyle.checks.suppressionxpathfilter.illegaltype;

public class InputXpathIllegalTypeOne {
    public <T extends java.util.HashSet> void typeParam(T t) {} // warn
}
