////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2002  Oliver Burn
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.GETFIELD;
import org.apache.bcel.generic.PUTFIELD;
import org.apache.bcel.generic.GETSTATIC;
import org.apache.bcel.generic.PUTSTATIC;
import org.apache.bcel.generic.FieldInstruction;

import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Used to locate unused constructs using BCEL.
 * A field is considered if:
 * <ul>
 *   <li>It is private as another class can access it</li>
 *   <li>It is not final as the compiler can optimise these away</li>
 * </ul>
 *
 * @author <a href="mailto:checkstyle@puppycrawl.com">Oliver Burn</a>
 * @version 1.0
 */
class UnusedDetector
    extends org.apache.bcel.classfile.EmptyVisitor
{
    /** the class being examined */
    private final JavaClass mJavaClass;
    /** the found field statistics */
    private final Map mFieldStats = new HashMap();
    /** a BCEL pool */
    private final ConstantPoolGen mPoolGen;
    /** a BCEL pool */
    private final ConstantPool mPool;
    /** used to process the instructions */
    private final InstructionVisitor mIV = new InstructionVisitor();

    /**
     * Creates a new <code>UnusedDetector</code> instance.
     *
     * @param aJavaClass a <code>JavaClass</code> value
     */
    UnusedDetector(JavaClass aJavaClass)
    {
        mJavaClass = aJavaClass;
        mPool = aJavaClass.getConstantPool();
        mPoolGen = new ConstantPoolGen(mPool);
    }

    /** @return the names of all unusued fields */
    String[] getUnusedFields()
    {
        final ArrayList unused = new ArrayList();
        final Iterator it = mFieldStats.values().iterator();
        while (it.hasNext()) {
            final FieldStats fs = (FieldStats) it.next();
            final Field details = fs.getDetails();
            if (details.isPrivate()
                && !details.isFinal()
                && !"this$0".equals(fs.getName()))
            {
                if (fs.getReads() == 0) {
                    unused.add(fs.getName());
                }
            }
        }

        return (String[]) unused.toArray(new String[unused.size()]);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Classfile Visitor methods
    ///////////////////////////////////////////////////////////////////////////

    /** @see org.apache.bcel.classfile.Visitor */
    public void visitField(Field aField)
    {
        reportField(aField);
    }

    /** @see org.apache.bcel.classfile.Visitor */
    public void visitCode(Code aCode)
    {
        // Process all the instructions
        final InstructionList list = new InstructionList(aCode.getCode());
        for (InstructionHandle handle = list.getStart();
             handle != null;
             handle = handle.getNext())
        {
            handle.getInstruction().accept(mIV);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Private methods
    ///////////////////////////////////////////////////////////////////////////

    /**
     * @param aField report the field found
     */
    private void reportField(Field aField)
    {
        final FieldStats fs = findFieldStats(aField.getName());
        fs.setDetails(aField);
    }

    /** @param aFI report the field is read */
    private void reportRead(FieldInstruction aFI)
    {
        if (memberOfClass(aFI)) {
            findFieldStats(aFI.getName(mPoolGen)).addRead();
        }
    }

    /** @param aFI report the field is written to */
    private void reportWrite(FieldInstruction aFI)
    {
        if (memberOfClass(aFI)) {
            findFieldStats(aFI.getName(mPoolGen)).addWrite();
        }
    }

    /**
     * @return whether the field is from this class
     * @param aFI the field to check
     */
    private boolean memberOfClass(FieldInstruction aFI)
    {
        return mJavaClass.getClassName().equals(
            aFI.getClassType(mPoolGen).getClassName());
    }

    /**
     * @return the FieldStats for the specifed name. If one does not exist, it
     *         is created
     * @param aName the field name
     */
    private FieldStats findFieldStats(String aName)
    {
        FieldStats retVal = (FieldStats) mFieldStats.get(aName);
        if (retVal == null) {
            retVal = new FieldStats(aName);
            mFieldStats.put(aName, retVal);
        }
        return retVal;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Internal types
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Helper class for visiting all the Class instructions. Looks for access
     * to fields.
     *
     */
    private class InstructionVisitor
        extends org.apache.bcel.generic.EmptyVisitor
    {
        /** @see org.apache.bcel.generic.EmptyVisitor */
        public void visitGETFIELD(GETFIELD aField)
        {
            reportRead(aField);
        }

        /** @see org.apache.bcel.generic.EmptyVisitor */
        public void visitPUTFIELD(PUTFIELD aField)
        {
            reportWrite(aField);
        }

        /** @see org.apache.bcel.generic.EmptyVisitor */
        public void visitGETSTATIC(GETSTATIC aField)
        {
            reportRead(aField);
        }

        /** @see org.apache.bcel.generic.EmptyVisitor */
        public void visitPUTSTATIC(PUTSTATIC aField)
        {
            reportWrite(aField);
        }
    }

    /**
     * Represents the statistics for a field.
     */
    private static class FieldStats
    {
        /** the name of the field */
        private final String mName;
        /** number of reads */
        private int mReads = 0;
        /** number of writes */
        private int mWrites = 0;
        /** field details */
        private Field mDetails;

        /**
         * Creates a new <code>FieldStats</code> instance.
         *
         * @param aName name of the field
         */
        FieldStats(String aName)
        {
            mName = aName;
        }

        /** @return the name of the field */
        String getName()
        {
            return mName;
        }

        /** @return the number of field reads */
        int getReads()
        {
            return mReads;
        }

        /** @return the number of field writes */
        int getWrites()
        {
            return mWrites;
        }

        /** Add a field read */
        void addRead()
        {
            mReads++;
        }

        /** Add a field write */
        void addWrite()
        {
            mWrites++;
        }

        /** @return the field details */
        Field getDetails()
        {
            return mDetails;
        }

        /** @param aDetails set the field detail */
        void setDetails(Field aDetails)
        {
            mDetails = aDetails;
        }
    }
}
