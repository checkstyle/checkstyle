//Tested with BCEL-5.1
//http://jakarta.apache.org/builds/jakarta-bcel/release/v5.1/

package com.puppycrawl.tools.checkstyle.bcel.classfile;

import java.util.HashSet;
import java.util.Set;

import org.apache.bcel.classfile.Field;

import com.puppycrawl.tools.checkstyle.bcel.generic.FieldReference;
import com.puppycrawl.tools.checkstyle.bcel.generic.PUTFIELDReference;
import com.puppycrawl.tools.checkstyle.bcel.generic.PUTSTATICReference;

/**
 * Contains the definition of a Field and its references.
 * @author Rick Giles
 */
public class FieldDefinition
    extends FieldOrMethodDefinition
{
    /** the GET references for the Field */
    private final Set mGetReferences = new HashSet();

    /** the PUT references for the FSield */
    private final Set mPutReferences = new HashSet();

    /**
     * Creates a <code>FieldDefinition</code> for a Field.
     * @param aField the Field.
     */
    public FieldDefinition(Field aField)
    {
        super(aField);
    }

    /**
     * Returns the Field for this definition.
     * @return the Field for this definition.
     */
    public Field getField()
    {
        return (Field) getFieldOrMethod();
    }

    /**
     * Determines the number of read, or GET, references to the Field.
     * @return the number of read references to the Field.
     */
    public int getReadReferenceCount()
    {
        return mGetReferences.size();
    }

    /**
     * Determines the number of write, or PUT, references to the Field.
     * @return the number of write references to the Field.
     */
    public int getWriteReferenceCount()
    {
        return mPutReferences.size();
    }

    /**
     * Determines the total number of references to the Field.
     * @return the number of references to the Field.
     */
    public int getReferenceCount()
    {
        return getReadReferenceCount() + getWriteReferenceCount();
    }

    /**
     * Adds a reference to the Field.
     * @param aFieldRef the reference.
     */
    public void addReference(FieldReference aFieldRef)
    {
        // TODO Polymorphize
        if ((aFieldRef instanceof PUTFIELDReference)
            || (aFieldRef instanceof PUTSTATICReference))
        {
            mPutReferences.add(aFieldRef);
        }
        else {
            mGetReferences.add(aFieldRef);
        }
    }
}
