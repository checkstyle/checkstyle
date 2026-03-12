/*
WriteTag
tag = @todo
tagFormat = \\S
target = INTERFACE_DEF, CLASS_DEF, METHOD_DEF, CTOR_DEF
severity = ignore

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

/**
 * Testing tag writing
 * @author Daniel Grenner
 * @incomplete This class needs more code...
 * @doubletag first text
 * @doubletag second text
 * @emptytag
 */
class InputWriteTagMethod implements Comparable<Integer>
{
    // violation 2 lines below 'Javadoc tag @todo=Add a constructor comment'
    /**
     * @todo Add a constructor comment
     */
    public InputWriteTagMethod()
    {
    }

    public void method()
    {
    }

    // violation 2 lines below 'Javadoc tag @todo=Add a comment'
    /**
     * @todo Add a comment
     */
    public void anotherMethod()
    {
    }

    /**
     * Compares to an integer.
     * @param o the object to be compared.
     * @return 0
     */
    @Override
    public int compareTo(Integer o) {
        return 0;
    }
}

