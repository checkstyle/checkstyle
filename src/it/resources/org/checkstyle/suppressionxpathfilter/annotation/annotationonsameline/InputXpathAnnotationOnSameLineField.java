/*
AnnotationOnSameLine
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, CTOR_DEF, \
         VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

package org.checkstyle.suppressionxpathfilter.annotation.annotationonsameline;

import java.util.ArrayList;
import java.util.List;

public class InputXpathAnnotationOnSameLineField {
    @Deprecated
    // violation above 'Annotation 'Deprecated' should be on the same line with its target.'
    private List<String> names = new ArrayList<>();
}
