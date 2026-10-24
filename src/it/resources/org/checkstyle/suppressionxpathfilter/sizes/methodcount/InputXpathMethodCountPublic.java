/*
MethodCount
maxTotal = (default)100
maxPrivate = (default)100
maxPackage = (default)100
maxProtected = (default)100
maxPublic = 1
tokens = (default)CLASS_DEF, ENUM_CONSTANT_DEF, ENUM_DEF, INTERFACE_DEF, \
         ANNOTATION_DEF, METHOD_DEF, RECORD_DEF, COMPACT_COMPILATION_UNIT


*/

package org.checkstyle.suppressionxpathfilter.sizes.methodcount;

class InputXpathMethodCountPublic { // violation 'Number of public methods'
    public void foo() {}
    public void foo1() {}
}
