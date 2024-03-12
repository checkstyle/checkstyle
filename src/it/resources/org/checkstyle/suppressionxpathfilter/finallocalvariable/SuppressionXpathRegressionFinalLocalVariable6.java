package org.checkstyle.suppressionxpathfilter.finallocalvariable;

public class SuppressionXpathRegressionFinalLocalVariable6 {
    public void method1()
    {
        final java.util.List<Object> list = new java.util.ArrayList<>();

        for(Object a : list){ // warn
        }
    }
}
