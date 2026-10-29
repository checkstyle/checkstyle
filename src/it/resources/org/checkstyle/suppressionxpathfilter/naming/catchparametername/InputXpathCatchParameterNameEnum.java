/*
CatchParameterName
format = ^[A-Z][a-z]+$


*/

package org.checkstyle.suppressionxpathfilter.naming.catchparametername;

public enum InputXpathCatchParameterNameEnum {
    VALUE {
        @Override
        public void method(String op) {
            switch (op) {
                case "x":
                    try {
                    // violation below 'Name 'eX' must match pattern '^[A-Z][a-z]+$'.'
                    } catch (Exception eX) {
                    }
                    break;
            }
        }
    };

    public abstract void method(String op);
}
