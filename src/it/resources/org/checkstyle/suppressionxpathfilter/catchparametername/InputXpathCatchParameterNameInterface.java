package org.checkstyle.suppressionxpathfilter.catchparametername;

interface InputXpathCatchParameterNameInterface {
    interface InnerInterface {
        default void method() {
            try {
            } catch (Exception EX) { // warn
            }
        }
    }
}
