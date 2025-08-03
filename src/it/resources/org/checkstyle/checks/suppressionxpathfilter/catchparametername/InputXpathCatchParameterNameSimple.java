package org.checkstyle.checks.suppressionxpathfilter.catchparametername;

public class InputXpathCatchParameterNameSimple {
    void method() {
        try {
        } catch (Exception e1) { // warn
        }
    }
}
