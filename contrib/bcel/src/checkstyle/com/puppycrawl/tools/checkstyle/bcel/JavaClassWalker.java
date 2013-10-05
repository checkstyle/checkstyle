//Tested with BCEL-5.1
//http://jakarta.apache.org/builds/jakarta-bcel/release/v5.1/

package com.puppycrawl.tools.checkstyle.bcel;

import org.apache.bcel.classfile.DescendingVisitor;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Visitor;

/**
 * Walks a JavaClass parse tree.
 * @author Rick Giles
 * @version 15-Jun-2003
 */
public final class JavaClassWalker
{
    /** visitor to be accepted during a traversal */
    private Visitor mVisitor = new EmptyClassFileVisitor();

    /**
     * Sets a visitor to be accepted during a traversal.
     * @param aVisitor the visitor to be accepted during a traversal.
     */
    public void setVisitor(Visitor aVisitor)
    {
        mVisitor = aVisitor;
    }

    /**
     * Traverses a JavaClass parse tree and accepts all registered
     * visitors.
     * @param aJavaClass the root of the tree.
     */
    public void walk(JavaClass aJavaClass)
    {
        DescendingVisitor visitor =
            new DescendingVisitor(aJavaClass, mVisitor);
        aJavaClass.accept(visitor);
    }
}
