//Tested with BCEL-5.1
//http://jakarta.apache.org/builds/jakarta-bcel/release/v5.1/

package com.puppycrawl.tools.checkstyle.bcel.generic;

import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.GETFIELD;

/**
 * Describe class GETFIELDReference
 * @author Rick Giles
 * @version 18-Jun-2003
 */
public class GETFIELDReference
    extends FieldReference
{

    /**
     * @param aInstruction
     * @param aPoolGen
     */
    public GETFIELDReference(
        GETFIELD aInstruction,
        ConstantPoolGen aPoolGen)
    {
        super(aInstruction, aPoolGen);
    }    
}
