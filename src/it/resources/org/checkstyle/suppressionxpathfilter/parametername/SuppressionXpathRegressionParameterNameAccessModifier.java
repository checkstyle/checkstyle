package org.checkstyle.suppressionxpathfilter.parametername;

class SuppressionXpathRegressionParameterNameAccessModifier {

    private void method1(int a) { // ok
    }

    public void method2(int b) { // warn
    }
}
