//Tested with BCEL-5.1
//http://jakarta.apache.org/builds/jakarta-bcel/release/v5.1/

package com.puppycrawl.tools.checkstyle.bcel.checks;

import org.apache.bcel.classfile.JavaClass;
import org.apache.commons.beanutils.ConversionException;
import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;

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
    /** the regexp to match class names against */
    private RE mIgnoreClassNameRegexp;

    /** the regexp to match names against */
    private RE mIgnoreNameRegexp;

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
     * Determines whether a class name and name should be ignored.
     * @param aClassName the class name.
     * @param aName the name.
     * @return true if aClassName and aName should be ignored.
     */
    protected boolean ignore(String aClassName, String aName)
    {
        return (mIgnoreClassNameRegexp.match(aClassName)
            || mIgnoreNameRegexp.match(aName));
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
            mIgnoreClassNameRegexp = Utils.getRE(aFormat);
        }
        catch (RESyntaxException e) {
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
            mIgnoreNameRegexp = Utils.getRE(aFormat);
        }
        catch (RESyntaxException e) {
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
