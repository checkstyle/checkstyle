package org.checkstyle.suppressionxpathfilter.coding.illegaltype;

public class InputXpathIllegalTypeInnerClass {
    class Inner {
        public <T extends java.util.HashMap> void typeParam(T t) {} // warn
    }
}
