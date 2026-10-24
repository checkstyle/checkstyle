/*
MethodCount
maxTotal = (default)100
maxPrivate = (default)100
maxPackage = 1
maxProtected = (default)100
maxPublic = (default)100
tokens = (default)CLASS_DEF, ENUM_CONSTANT_DEF, ENUM_DEF, INTERFACE_DEF, \
         ANNOTATION_DEF, METHOD_DEF, RECORD_DEF, COMPACT_COMPILATION_UNIT


*/

package org.checkstyle.suppressionxpathfilter.sizes.methodcount;

class InputXpathMethodCountPackage { // violation 'Number of package methods'
    void foo() {}
    void foo1() {}
}
