//Tested with BCEL-5.1
//http://jakarta.apache.org/builds/jakarta-bcel/release/v5.1/

package com.puppycrawl.tools.checkstyle.bcel;

/**
 * Default classfile visitor.
 * @author Rick Giles
 */
public class EmptyClassFileVisitor
    extends org.apache.bcel.classfile.EmptyVisitor
{
    /** restrict client creation */
    protected EmptyClassFileVisitor()
    {
    }
}