//Tested with BCEL-5.1
//http://jakarta.apache.org/builds/jakarta-bcel/release/v5.1/

package com.puppycrawl.tools.checkstyle.bcel;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.EmptyVisitor;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.LocalVariable;
import org.apache.bcel.classfile.LocalVariableTable;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.classfile.Visitor;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;

/**
 * Manages a set of visitors that are accepted by nodes visited by
 * a VisitorSet. Any visit to this object is passed on to its managed
 * visitors.
 * @author Rick Giles
 */
// TODO: review visitXXX
public class VisitorSet
    extends EmptyVisitor
{
    /** the managed visitors */
    private Set mVisitors = new HashSet();

    /** Creates a <code>VisitorSet</code> for a set of visitors.
     * @param aVisitors the set of managed visitors.
     */
    public VisitorSet(Set aVisitors)
    {
        mVisitors = aVisitors;
    }

   /**
     * @see org.apache.bcel.classfile.Visitor#visitCode
     */
    public void visitCode(Code aCode)
    {   
        for (Iterator iter = mVisitors.iterator(); iter.hasNext();) {
            IDeepVisitor visitor = (IDeepVisitor) iter.next();
            Visitor v = visitor.getClassFileVisitor();
            aCode.accept(v);
        }
        
        // perform a deep visit
        final byte[] code = aCode.getCode();
        final InstructionList list = new InstructionList(code);
        final Iterator it = list.iterator();
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            InstructionHandle instruction = (InstructionHandle) iter.next();
            visitInstructionHandle(instruction);
        }
    }

    /**
     * Deep visit of an InstructionHandle
     * @param aInstruction the InstructionHandle
     */
    private void visitInstructionHandle(InstructionHandle aInstruction)
    {
        for (Iterator iter = mVisitors.iterator(); iter.hasNext();) {
            final IDeepVisitor visitor = (IDeepVisitor) iter.next();
            org.apache.bcel.generic.Visitor v =
                visitor.getGenericVisitor();
            aInstruction.accept(v);
        }
    }

    /**
     * @see org.apache.bcel.classfile.Visitor
     */
    public void visitConstantPool(ConstantPool aConstantPool)
    {
        for (Iterator iter = mVisitors.iterator(); iter.hasNext();) {
            IDeepVisitor visitor = (IDeepVisitor) iter.next();
            Visitor v = visitor.getClassFileVisitor();
            aConstantPool.accept(v);
        }
    }

    /**
     * @see org.apache.bcel.classfile.Visitor
     */
    public void visitField(Field aField)
    {
        for (Iterator iter = mVisitors.iterator(); iter.hasNext();) {
            IDeepVisitor visitor = (IDeepVisitor) iter.next();
            Visitor v = visitor.getClassFileVisitor();
            aField.accept(v);
        }
    }

    /**
     * @see org.apache.bcel.classfile.Visitor
     */
    public void visitJavaClass(JavaClass aJavaClass)
    {
        for (Iterator iter = mVisitors.iterator(); iter.hasNext();) {
            IDeepVisitor visitor = (IDeepVisitor) iter.next();
            Visitor v = visitor.getClassFileVisitor();
            aJavaClass.accept(v);
        }
    }

//    /**
//     * @see org.apache.bcel.classfile.Visitor
//     */
//    public void visitLocalVariable(LocalVariable aLocalVariable)
//    {
//        for (Iterator iter = mVisitors.iterator(); iter.hasNext();) {
//            IDeepVisitor visitor = (IDeepVisitor) iter.next();
//            Visitor v = visitor.getClassFileVisitor();
//            aLocalVariable.accept(v);
//        }
//    }
//
    /**
     * @see org.apache.bcel.classfile.Visitor
     */
    public void visitLocalVariableTable(LocalVariableTable aTable)
    {
        for (Iterator iter = mVisitors.iterator(); iter.hasNext();) {
            IDeepVisitor visitor = (IDeepVisitor) iter.next();
            Visitor v = visitor.getClassFileVisitor();
            aTable.accept(v);
        }
    }

    /**
     * @see org.apache.bcel.classfile.Visitor
     */
    public void visitMethod(Method aMethod)
    {
        for (Iterator iter = mVisitors.iterator(); iter.hasNext();) {
            IDeepVisitor visitor = (IDeepVisitor) iter.next();
            Visitor v = visitor.getClassFileVisitor();
            aMethod.accept(v);
        }
    }
}
