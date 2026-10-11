package org.checkstyle.suppressionxpathfilter.naming.catchparametername;

public class InputXpathCatchParameterNameAnonymous {
    class InnerClass {
        InnerClass() {
            new Runnable() {
                @Override
                public void run() {
                    try {
                    } catch (Exception e1) {
                    } try {
                    } catch (Exception E1) { // warn
                    }
                }
            };
        }
    }
}
