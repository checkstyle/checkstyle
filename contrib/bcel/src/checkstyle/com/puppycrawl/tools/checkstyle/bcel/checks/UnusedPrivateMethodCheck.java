//Tested with BCEL-5.1
//http://jakarta.apache.org/builds/jakarta-bcel/release/v5.1/

package com.puppycrawl.tools.checkstyle.bcel.checks;

import org.apache.bcel.classfile.Method;

/**
 * Checks for unused private methods
 * @author Rick Giles
 */
public class UnusedPrivateMethodCheck
    extends UnusedMethodCheck
{
    /** @see UnusedMethodCheck */
    protected boolean ignore(String aClassName, Method aMethod)
    {
        return (!aMethod.isPrivate() || super.ignore(aClassName, aMethod));
    }
}