//Tested with BCEL-5.1
//http://jakarta.apache.org/builds/jakarta-bcel/release/v5.1/

package com.puppycrawl.tools.checkstyle.bcel.classfile;

import java.util.HashMap;
import java.util.Map;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.Type;


/**
 * Contains the definition of a org.apache.bcel.classfile.JavaClass and
 * the definitions of Methods and Fields of the JavaClass
 * @author Rick Giles
 */
public class JavaClassDefinition
{
    /** the JavaClass */
    private JavaClass mJavaClass;

    /** the method definitions */
    private MethodDefinition[] mMethodDefs;

    /** field definitions, keyed on field name */
    private Map mFieldDefs;

    /**
     * Creates a JavaClassDefinition from a JavaClass.
     * @param aJavaClass the JavaClass for the definition.
     */
    public JavaClassDefinition(JavaClass aJavaClass)
    {
        mJavaClass = aJavaClass;

        // store method definitions
        final Method[] methods = aJavaClass.getMethods();
        mMethodDefs = new MethodDefinition[methods.length];
        for (int i = 0; i < methods.length; i++) {
            mMethodDefs[i] = new MethodDefinition(methods[i]);
        }

        // store field definitions
        final Field[] fields = aJavaClass.getFields();
        mFieldDefs = new HashMap(fields.length);
        for (int i = 0; i < fields.length; i++) {
            mFieldDefs.put(fields[i].getName(), new FieldDefinition(fields[i]));
        }
    }

    /**
     * Gets the JavaClass for this definition.
     * @return the JavaClass
     */
    public JavaClass getJavaClass()
    {
        return mJavaClass;
    }

    /**
     * Gets the method definitions for Methods of the JavaClass.
     * @return the method definitions for Methods of the JavaClass.
     */
    public MethodDefinition[] getMethodDefs()
    {
        return mMethodDefs;
    }

    /**
     * Gets the field definitions for Fields of the JavaClass.
     * @return the method definitions for Fields of the JavaClass.
     */
    public FieldDefinition[] getFieldDefs()
    {
        return (FieldDefinition[]) mFieldDefs.values().toArray(
            new FieldDefinition[mFieldDefs.size()]);
    }

    /**
     * Finds the narrowest method that is compatible with a method.
     * An invocation of the given method can be resolved as an invocation
     * of the narrowest method.
     * @param aClassName the class for the method.
     * @param aMethodName the name of the method.
     * @param aArgTypes the types for the method.
     * @return the narrowest compatible method.
     */
    public MethodDefinition findNarrowestMethod(
        String aClassName,
        String aMethodName,
        Type[] aArgTypes)
    {
        MethodDefinition result = null;
        final String javaClassName = mJavaClass.getClassName();
        if (Repository.instanceOf(aClassName, javaClassName)) {
            // check all
            for (int i = 0; i < mMethodDefs.length; i++) {
                // TODO: check access privileges
                if (mMethodDefs[i].isCompatible(aMethodName, aArgTypes)) {
                    if (result == null) {
                        result = mMethodDefs[i];
                    }
                    //else if (mMethodDefs[i].isAsNarrow(result)) {
                    else if (result.isCompatible(mMethodDefs[i])) {
                        result = mMethodDefs[i];
                    }
                }
            }
        }
        return result;
    }

    /**
     * Finds a field definition.
     * @param aFieldName the name of the field.
     * @return the field definition named aFieldName.
     */
    public FieldDefinition findFieldDef(String aFieldName)
    {
        return (FieldDefinition) mFieldDefs.get(aFieldName);
    }

    /**
     * Determines whether there is reference to a given Method in this JavaClass
     * definition or a definition in a superclass.
     * @param aMethodDef the Method to check.
     * @param aReferenceDAO reference DAO.
     * @return true if there is a reference to the method of aMethodDef in
     * this JavaClass or a superclass.
     */
    public boolean hasReference(
        MethodDefinition aMethodDef,
        ReferenceDAO aReferenceDAO)
     {
         final String methodName = aMethodDef.getName();
         final Type[] argTypes = aMethodDef.getArgumentTypes();
        
         // search the inheritance hierarchy
         JavaClass currentJavaClass = getJavaClass();
         while (currentJavaClass != null) {
             final JavaClassDefinition javaClassDef =
                 aReferenceDAO.findJavaClassDef(currentJavaClass);
             if (javaClassDef != null) {
                 final MethodDefinition methodDef =
                     javaClassDef.findNarrowestMethod(
                         getJavaClass().getClassName(),
                         methodName,
                         argTypes);
                 if ((methodDef != null)
                    && (methodDef.hasReference(getJavaClass())))
                 {
                     return true;
                 }
             }
             currentJavaClass = currentJavaClass.getSuperClass();
         }       
         return false;
     }

    /** @see java.lang.Object#toString() */
    public String toString()
    {
        return getJavaClass().toString();
    }
}
