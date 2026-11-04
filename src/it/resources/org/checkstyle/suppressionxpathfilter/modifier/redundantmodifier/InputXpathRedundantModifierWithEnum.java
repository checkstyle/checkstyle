/*
RedundantModifier
jdkVersion = 11
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE, ANNOTATION_DEF, RECORD_DEF, \
         PATTERN_VARIABLE_DEF, LITERAL_CATCH, LAMBDA


*/

package org.checkstyle.suppressionxpathfilter.modifier.redundantmodifier;

public class InputXpathRedundantModifierWithEnum {
    interface I {
        static enum E { // violation 'Redundant 'static' modifier.'
            A, B, C
        }
        void m();
        int x = 0;
  }
}
