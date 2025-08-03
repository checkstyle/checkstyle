package org.checkstyle.checks.suppressionxpathfilter.parametername;

class InputXpathParameterNameAccessModifier {

    private void method1(int a) { // ok
    }

    public void method2(int b) { // warn
    }
}
