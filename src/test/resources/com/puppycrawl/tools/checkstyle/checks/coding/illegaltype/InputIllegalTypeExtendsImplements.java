package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

import java.io.Serializable;
import java.util.*;

/**
 * This test-input is intended to be checked using following configuration:
 *
 * illegalClassNames = {Boolean, Foo, Hashtable, Serializable}
 * memberModifiers = {LITERAL_PUBLIC}
 *
 */
public abstract class InputIllegalTypeExtendsImplements {

    public abstract class Bar
        extends Hashtable // warn
            <Boolean, // warn
                Bar> { // OK
    }

    public abstract class Foo<
            T extends Boolean> // warn
        implements Cloneable, // OK
            Serializable, // warn
            Comparator, // OK
            Comparable<Foo< // warn
                ? extends Boolean>> { // warn
    }

    public interface Interface<Foo>
        extends Comparable<Boolean>, // warn
            Serializable { // warn
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
