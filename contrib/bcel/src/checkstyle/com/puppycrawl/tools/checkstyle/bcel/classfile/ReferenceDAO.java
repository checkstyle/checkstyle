//Tested with BCEL-5.1
//http://jakarta.apache.org/builds/jakarta-bcel/release/v5.1/

package com.puppycrawl.tools.checkstyle.bcel.classfile;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.generic.Type;

import com.puppycrawl.tools.checkstyle.bcel.generic.FieldReference;
import com.puppycrawl.tools.checkstyle.bcel.generic.InvokeReference;

/**
 * Accesses Method and Field references for a set of JavaClasses.
 * @author Rick Giles
 */
public class ReferenceDAO
{
    /**
     * Creates a <code>ReferenceDAO</code> from a set of JavaClasses.
     * @param aJavaClasses the set of JavaClasses for this DAO.
     * @param aFieldScopes the scopes for field references.
     * @param aMethodScopes the scopes for method references.
     */
    public ReferenceDAO(Set aJavaClasses, Set aFieldScopes, Set aMethodScopes)
    {
        mJavaClasses = new HashMap(aJavaClasses.size());
        final Iterator it = aJavaClasses.iterator();
        while (it.hasNext()) {
            final JavaClass javaClass = (JavaClass) it.next();
            final JavaClassDefinition javaClassDef =
                new JavaClassDefinition(javaClass, aFieldScopes, aMethodScopes);
            mJavaClasses.put(javaClass, javaClassDef);
        }
    }

    /** maps a JavaClass to a JavaClassDefinition */
    private Map mJavaClasses = null;

    /**
     * Finds the JavaClassDefinition for a given JavaClass.
     * @param aJavaClass the JavaClass.
     * @return the JavaClassDefinition keyed on aJavaClass.
     */
    public JavaClassDefinition findJavaClassDef(JavaClass aJavaClass)
    {
        return (JavaClassDefinition) mJavaClasses.get(aJavaClass);
    }

    /**
     * Adds a reference for an invocation in the invoked method definition.
     * The invocation is of the form class.method(args).
     * @param aInvokeRef the invocation reference.
     */
    public void addInvokeReference(InvokeReference aInvokeRef)
    {
        // find the class for the instruction
        final String className = aInvokeRef.getClassName();
        JavaClass javaClass = Repository.lookupClass(className);
        final String methodName = aInvokeRef.getName();
        final Type[] argTypes = aInvokeRef.getArgTypes();

        // search up the class hierarchy for the class containing the
        // method definition.
        MethodDefinition narrowest = null;
        while ((javaClass != null) && (narrowest == null)) {
            final JavaClassDefinition javaClassDef =
                (JavaClassDefinition) mJavaClasses.get(javaClass);
            if (javaClassDef != null) {
                // find narrowest compatible in the current class
                narrowest =
                    javaClassDef.findNarrowestMethod(
                        className,
                        methodName,
                        argTypes);
                if (narrowest != null) {
                    narrowest.addReference(aInvokeRef);
                }
            }
            // search the parent
            javaClass = javaClass.getSuperClass();
        }
    }

    /**
     * Adds a reference to a field.
     * @param aFieldRef the field reference.
     */
    public void addFieldReference(FieldReference aFieldRef)
    {
        final String className = aFieldRef.getClassName();
        JavaClass javaClass = Repository.lookupClass(className);
        final String fieldName = aFieldRef.getName();
        // search up the class hierarchy for the class containing the
        // method definition.
        FieldDefinition fieldDef = null;
        while ((javaClass != null) && (fieldDef == null)) {
            final JavaClassDefinition javaClassDef =
                (JavaClassDefinition) mJavaClasses.get(javaClass);
            if (javaClassDef != null) {
                fieldDef = javaClassDef.findFieldDef(fieldName);
                if (fieldDef != null) {
                    fieldDef.addReference(aFieldRef);
                }
            }
            //search the parent
            javaClass = javaClass.getSuperClass();
        }
    }

    /**
     * Finds the definition of the field of a field reference.
     * @param aFieldRef the reference to a field.
     * @return the definition of the field for aFieldRef.
     */
    public FieldDefinition findFieldDef(FieldReference aFieldRef)
    {
        final String className = aFieldRef.getClassName();
        JavaClass javaClass = Repository.lookupClass(className);
        final String fieldName = aFieldRef.getName();
        // search up the class hierarchy for the class containing the
        // method definition.
        FieldDefinition result = null;
        while ((javaClass != null) && (result == null)) {
            final JavaClassDefinition javaClassDef =
                (JavaClassDefinition) mJavaClasses.get(javaClass);
            if (javaClassDef != null) {
                result = javaClassDef.findFieldDef(fieldName);
            }
            //search the parent
            javaClass = javaClass.getSuperClass();
        }
        return result;
    }
}
