//Tested with BCEL-5.1
//http://jakarta.apache.org/builds/jakarta-bcel/release/v5.1/

package com.puppycrawl.tools.checkstyle.bcel.checks;

import org.apache.bcel.classfile.Field;

/**
 * Checks for unread, non-final private fields
 * @author Rick Giles
 */
public class UnreadPrivateFieldCheck
    extends UnreadFieldCheck
{
    /** @see UnreadFieldCheck */
    protected boolean ignore(String aClassName, Field aField)
    {
        return (!aField.isPrivate() || super.ignore(aClassName, aField));
    }
}