//Tested with BCEL-5.1
//http://jakarta.apache.org/builds/jakarta-bcel/release/v5.1/

package com.puppycrawl.tools.checkstyle.bcel;

/**
 * Default generic visitor
 * @author Rick Giles
 */
public class EmptyGenericVisitor
    extends org.apache.bcel.generic.EmptyVisitor
{
    /** Restrict client creation */
    protected EmptyGenericVisitor()
    {
    }
}

