//Tested with BCEL-5.1
//http://jakarta.apache.org/builds/jakarta-bcel/release/v5.1/

package com.puppycrawl.tools.checkstyle.bcel.generic;

import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.FieldOrMethod;
import org.apache.bcel.generic.ObjectType;

/**
 * Describe class InstructionReference
 * @author Rick Giles
 * @version 18-Jun-2003
 */
public class FieldOrMethodReference
{
    protected FieldOrMethod mInstruction;
    
    protected ConstantPoolGen mPoolGen;
    
    protected FieldOrMethodReference(
        FieldOrMethod aInstruction,
        ConstantPoolGen aPoolGen)
    {
        mInstruction = aInstruction;
        mPoolGen = aPoolGen;   
    }

    /**
     * @return
     */
    public FieldOrMethod getInstruction()
    {
        return mInstruction;
    }
    
    public String getClassName()
    {
        return mInstruction.getClassName(mPoolGen);
    }
    
    public ObjectType getClassType()
    {
        return mInstruction.getClassType(mPoolGen);
    }
    
    public ObjectType getLoadClassType()
    {
        return mInstruction.getLoadClassType(mPoolGen);
    }
    
    public String getName()
    {
        return mInstruction.getName(mPoolGen);
    }
    
    public String toString()
    {
        return mInstruction.toString(mPoolGen.getConstantPool());
    }
}
