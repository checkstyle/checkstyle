package org.checkstyle.suppressionxpathfilter.naming.catchparametername;

interface InputXpathCatchParameterNameInterface {
    interface InnerInterface {
        default void method() {
            try {
            } catch (Exception EX) { // warn
            }
        }
    }
}
