//Tested with BCEL-5.1
//http://jakarta.apache.org/builds/jakarta-bcel/release/v5.1/

package com.puppycrawl.tools.checkstyle.bcel.generic;

import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.PUTSTATIC;

/**
 * Describe class GETSTATICReference
 * @author Rick Giles
 * @version 18-Jun-2003
 */
public class PUTSTATICReference
    extends FieldReference
{

    /**
     * @param aInstruction
     * @param aPoolGen
     */
    public PUTSTATICReference(
        PUTSTATIC aInstruction,
        ConstantPoolGen aPoolGen)
    {
        super(aInstruction, aPoolGen);
    }    
}
