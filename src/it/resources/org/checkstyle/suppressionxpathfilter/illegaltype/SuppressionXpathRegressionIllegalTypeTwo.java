package org.checkstyle.suppressionxpathfilter.illegaltype;

public class SuppressionXpathRegressionIllegalTypeTwo {
 public static <T extends java.util.HashSet> void method() { // warn
 }
}


