//Tested with BCEL-5.1
//http://jakarta.apache.org/builds/jakarta-bcel/release/v5.1/

package com.puppycrawl.tools.checkstyle.bcel.generic;

import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.InvokeInstruction;
import org.apache.bcel.generic.Type;


/**
 * Describe class MethodReference
 * @author Rick Giles
 * @version 18-Jun-2003
 */
public class InvokeReference
    extends FieldOrMethodReference
{

    /**
     * @param aInstruction
     * @param aPoolGen
     */
    public InvokeReference(
        InvokeInstruction aInstruction,
        ConstantPoolGen aPoolGen)
    {
        super(aInstruction, aPoolGen);
    }

    /**
     * @return
     */
    public Type[] getArgTypes()
    {
        return ((InvokeInstruction) mInstruction).getArgumentTypes(mPoolGen);
    }
}
