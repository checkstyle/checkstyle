//Tested with BCEL-5.1
//http://jakarta.apache.org/builds/jakarta-bcel/release/v5.1/

package com.puppycrawl.tools.checkstyle.bcel;

import java.util.HashSet;
import java.util.Set;

import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.GETFIELD;
import org.apache.bcel.generic.GETSTATIC;
import org.apache.bcel.generic.INVOKESPECIAL;
import org.apache.bcel.generic.INVOKESTATIC;
import org.apache.bcel.generic.INVOKEVIRTUAL;
import org.apache.bcel.generic.PUTFIELD;
import org.apache.bcel.generic.PUTSTATIC;

import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.bcel.classfile.JavaClassDefinition;
import com.puppycrawl.tools.checkstyle.bcel.classfile.ReferenceDAO;
import com.puppycrawl.tools.checkstyle.bcel.generic.FieldReference;
import com.puppycrawl.tools.checkstyle.bcel.generic.GETFIELDReference;
import com.puppycrawl.tools.checkstyle.bcel.generic.GETSTATICReference;
import com.puppycrawl.tools.checkstyle.bcel.generic.InvokeReference;
import com.puppycrawl.tools.checkstyle.bcel.generic.PUTFIELDReference;
import com.puppycrawl.tools.checkstyle.bcel.generic.PUTSTATICReference;

/**
 * Records references during a deep parse tree traversal.
 * @author Rick Giles
 */
public final class ReferenceVisitor extends EmptyDeepVisitor
{
    /** singleton */
    private static ReferenceVisitor sInstance = new ReferenceVisitor();

    /** scope for checking field references */
    private Set mFieldScopes = new HashSet();

    /** scope for checking method references */
    private Set mMethodScopes = new HashSet();

    /** maps a JavaClass to a JavaClassDefinition */
    private ReferenceDAO mReferenceDAO;

    /** access to current constant pool */
    private ConstantPoolGen mCurrentPoolGen;

    /**
     * Adds a reference when it visits an instruction that invokes
     * a method or references a field.
     * @author Rick Giles
     * @version 18-Jun-2003
     */
    private class GenericVisitor extends org.apache.bcel.generic.EmptyVisitor
    {
        /** @see org.apache.bcel.generic.Visitor */
        public void visitINVOKESPECIAL(INVOKESPECIAL aINVOKESPECIAL)
        {
            addInvokeReference(
                new InvokeReference(aINVOKESPECIAL, mCurrentPoolGen));
        }

        /** @see org.apache.bcel.generic.Visitor */
        public void visitINVOKESTATIC(INVOKESTATIC aINVOKESTATIC)
        {
            addInvokeReference(
                new InvokeReference(aINVOKESTATIC, mCurrentPoolGen));
        }

        /** @see org.apache.bcel.generic.Visitor */
        public void visitINVOKEVIRTUAL(INVOKEVIRTUAL aINVOKEVIRTUAL)
        {
            addInvokeReference(
                new InvokeReference(aINVOKEVIRTUAL, mCurrentPoolGen));
        }

        /** @see org.apache.bcel.generic.Visitor */
        public void visitGETSTATIC(GETSTATIC aGETSTATIC)
        {
            addFieldReference(
                new GETSTATICReference(aGETSTATIC, mCurrentPoolGen));
        }

        /** @see org.apache.bcel.generic.Visitor */
        public void visitGETFIELD(GETFIELD aGETFIELD)
        {
            addFieldReference(
                new GETFIELDReference(aGETFIELD, mCurrentPoolGen));
        }

        /** @see org.apache.bcel.generic.Visitor */
        public void visitPUTSTATIC(PUTSTATIC aPUTSTATIC)
        {
            addFieldReference(
                new PUTSTATICReference(aPUTSTATIC, mCurrentPoolGen));
        }

        /** @see org.apache.bcel.generic.Visitor */
        public void visitPUTFIELD(PUTFIELD aPUTFIELD)
        {
            addFieldReference(
                new PUTFIELDReference(aPUTFIELD, mCurrentPoolGen));
        }
    }

    /** prevent client construction */
    private ReferenceVisitor()
    {
        setGenericVisitor(new GenericVisitor());
        addFieldScope(Scope.PRIVATE);
    }

    /**
     * Returns the singleton ReferencesVisitor
     * @return the singleton
     */
    public static ReferenceVisitor getInstance()
    {
        return sInstance;
    }

    /**
     * Adds an invoke reference to the reference DAO.
     * @param aReference the reference.
     */
    private void addInvokeReference(InvokeReference aReference)
    {
        getReferenceDAO().addInvokeReference(aReference);
    }

    /**
     * Adds an field reference to the reference DAO.
     * @param aReference the reference.
     */
    private void addFieldReference(FieldReference aReference)
    {
        getReferenceDAO().addFieldReference(aReference);
    }

    /** @see com.puppycrawl.tools.checkstyle.bcel.IDeepVisitor */
    public void visitSet(Set aJavaClasses)
    {
        mReferenceDAO =
            new ReferenceDAO(aJavaClasses, mFieldScopes, mMethodScopes);
    }

    /**
     * Gets the reference DAO.
     * @return the reference DAO.
     */
    public ReferenceDAO getReferenceDAO()
    {
        return mReferenceDAO;
    }

    /** @see com.puppycrawl.tools.checkstyle.bcel.IDeepVisitor */
    public void visitObject(Object aObject)
    {
        final JavaClass javaClass = (JavaClass) aObject;
        final ConstantPool pool = javaClass.getConstantPool();
        mCurrentPoolGen = new ConstantPoolGen(pool);
    }

    /**
     * Finds the JavaClassDefinition for a given JavaClass.
     * @param aJavaClass the JavaClass.
     * @return the JavaClassDefinition for aJavaClass.
     */
    public JavaClassDefinition findJavaClassDef(JavaClass aJavaClass)
    {
        return getReferenceDAO().findJavaClassDef(aJavaClass);
    }

    /**
     * Includes a scope in the scope for checking field references.
     * @param aScope the scope to include.
     */
    public void addFieldScope(Scope aScope)
    {
        mFieldScopes.add(aScope);
    }

    /**
     * Includes a scope in the scope for checking method references.
     * @param aScope the scope to include.
     */
    public void addMethodScope(Scope aScope)
    {
        mMethodScopes.add(aScope);
    }
}
