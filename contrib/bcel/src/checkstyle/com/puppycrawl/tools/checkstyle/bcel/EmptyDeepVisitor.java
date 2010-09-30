//Tested with BCEL-5.1
//http://jakarta.apache.org/builds/jakarta-bcel/release/v5.1/

package com.puppycrawl.tools.checkstyle.bcel;

import java.util.Set;


/**
 * Default deep visitor
 * @author Rick Giles
 * @version 17-Jun-2003
 */
public class EmptyDeepVisitor
    implements IDeepVisitor
{
    /** the classfile visitor */
    private org.apache.bcel.classfile.Visitor mClassFileVisitor =
        new EmptyClassFileVisitor();

    /** the generic visitor */
    private org.apache.bcel.generic.Visitor mGenericVisitor =
        new EmptyGenericVisitor();

    /**
     * @see com.puppycrawl.tools.checkstyle.bcel.IDeepVisitor
     */
    public org.apache.bcel.classfile.Visitor getClassFileVisitor()
    {
        return mClassFileVisitor;
    }

    /**
     * @see com.puppycrawl.tools.checkstyle.bcel.IDeepVisitor
     */
    public org.apache.bcel.generic.Visitor getGenericVisitor()
    {
        return mGenericVisitor;
    }

    /**
     * Sets the classfile visitor.
     * @param aVisitor the classfile visitor.
     */
    public void setClassFileVisitor(org.apache.bcel.classfile.Visitor aVisitor)
    {
        mClassFileVisitor = aVisitor;
    }

    /**
     * Sets the generic visitor.
     * @param aVisitor the generic visitor.
     */
    public void setGenericVisitor(org.apache.bcel.generic.Visitor aVisitor)
    {
        mGenericVisitor = aVisitor;
    }

    /**
     * @see com.puppycrawl.tools.checkstyle.bcel.IObjectSetVisitor
     */
    public void visitObject(Object aObject)
    {
    }

    /**
     * @see com.puppycrawl.tools.checkstyle.bcel.IObjectSetVisitor
     */
    public void leaveObject(Object aObject)
    {
    }

    /**
     * @see com.puppycrawl.tools.checkstyle.bcel.IObjectSetVisitor
     */
    public void visitSet(Set aSet)
    {
    }

    /**
     * @see com.puppycrawl.tools.checkstyle.bcel.IObjectSetVisitor
     */
    public void leaveSet(Set aSet)
    {
    }
}
