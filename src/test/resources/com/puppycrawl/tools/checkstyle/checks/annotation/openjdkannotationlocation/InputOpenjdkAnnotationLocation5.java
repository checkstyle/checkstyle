/*
OpenjdkAnnotationLocation
tokens = (default)CLASS_DEF, INTERFACE_DEF, PACKAGE_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.annotation.openjdkannotationlocation;

// violation below, 'Annotation 'MyAnn' must be on a separate line from target.'
@com.puppycrawl.tools.checkstyle.checks.annotation.openjdkannotationlocation.MyAnn public class
        InputOpenjdkAnnotationLocation5 {

}

@com.puppycrawl.tools.checkstyle.checks.annotation.openjdkannotationlocation.MyAnn
class Good {
}

@interface MyAnn {
}

