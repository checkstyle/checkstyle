/*
RedundantModifier
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;


public interface InputRedundantModifierStaticMethodInInterface
{
    static int f()
    {
        int someName = 5;
        return someName;
    }
}
