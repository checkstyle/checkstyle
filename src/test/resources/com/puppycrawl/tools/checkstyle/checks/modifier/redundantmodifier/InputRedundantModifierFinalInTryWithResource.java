/*
RedundantModifier
jdkVersion = (default)22
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE, ANNOTATION_DEF, RECORD_DEF, \
         PATTERN_VARIABLE_DEF, LITERAL_CATCH, LAMBDA

*/

package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class InputRedundantModifierFinalInTryWithResource {
    private static InputStreamReader streamm;

    public InputRedundantModifierFinalInTryWithResource() throws UnsupportedEncodingException {
        streamm = new InputStreamReader(null, "utf");
    }

    public static void test() {
        try {
        }
        catch (RuntimeException e) {
        }

        try (@NotNull BufferedReader br =
                     new BufferedReader(streamm)) {
        }
        catch (IOException e) {
        }

        // violation below 'Redundant 'final' modifier.'
        try (final BufferedReader br =  new BufferedReader(streamm)) {
        }
        catch (IOException e) {
        }

        // violation below 'Redundant 'final' modifier.'
        try (final BufferedReader br =  new BufferedReader(streamm);
                // violation below 'Redundant 'final' modifier.'
                final BufferedReader br2 = new BufferedReader(streamm)) {
        }
        catch (IOException e) {
        }
    }
}

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
@interface NotNull {
}
