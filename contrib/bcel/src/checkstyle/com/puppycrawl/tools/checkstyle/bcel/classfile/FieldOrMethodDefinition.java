//Tested with BCEL-5.1
//http://jakarta.apache.org/builds/jakarta-bcel/release/v5.1/

package com.puppycrawl.tools.checkstyle.bcel.classfile;

import org.apache.bcel.classfile.FieldOrMethod;

/**
 * Contains the definition of a Field or a Method.
 * @author Rick Giles
 */
public class FieldOrMethodDefinition
{
    /** the Field or Method */
    private FieldOrMethod mFieldOrMethod;

    /**
     * Constructs a <code>FieldOrMethodDefinition</code> for a Field
     * or a Method.
     * @param aFieldOrMethod the Field or Method
     */
    protected FieldOrMethodDefinition(FieldOrMethod aFieldOrMethod)
    {
        mFieldOrMethod = aFieldOrMethod;
    }

    /**
     * Returns the FieldOrMethod.
     * @return the FieldOrMethod.
     */
    protected FieldOrMethod getFieldOrMethod()
    {
        return mFieldOrMethod;
    }

    /**
     * Returns the name of the Field or Method.
     * @return the name of the Field or Method.
     */
    public String getName()
    {
        return mFieldOrMethod.getName();
    }

    /** @see java.lang.Object#toString() */
    public String toString()
    {
        return mFieldOrMethod.toString();
    }
}
