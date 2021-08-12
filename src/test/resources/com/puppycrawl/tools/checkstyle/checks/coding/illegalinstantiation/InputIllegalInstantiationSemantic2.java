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

/**
 * Test case for detecting simple semantic violations.
 * @author Lars Kühne
 **/
class InputIllegalInstantiationSemantic2
{
    /* Boolean instantiation in a static initializer */
    static {
        Boolean x = new Boolean(true); // violation
    }

    /* Boolean instantiation in a non-static initializer */
    {
        Boolean x = new Boolean(true); // violation
        Boolean[] y = new Boolean[]{Boolean.TRUE, Boolean.FALSE};
    }

    /** fully qualified Boolean instantiation in a method. **/
    Boolean getBoolean()
    {
        return new java.lang.Boolean(true); // violation
    }

    void otherInstantiations()
    {
        // instantiation of classes in the same package
        Object o1 = new InputBraces();
        Object o2 = new InputModifier(); // violation
        // classes in another package with .* import
        ByteArrayOutputStream s = new ByteArrayOutputStream();
        File f = new File("/tmp"); // violation
        // classes in another package with explicit import
        Dimension dim = new Dimension();
        Color col = new Color(0, 0, 0); // violation
    }

    public class EqualsVsHashCode1
    {
        public boolean equals(int a) // wrong arg type, don't flag
        {
            return a == 1;
        }
    }

    public class EqualsVsHashCode2
    {
        public boolean equals(String a) // don't flag
        {
            return true;
        }
    }

    public class EqualsVsHashCode3
    {
        public boolean equals(Object a) // don't flag
        {
            return true;
        }

        public int hashCode()
        {
            return 0;
        }
    }

    public class EqualsVsHashCode4
    {
        // in anon inner class
        ByteArrayOutputStream bos1 = new ByteArrayOutputStream()
        {
            public boolean equals(Object a) // don't flag
            {
                return true;
            }

            public int hashCode()
            {
                return 0;
            }
        };

        ByteArrayOutputStream bos2 = new ByteArrayOutputStream()
        {
            public boolean equals(Object a) // flag
            {
                return true;
            }
        };
    }

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
}
