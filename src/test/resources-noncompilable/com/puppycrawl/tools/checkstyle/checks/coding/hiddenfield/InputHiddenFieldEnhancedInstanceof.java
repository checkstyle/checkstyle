//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

import java.util.Locale;
// config:
// tokens="PATTERN_VARIABLE_DEF"
public class InputHiddenFieldEnhancedInstanceof {

    public class Keyboard {
        private String model = null;
        private final int price = 2;

        // Pattern variable price hides field price
        public boolean doStuff(Float f) { // violation
            return f instanceof Float price &&
                    price.floatValue() > 0 &&
                    model != null &&
                    price.intValue() == 5;
        }
    }

    static final Object OBJ = "";
    static String hiddenStaticField = "hiddenStaticField";
    static {
        // Pattern variable hiddenStaticField hides static field hiddenStaticField
        if (OBJ instanceof String hiddenStaticField) { //violation
            System.out.println(hiddenStaticField
                    .toLowerCase(Locale.forLanguageTag(hiddenStaticField)));
            boolean stringCheck = "test".equals(hiddenStaticField);
        }
    }
}
