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
        extends Hashtable // violation, 'Usage of type Hashtable is not allowed'.
            <Boolean,// violation, 'Usage of type Boolean is not allowed'.
                Bar> {
    }

    public abstract class Foo<
            T extends Boolean> // violation, 'Usage of type Boolean is not allowed'.
        implements Cloneable,
            Serializable, // violation, 'Usage of type Serializable is not allowed'.
            Comparator,
            Comparable<Foo< // violation, 'Usage of type Foo is not allowed'.
                ? extends Boolean>> { // violation, 'Usage of type Boolean is not allowed'.
    }

    public interface Interface<Foo> // violation, 'Usage of type Foo is not allowed'.
        extends Comparable<Boolean>, // violation, 'Usage of type Boolean is not allowed'.
            Serializable { // violation, 'Usage of type Serializable is not allowed'.
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
