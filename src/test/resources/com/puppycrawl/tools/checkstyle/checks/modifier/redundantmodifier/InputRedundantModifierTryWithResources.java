/*
RedundantModifier
jdkVersion = (default)22
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE, ANNOTATION_DEF, RECORD_DEF, \
         PATTERN_VARIABLE_DEF, LITERAL_CATCH, LAMBDA


*/


package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class InputRedundantModifierTryWithResources {
    public static void main(String[] args) {
        OutputStreamWriter out = null;
        // violation below 'Redundant 'final' modifier.'
        try (out; final OutputStreamWriter out2 = null;) {
            out.write(1);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
