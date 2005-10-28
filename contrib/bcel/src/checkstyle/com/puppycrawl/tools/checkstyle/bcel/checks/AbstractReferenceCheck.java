//Tested with BCEL-5.1
//http://jakarta.apache.org/builds/jakarta-bcel/release/v5.1/

package com.puppycrawl.tools.checkstyle.bcel.checks;

import org.apache.bcel.classfile.FieldOrMethod;
import org.apache.bcel.classfile.JavaClass;
import org.apache.commons.beanutils.ConversionException;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.Utils;
import com.puppycrawl.tools.checkstyle.bcel.AbstractCheckVisitor;
import com.puppycrawl.tools.checkstyle.bcel.IDeepVisitor;
import com.puppycrawl.tools.checkstyle.bcel.ReferenceVisitor;
import com.puppycrawl.tools.checkstyle.bcel.classfile.JavaClassDefinition;
import com.puppycrawl.tools.checkstyle.bcel.classfile.ReferenceDAO;

/**
 * Abstract class for checks that require reference information.
 * @author Rick Giles
 */
public abstract class AbstractReferenceCheck
    extends AbstractCheckVisitor
{
    /** the scope for recorded references */
    private Scope mScope = Scope.PRIVATE;

    /** the regexp to match class names against */
    private Pattern mIgnoreClassNameRegexp;

    /** the regexp to match names against */
    private Pattern mIgnoreNameRegexp;

    /**
     * Creates a <code>AbstractReferenceCheck</code>.
     *
     */
    public AbstractReferenceCheck()
    {
        setIgnoreClassName("^$");
        setIgnoreName("^$");
    }

    /**
     * Sets the scope for recorded references.
     * @param aScopeName the scope for recorded references.
     */
    public void setScope(String aScopeName)
    {
        mScope = Scope.getInstance(aScopeName);
    }

    /**
     * Determines whether a class name, and field or method should be ignored.
     * @param aClassName the class name to consider.
     * @param aFieldOrMethod  the field or method to consider.
     * @return true if aClassName, and field or method should be ignored.
     */
    protected boolean ignore(String aClassName, FieldOrMethod aFieldOrMethod)
    {
        final String fieldOrMethodName = aFieldOrMethod.getName();
        return (!equalScope(aFieldOrMethod)
                || mIgnoreClassNameRegexp.matcher(aClassName).matches()
            || mIgnoreNameRegexp.matcher(fieldOrMethodName).matches());
    }

    /**
     * Tests whether the scope of a field or method is compatible
     * with the scope of this check. References for compatible
     * fields or methods should be checked.
     * @param aFieldOrMethod the field or method to check.
     * @return true if the scope of aFieldOrMethod is compatible
     * with the scope of this check.
     */
    private boolean equalScope(FieldOrMethod aFieldOrMethod)
    {
        if (aFieldOrMethod.isPrivate()) {
            return (mScope == Scope.PRIVATE);
        }
        else if (aFieldOrMethod.isProtected()) {
            return (mScope == Scope.PROTECTED);
        }
        else if (aFieldOrMethod.isPublic()) {
            return (mScope == Scope.PUBLIC);
        }
        else {
            return (mScope == Scope.PACKAGE);
        }
    }


    /**
     * Set the ignore class name to the specified regular expression.
     * @param aFormat a <code>String</code> value
     * @throws ConversionException unable to parse aFormat
     */
    public void setIgnoreClassName(String aFormat)
        throws ConversionException
    {
        try {
            mIgnoreClassNameRegexp = Utils.getPattern(aFormat);
        }
        catch (PatternSyntaxException e) {
            throw new ConversionException("unable to parse " + aFormat, e);
        }
    }

    /**
     * Set the ignore name format to the specified regular expression.
     * @param aFormat a <code>String</code> value
     * @throws ConversionException unable to parse aFormat
     */
    public void setIgnoreName(String aFormat)
        throws ConversionException
    {
        try {
            mIgnoreNameRegexp = Utils.getPattern(aFormat);
        }
        catch (PatternSyntaxException e) {
            throw new ConversionException("unable to parse " + aFormat, e);
        }
    }

    /** @see com.puppycrawl.tools.checkstyle.bcel.AbstractCheckVisitor */
    public IDeepVisitor getVisitor()
    {
        return ReferenceVisitor.getInstance();
    }

    /**
     * Gets the DAO that manages references for this check.
     * @return the the DAO that manages references for this check.
     */
    public ReferenceDAO getReferenceDAO()
    {
        return ((ReferenceVisitor) getVisitor()).getReferenceDAO();
    }

    /**
     * Finds a JavaClassDefinition for a JavaClass.
     * @param aJavaClass the JavaClass.
     * @return the JavaClassDefinition for aJavaClass.
     */
    protected JavaClassDefinition findJavaClassDef(JavaClass aJavaClass)
    {
        return getReferenceDAO().findJavaClassDef(aJavaClass);
    }
}
