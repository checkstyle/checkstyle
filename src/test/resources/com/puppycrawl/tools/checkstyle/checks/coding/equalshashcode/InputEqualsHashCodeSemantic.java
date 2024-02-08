/*
EqualsHashCode


*/

package com.puppycrawl.tools.checkstyle.checks.coding.equalshashcode;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Test case for detecting simple semantic violations.
 * @author Lars Kühne
 **/
class InputEqualsHashCodeSemantic
{
    /* Boolean instantiation in a static initializer */
    static {
        Boolean x = new Boolean(true);
    }

    /* Boolean instantiation in a non-static initializer */
    {
        Boolean x = new Boolean(true);
        Boolean[] y = new Boolean[]{Boolean.TRUE, Boolean.FALSE};
    }

    /** fully qualified Boolean instantiation in a method. **/
    Boolean getBoolean()
    {
        return new Boolean(true);
    }

    void otherInstantiations()
    {
        // instantiation of classes in the same package
        Object o1 = new InputBraces();
        Object o2 = new InputModifier();
        // classes in another package with .* import
        ByteArrayOutputStream s = new ByteArrayOutputStream();
        File f = new File("/tmp");
        // classes in another package with explicit import
        Dimension dim = new Dimension();
        Color col = new Color(0, 0, 0);
    }

    public class EqualsVsHashCode1
    {
        public boolean equals(int a) // wrong arg type, ok
        {
            return a == 1;
        }
    }

    public class EqualsVsHashCode2
    {
        public boolean equals(String a)
        {
            return true;
        }
    }

    public class EqualsVsHashCode3
    {
        public boolean equals(Object a)
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
            public boolean equals(Object a)
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
            public boolean equals(Object a) // violation 'without .* of 'hashCode()'.'
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
        public <A> boolean equals(int a) // wrong arg type, ok even with generics
        {
            return a == 1;
        }
    }

    public class EqualsVsHashCode6
    {
        public <A> boolean equals(Comparable<A> a)
        {
            return true;
        }
    }

    private class InputBraces {

    }

    private class InputModifier {

    }
}
