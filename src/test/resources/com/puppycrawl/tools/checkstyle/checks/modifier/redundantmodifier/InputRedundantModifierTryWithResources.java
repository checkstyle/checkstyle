/*
RedundantModifier
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE
jdkVersion = (default)22

*/


package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class InputRedundantModifierTryWithResources {
    public static void main(String[] args) {
        OutputStreamWriter out = null;
        try (out; final OutputStreamWriter out2 = null;) { // violation
            out.write(1);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
