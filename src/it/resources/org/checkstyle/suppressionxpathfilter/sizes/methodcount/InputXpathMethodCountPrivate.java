/*
MethodCount
maxTotal = (default)100
maxPrivate = 1
maxPackage = (default)100
maxProtected = (default)100
maxPublic = (default)100
tokens = (default)CLASS_DEF, ENUM_CONSTANT_DEF, ENUM_DEF, INTERFACE_DEF, \
         ANNOTATION_DEF, METHOD_DEF, RECORD_DEF, COMPACT_COMPILATION_UNIT


*/

package org.checkstyle.suppressionxpathfilter.sizes.methodcount;

class InputXpathMethodCountPrivate { // violation 'Number of private methods'
    private void foo() {}
    private void foo1() {}
}
