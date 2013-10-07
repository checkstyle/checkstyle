//Tested with BCEL-5.1
//http://jakarta.apache.org/builds/jakarta-bcel/release/v5.1/

package com.puppycrawl.tools.checkstyle.bcel.generic;

import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.PUTFIELD;

/**
 * Describe class GETFIELDReference
 * @author Rick Giles
 * @version 18-Jun-2003
 */
public class PUTFIELDReference
    extends FieldReference
{

    /**
     * @param aInstruction
     * @param aPoolGen
     */
    public PUTFIELDReference(
        PUTFIELD aInstruction,
        ConstantPoolGen aPoolGen)
    {
        super(aInstruction, aPoolGen);
    }    
}
