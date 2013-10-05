//Tested with BCEL-5.1
//http://jakarta.apache.org/builds/jakarta-bcel/release/v5.1/

package com.puppycrawl.tools.checkstyle.bcel;

/**
 * Deep visitor for classfile and generic traversals. A ClassFile traversal
 * visits all classfile and all generic nodes.
 * @author Rick Giles
 */
public interface IDeepVisitor
    extends IObjectSetVisitor
{
    /**
     * Returns the classfile visitor.
     * @return the classfile visitor.
     */
    org.apache.bcel.classfile.Visitor getClassFileVisitor();

    /**
     * Returns the generic visitor.
     * @return the generic visitor.
     */
    org.apache.bcel.generic.Visitor getGenericVisitor();
}
