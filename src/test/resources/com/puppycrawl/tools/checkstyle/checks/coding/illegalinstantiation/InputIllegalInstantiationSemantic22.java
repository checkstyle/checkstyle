/*
IllegalInstantiation
classes = java.lang.Boolean,com.puppycrawl.tools.checkstyle.checks.coding.illegalinstantiation.\
          InputModifier,java.io.File,java.awt.Color
tokens = (default)CLASS_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalinstantiation;

import java.io.*; // star import for instantiation tests
import java.awt.Dimension; // explicit import for instantiation tests
import java.awt.Color;
import java.util.*;
/**
 * Test case for detecting simple semantic violations.
 * @author Lars Kühne
 **/
public class InputIllegalInstantiationSemantic22
{
    public void triggerEmptyBlockWithoutBlock()
    {
        // an if statement without a block to increase test coverage
        if (true)
            return;
    }

    // empty instance initializer
    {
    }

    public class EqualsVsHashCode5
    {
        public <A> boolean equals(int a) // wrong arg type, don't flag even with generics
        {
            return a == 1;
        }
    }

    public class EqualsVsHashCode6
    {
        public <A> boolean equals(Comparable<A> a) // don't flag
        {
            return true;
        }
    }

    private class InputBraces {

    }

    private class InputModifier {

    }

    private void method() {
        Boolean[] array = new Boolean[3];
        Object object = new @Interned Object();
        Map<Class<?>, Boolean> x = new HashMap<Class<?>, Boolean>();
    }

    @java.lang.annotation.Target(java.lang.annotation.ElementType.TYPE_USE)
    @interface Interned {
    }
}
