package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

import java.io.Serializable;
import java.util.*;

/*
 * Config:
 * illegalClassNames = { Boolean, Foo, Hashtable, Serializable }
 * memberModifiers = { LITERAL_PUBLIC }
 */
public abstract class InputIllegalTypeTestExtendsImplements {

    public abstract class Bar
        extends Hashtable // violation
            <Boolean, // violation
                Bar> { // OK
    }

    public abstract class Foo<
            T extends Boolean> // violation
        implements Cloneable, // OK
            Serializable, // violation
            Comparator, // OK
            Comparable<Foo< // violation
                ? extends Boolean>> { // violation
    }

    public interface Interface<Foo> // violation
        extends Comparable<Boolean>, // violation
            Serializable { // violation
    }

    abstract class NonPublicBar
            extends Hashtable // OK
            <Boolean, // OK
                Bar> { // OK
    }

    abstract class NonPublicFoo<
            T extends Boolean> // OK
        implements Cloneable, // OK
            Serializable, // OK
            Comparator, // OK
            Comparable<Foo< // OK
                ? extends Boolean>> { // OK
    }

    interface NonPublicInterface<Foo>
        extends Comparable<Boolean>, // OK
            Serializable { // OK
    }

}
