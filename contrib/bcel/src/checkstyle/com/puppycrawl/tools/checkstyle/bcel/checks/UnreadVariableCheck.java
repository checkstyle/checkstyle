package com.puppycrawl.tools.checkstyle.bcel.checks;

import java.io.File;
import java.util.BitSet;
import java.util.Set;

import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.LocalVariable;
import org.apache.bcel.classfile.LocalVariableTable;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.LoadInstruction;

import com.puppycrawl.tools.checkstyle.bcel.AbstractCheckVisitor;
import com.puppycrawl.tools.checkstyle.bcel.EmptyClassFileVisitor;
import com.puppycrawl.tools.checkstyle.bcel.EmptyDeepVisitor;
import com.puppycrawl.tools.checkstyle.bcel.EmptyGenericVisitor;
import com.puppycrawl.tools.checkstyle.bcel.IDeepVisitor;

/**
 * Checks for unread variables, i.e. unread formal parameters and local
 * variables.
 * Each class file must have a LocalVariableTable.
 * To generate a LocalVariableTable, compile with debug information,
 * as with option -g.
 * @author Rick Giles
 */
public class UnreadVariableCheck
    extends AbstractCheckVisitor
{
    private final DeepVisitor mDeepVisitor = new DeepVisitor();
    
    private final org.apache.bcel.classfile.Visitor mClassFileVisitor =
        new ClassFileVisitor();
    
    private final org.apache.bcel.generic.Visitor mGenericVisitor =
        new GenericVisitor();

    private JavaClass mCurrentJavaClass = null;
       
    private Method mCurrentMethod = null;
        
    private LocalVariableTable mLocalVariableTable = null;
    
    private final BitSet mLocalVariableBitSet = new BitSet();
    
    private class DeepVisitor
        extends EmptyDeepVisitor
    {
        public org.apache.bcel.classfile.Visitor getClassFileVisitor()
        {
            return mClassFileVisitor;
        }
        
        public org.apache.bcel.generic.Visitor getGenericVisitor()
        {
            return mGenericVisitor;
        }

        public void visitObject(Object aObj)
        {
            mCurrentJavaClass = (JavaClass) aObj;      
        }
         
        public void leaveSet(Set aSet)
        {
            checkReferences();   
        }
    }
    
    private class GenericVisitor
        extends EmptyGenericVisitor
    {
        public void visitLoadInstruction(LoadInstruction aInstruction)
        {
            mLocalVariableBitSet.set(aInstruction.getIndex());
        }
    }
    
    private class ClassFileVisitor
        extends EmptyClassFileVisitor
    {
        public void visitMethod(Method aMethod)
        {
            
//          check references from previous method
                     checkReferences();
            mCurrentMethod = aMethod;
                     // update reference recording
                     
                     mLocalVariableTable = aMethod.getLocalVariableTable();
                     mLocalVariableBitSet.clear();
        }

        public void visitLocalVariableTable(LocalVariableTable aTable)
        {

  
        }
    }
    
     private void checkReferences()
     {
        if (mLocalVariableTable != null) {
           for (int i = 0; i < mLocalVariableTable.getLength(); i++) {
                if (!mLocalVariableBitSet.get(i)) {
                    final LocalVariable localVariable =
                        mLocalVariableTable.getLocalVariable(i);
                    if (localVariable != null
                        && !localVariable.getName().equals("this"))
                    {
                        log(0,
                            "unread.variable",
                            new Object[] {
                                mCurrentJavaClass.getClassName(),
                                mCurrentMethod.getName(),
                                localVariable.getName(),});
                    }
                }
            }
        }
     }

    
    /** @see com.puppycrawl.tools.checkstyle.bcel.AbstractCheckVisitor */
    public IDeepVisitor getVisitor()
    {
        return mDeepVisitor;
    }
}
