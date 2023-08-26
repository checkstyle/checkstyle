/*
IllegalType
validateAbstractClassNames = (default)false
illegalClassNames = Boolean, Foo, Hashtable, Serializable
legalAbstractClassNames = (default)
ignoredMethodNames = (default)getEnvironment, getInitialContext
illegalAbstractClassNameFormat = (default)^(.*[.])?Abstract.*$
memberModifiers = LITERAL_PUBLIC
tokens = (default)ANNOTATION_FIELD_DEF, CLASS_DEF, INTERFACE_DEF, METHOD_CALL, METHOD_DEF, \
         METHOD_REF, PARAMETER_DEF, VARIABLE_DEF, PATTERN_VARIABLE_DEF, RECORD_DEF, \
         RECORD_COMPONENT_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

import java.io.Serializable;
import java.util.*;

public abstract class InputIllegalTypeTestExtendsImplements {

    public abstract class Bar
        extends Hashtable // violation
            <Boolean, // violation
                Bar> {
    }

    public abstract class Foo<
            T extends Boolean> // violation
        implements Cloneable,
            Serializable, // violation
            Comparator,
            Comparable<Foo< // violation
                ? extends Boolean>> { // violation
    }

    public interface Interface<Foo> // violation
        extends Comparable<Boolean>, // violation
            Serializable { // violation
    }

    abstract class NonPublicBar
            extends Hashtable
            <Boolean,
                Bar> {
    }

    abstract class NonPublicFoo<
            T extends Boolean>
        implements Cloneable,
            Serializable,
            Comparator,
            Comparable<Foo<
                ? extends Boolean>> {
    }

    interface NonPublicInterface<Foo>
        extends Comparable<Boolean>,
            Serializable {
    }

}
