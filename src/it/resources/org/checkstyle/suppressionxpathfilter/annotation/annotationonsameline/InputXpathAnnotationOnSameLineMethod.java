/*
AnnotationOnSameLine
tokens = CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, CTOR_DEF, \
         VARIABLE_DEF, PARAMETER_DEF, ANNOTATION_DEF, TYPECAST, LITERAL_THROWS, \
         IMPLEMENTS_CLAUSE, TYPE_ARGUMENT, LITERAL_NEW, DOT, ANNOTATION_FIELD_DEF


*/

package org.checkstyle.suppressionxpathfilter.annotation.annotationonsameline;

public class InputXpathAnnotationOnSameLineMethod {
    @Deprecated int x;

    @Deprecated
    // violation above 'Annotation 'Deprecated' should be on the same line with its target.'
    public int getX() {
        return (int) x;
    }
}
