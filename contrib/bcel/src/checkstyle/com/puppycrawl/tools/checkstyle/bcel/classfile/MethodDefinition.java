//Tested with BCEL-5.1
//http://jakarta.apache.org/builds/jakarta-bcel/release/v5.1/

package com.puppycrawl.tools.checkstyle.bcel.classfile;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.Type;

import com.puppycrawl.tools.checkstyle.bcel.generic.InvokeReference;
import com.puppycrawl.tools.checkstyle.bcel.generic.Utils;

/**
 * Contains the definition of a Method and its references.
 * @author Rick Giles
 */
public class MethodDefinition
    extends FieldOrMethodDefinition
{
    /** the references to the Method */
    private Set mReferences = new HashSet();

    /**
     * Creates a <code>MethodDefinition</code> for a Method.
     * @param aMethod the Method.
     */
    public MethodDefinition(Method aMethod)
    {
        super(aMethod);
    }

    /**
     * Gets the references to the Method.
     * @return the references to the Method.
     */
    public Set getReferences()
    {
        return mReferences;
    }

    /**
     * Determines the number of references to the Method.
     * @return the number of references to the Method.
     */
    public int getReferenceCount()
    {
        return mReferences.size();
    }

    /**
     * Returns the Method for this definition.
     * @return the Method for this definition.
     */
    public Method getMethod()
    {
        return (Method) getFieldOrMethod();
    }

    /**
     * Adds a reference to the Method.
     * @param aRef the reference.
     */

    public void addReference(InvokeReference aRef)
    {
        mReferences.add(aRef);
    }

    /**
     * Gets the Types of the Method's arguments.
     * @return the argument Types.
     */
    public Type[] getArgumentTypes()
    {
        return getMethod().getArgumentTypes();
    }

    /**
     * Determines whether a Method is compatible with the
     * Method of this definition.
     * @param aMethod the Method to check.
     * @return true if aMethod is compatible with the Method
     * of this definition.
     */
    public boolean isCompatible(Method aMethod)
    {
        return isCompatible(aMethod.getName(), aMethod.getArgumentTypes());
    }

    /**
     * Determines whether a MethodDefinition is compatible with the
     * Method of this definition.
     * @param aMethodDef the Method definition to check.
     * @return true if aMethod is compatible with the Method
     * of this definition.
     */
    public boolean isCompatible(MethodDefinition aMethodDef)
    {
        return isCompatible(aMethodDef.getMethod());
    }

    /**
     * Determines whether the Method of a MethodDefinition is as narrow
     * as the method for this definition.
     * Precondition: the method for this has the same name and the same
     * number of arguments as the Method for the given MethodDefinition.
     * @param aMethodDef the MethodDefinition to check.
     * @return true if the Method of aMethodDef is as narrow
     * as the method for this definition.
     */
    public boolean isAsNarrow(MethodDefinition aMethodDef)
    {
        return aMethodDef.isCompatible(this);
//        final Type[] types1 = getArgumentTypes();
//        final Type[] types2 = aMethodDef.getArgumentTypes();
//        for (int i = 0; i < types2.length; i++) {
//            if (!Utils.isCompatible(types1[i], types2[i])) {
//                return false;
//            }
//        }
//        return true;
    }

    /**
     * Determines whether a method is compatible with the Method of
     * this definition.
     * @param aMethodName the name of the method to check.
     * @param aArgTypes the method argument types.
     * @return true if the method is compatible with the Method of
     * this definition.
     */
    public boolean isCompatible(String aMethodName, Type[] aArgTypes)
    {
        // same name?
        if (!getName().equals(aMethodName)) {
            return false;
        }
        // compatible argument types?
        final Type[] methodTypes = getArgumentTypes();
        if (methodTypes.length != aArgTypes.length) {
            return false;
        }
        for (int i = 0; i < aArgTypes.length; i++) {
            if (!Utils.isCompatible(aArgTypes[i], methodTypes[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determine whether this method definition has a reference from a class or
     * a superclass.
     * @param aJavaClass the JavaClass to check against.
     * @return true if there is a reference to this method definition from a
     * aJavaClass or a superclass of aJavaClass.
     */
    public boolean hasReference(JavaClass aJavaClass)
    {
        final Iterator it = getReferences().iterator();
        while (it.hasNext()) {
            final InvokeReference invokeRef = (InvokeReference) it.next();
            final String invokeClassName = invokeRef.getClassName();
            if (Repository.instanceOf(aJavaClass, invokeClassName)) {
                return true;
            }
        }
        return false;
    }
}
