package org.checkstyle.checks.suppressionxpathfilter.catchparametername;

interface InputXpathCatchParameterNameInterface {
    interface InnerInterface {
        default void method() {
            try {
            } catch (Exception EX) { // warn
            }
        }
    }
}
