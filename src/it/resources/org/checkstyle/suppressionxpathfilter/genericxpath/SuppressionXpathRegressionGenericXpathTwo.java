package org.checkstyle.suppressionxpathfilter.genericxpath;

public class SuppressionXpathRegressionGenericXpathTwo {
    public void func1() throws RuntimeException {} // warning
    public void func2() throws Exception {}  // ok
    public void func5() throws NullPointerException {} // ok
}
