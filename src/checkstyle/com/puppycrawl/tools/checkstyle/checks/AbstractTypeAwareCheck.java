////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2003  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Utils;
import java.util.HashSet;
import java.util.Set;

/**
 * Abstract class that endeavours to maintain type information for the Java
 * file being checked. It provides helper methods for performing type
 * information functions.
 *
 * @author Oliver Burn
 * @version 1.0
 */
public abstract class AbstractTypeAwareCheck
    extends Check
{
    /** imports details **/
    private Set mImports = new HashSet();

    /** full identifier for package of the method **/
    private FullIdent mPackageFullIdent = null;

    /** <code>ClassResolver</code> instance for current tree. */
    private ClassResolver mClassResolver;

    /**
     * Called to process an AST when visiting it.
     * @param aAST the AST to process. Guaranteed to not be PACKAGE_DEF or
     *             IMPORT tokens.
     */
    protected abstract void processAST(DetailAST aAST);

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void beginTree(DetailAST aRootAST)
    {
        mPackageFullIdent = FullIdent.createFullIdent(null);
        mImports.clear();
        mClassResolver = null;
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public final void visitToken(DetailAST aAST)
    {
        if (aAST.getType() == TokenTypes.PACKAGE_DEF) {
            processPackage(aAST);
        }
        else if (aAST.getType() == TokenTypes.IMPORT) {
            processImport(aAST);
        }
        else {
            processAST(aAST);
        }
    }

    /**
     * Calculate if one type name is a shortname for another type name.
     * @param aShortName a shorthand, such as <code>IOException</code>
     * @param aFullName a full name, such as <code>java.io.IOException</code>
     * @return true iff aShortName represents the same type as aFullName
     */
    protected boolean isShortName(String aShortName, String aFullName)
    {
        if (aShortName.length() >= aFullName.length()) {
            return false;
        }

        final String base = Utils.baseClassname(aFullName);
        if (aShortName.length() >= aFullName.length()
                || !base.equals(aShortName))
        {
            return false;
        }

        // check fully qualified import
        if (mImports.contains(aFullName)) {
            return true;
        }

        // check .* import
        final int endIndex = aFullName.length() - base.length() - 1;
        final String packageName = aFullName.substring(0, endIndex);
        final String starImport = packageName + ".*";
        if (mImports.contains(starImport)) {
            return true;
        }

        // check fully qualified class from same package
        return packageName.equals(mPackageFullIdent.getText());
    }

    /**
     * Is exception is unchecked (subclass of <code>RuntimeException</code>
     * or <code>Error</code>
     *
     * @param aException <code>FullIdent</code> of exception to check
     * @return true  if exception is unchecked
     *         false if exception is checked
     */
    protected boolean isUnchecked(FullIdent aException)
    {
        final ClassResolver cr = getClassResolver();
        try {
            final Class clazz = cr.resolve(aException.getText());
            return (RuntimeException.class.isAssignableFrom(clazz)
                    || Error.class.isAssignableFrom(clazz));
        }
        catch (ClassNotFoundException e) {
            log(aException.getLineNo(), aException.getColumnNo(),
                "redundant.throws.classInfo",
                aException.getText());
        }

        // return false to prefent from additional errors
        return false;
    }

    /**
     * Checks if one class is subclass of another
     *
     * @param aChild <code>FullIdent</code> of class
     *               which should be child
     * @param aParent <code>FullIdent</code> of class
     *                which should be parent
     * @return true  if aChild is subclass of aParent
     *         false otherwise
     */
    protected boolean isSubclass(FullIdent aChild, FullIdent aParent)
    {
        final ClassResolver cr = getClassResolver();
        try {
            final Class childClass = cr.resolve(aChild.getText());
            try {
                final Class parentClass = cr.resolve(aParent.getText());
                return parentClass.isAssignableFrom(childClass);
            }
            catch (ClassNotFoundException e) {
                log(aChild.getLineNo(), aChild.getColumnNo(),
                    "redundant.throws.classInfo",
                    aParent.getText());
            }
        }
        catch (ClassNotFoundException e) {
            log(aChild.getLineNo(), aChild.getColumnNo(),
                "redundant.throws.classInfo",
                aChild.getText());
        }

        return true;
    }

    /**
     * Return if two Strings represent the same type, inspecting the
     * import statements if necessary
     *
     * @param aFirst first type declared in throws clause
     * @param aSecond second type declared in thros tag
     * @return true iff type names represent the same type
     */
    protected boolean isSameType(String aFirst, String aSecond)
    {
        return aFirst.equals(aSecond)
                || isShortName(aFirst, aSecond)
                || isShortName(aSecond, aFirst);
    }

    /** @return <code>ClassResolver</code> for current tree. */
    protected final ClassResolver getClassResolver()
    {
        if (mClassResolver == null) {
            mClassResolver =
                new ClassResolver(getClassLoader(),
                                  mPackageFullIdent.getText(),
                                  mImports);
        }
        return mClassResolver;
    }

    /**
     * Collects the details of a package.
     * @param aAST node containing the package details
     */
    private void processPackage(DetailAST aAST)
    {
        final DetailAST nameAST = (DetailAST) aAST.getFirstChild();
        mPackageFullIdent = FullIdent.createFullIdent(nameAST);
    }

    /**
     * Collects the details of imports.
     * @param aAST node containing the import details
     */
    private void processImport(DetailAST aAST)
    {
        final FullIdent name = FullIdent.createFullIdentBelow(aAST);
        if (name != null) {
            mImports.add(name.getText());
        }
    }
}
