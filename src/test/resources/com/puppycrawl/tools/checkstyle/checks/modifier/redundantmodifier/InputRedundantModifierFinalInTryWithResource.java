/*
RedundantModifier
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class InputRedundantModifierFinalInTryWithResource {
    public static void test() {
        try {
        }
        catch (RuntimeException e) {
        }

        try (@NotNull BufferedReader br = new BufferedReader(new InputStreamReader(null, "utf"))) {
        }
        catch (IOException e) {
        }

        try (final BufferedReader br = new BufferedReader(new InputStreamReader(null, "utf-8"))) { // violation
        }
        catch (IOException e) {
        }

        try (final BufferedReader br = new BufferedReader(new InputStreamReader(null, "utf-8")); // violation
                final BufferedReader br2 = new BufferedReader(new InputStreamReader(null, "utf"))) { // violation
        }
        catch (IOException e) {
        }
    }
}

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
@interface NotNull {
}
