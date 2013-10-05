//Tested with BCEL-5.1
//http://jakarta.apache.org/builds/jakarta-bcel/release/v5.1/

package com.puppycrawl.tools.checkstyle.bcel.generic;

import org.apache.bcel.generic.ReferenceType;
import org.apache.bcel.generic.Type;

/**
 * Utility methods.
 * @author Rick Giles
 * @version 17-Jun-2003
 */
public class Utils
{
    /**
     * Tests whether one type is compatible with another for method
     * invocation conversion. This includes assignment conversion,
     * except the implicit narrowing of integer constants.
     * JLS Section 5.2
     * @param aSubType the type to be converted.
     * @param aSuperType the converted type.
     * @return true if aSubType can be converted to aSuperType.
     */
    public static boolean isCompatible(Type aSubType, Type aSuperType)
    {
        boolean result = false;

        if (aSubType.equals(aSuperType)) {
            // identity conversion
            result = true;
        }
        else if ((aSubType instanceof ReferenceType)
            && (aSuperType instanceof ReferenceType))
        {
            // widening reference conversion?
            final ReferenceType aSubRefType = (ReferenceType) aSubType;
            result = aSubRefType.isAssignmentCompatibleWith(aSuperType);
        }
        // widening primitive conversion?
        else if (aSubType.equals(Type.BYTE)) {
            result =
                aSuperType.equals(Type.SHORT)
                    || aSuperType.equals(Type.INT)
                    || aSuperType.equals(Type.LONG)
                    || aSuperType.equals(Type.FLOAT)
                    || aSuperType.equals(Type.DOUBLE);
        }
        else if (aSubType.equals(Type.SHORT)) {
            result =
                aSuperType.equals(Type.INT)
                    || aSuperType.equals(Type.LONG)
                    || aSuperType.equals(Type.FLOAT)
                    || aSuperType.equals(Type.DOUBLE);
        }
        else if (aSubType.equals(Type.INT)) {
            result =
                aSuperType.equals(Type.LONG)
                    || aSuperType.equals(Type.FLOAT)
                    || aSuperType.equals(Type.DOUBLE);
        }
        else if (aSubType.equals(Type.LONG)) {
            result =
                aSuperType.equals(Type.FLOAT) || aSuperType.equals(Type.DOUBLE);
        }
        else if (aSubType.equals(Type.DOUBLE)) {
            result = aSuperType.equals(Type.DOUBLE);
        }
        return result;
    }
}
