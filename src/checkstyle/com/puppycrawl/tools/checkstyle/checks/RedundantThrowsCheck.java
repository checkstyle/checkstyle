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
package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;

/**
 * Checks for redundant exceptions declared in throws clause
 * such as duplicates, unchecked exceptions or subclasses of
 * another declared exception.
 *
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="RedundantThrows"&gt;
 *    &lt;property name=&quot;allowUnchecked&quot; value=&quot;true&quot;/&gt;
 *    &lt;property name=&quot;allowSubclasses&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * @author o_sukhodolsky
 */
public class RedundantThrowsCheck
    extends AbstractImportCheck
{
    /**
     * whether unchecked exceptions in throws
     * are allowed or not
     */
    private boolean mAllowUnchecked = false;

    /**
     * whether subclass of another declared
     * exception is allowed in throws clause
     */
    private boolean mAllowSubclasses = false;

    /** full identifier for package of the method **/
    private FullIdent mPackageFullIdent = null;

    /** imports details **/
    private Set mImports = new HashSet();

    /**
     * Getter for allowUnchecked property
     * @param aAllowUnchecked whether unchecked excpetions in throws
     *                         are allowed or not
     */
    public void setAllowUnchecked(boolean aAllowUnchecked)
    {
        mAllowUnchecked = aAllowUnchecked;
    }

    /**
     * Getter for allowSubclasses property
     * @param aAllowSubclasses whether subclass of another declared
     *                         exception is allowed in throws clause
     */
    public void setAllowSubclasses(boolean aAllowSubclasses)
    {
        mAllowSubclasses = aAllowSubclasses;
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.PACKAGE_DEF,
            TokenTypes.IMPORT,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
        };
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void beginTree(DetailAST aRootAST)
    {
        mPackageFullIdent = FullIdent.createFullIdent(null);
        mImports.clear();
        mClassResolver = null;
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST)
    {
        if (aAST.getType() == TokenTypes.PACKAGE_DEF) {
            processPackage(aAST);
        }
        else if (aAST.getType() == TokenTypes.IMPORT) {
            processImport(aAST);
        }
        else {
            //TokenTypes.METHOD_DEF or TokenTypes.CTOR_DEF
            processMethod(aAST);
        }
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
        final FullIdent name = getImportText(aAST);
        if (name != null) {
            mImports.add(name.getText());
        }
    }

    /**
     * Checks exceptions declared in throws for a method or constructor.
     * @param aAST the tree node for the method or constructor.
     */
    private void processMethod(DetailAST aAST)
    {
        final List knownExcs = new LinkedList();
        final DetailAST throwsAST =
            aAST.findFirstToken(TokenTypes.LITERAL_THROWS);
        if (throwsAST != null) {
            DetailAST child = (DetailAST) throwsAST.getFirstChild();
            while (child != null) {
                if ((child.getType() == TokenTypes.IDENT)
                    || (child.getType() == TokenTypes.DOT))
                {
                    final FullIdent fi = FullIdent.createFullIdent(child);
                    checkException(fi, knownExcs);
                }
                child = (DetailAST) child.getNextSibling();
            }
        }
    }

    /**
     * Checks if an exception is already know (list of known
     * exceptions contains it or its superclass) and it's not
     * a superclass for some known exception and it's not
     * an unchecked exception.
     * If it's unknown then it will be added to ist of known exception.
     * All subclasses of this exception will be deleted from known
     * and the exception  will be added instead.
     *
     * @param aExc <code>FullIdent</code> of exception to check
     * @param aKnownExcs list of already known exception
     */
    private void checkException(FullIdent aExc, List aKnownExcs)
    {
        if (!mAllowUnchecked) {
            if (isUnchecked(aExc)) {
                log(aExc.getLineNo(), aExc.getColumnNo(),
                    "redundant.throws.unchecked", aExc.getText());
            }
        }

        boolean shouldAdd = true;
        for (Iterator known = aKnownExcs.iterator(); known.hasNext();) {
            final FullIdent fi = (FullIdent) known.next();
            if (isSameType(fi.getText(), aExc.getText())) {
                shouldAdd = false;
                log(aExc.getLineNo(), aExc.getColumnNo(),
                    "redundant.throws.duplicate", aExc.getText());
            }
            else if (!mAllowSubclasses) {
                if (isSubclass(fi, aExc)) {
                    known.remove();
                    log(fi.getLineNo(), fi.getColumnNo(),
                        "redundant.throws.subclass",
                        fi.getText(), aExc.getText());
                }
                else if (isSubclass(aExc, fi)) {
                    shouldAdd = false;
                    log(aExc.getLineNo(), aExc.getColumnNo(),
                        "redundant.throws.subclass",
                        aExc.getText(), fi.getText());
                }
            }
        }

        if (shouldAdd) {
            aKnownExcs.add(aExc);
        }
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
    private boolean isSubclass(FullIdent aChild, FullIdent aParent)
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
     * Is exception is unchecked (subclass of <code>RuntimeException</code>
     * or <code>Error</code>
     *
     * @param aException <code>FullIdent</code> of exception to check
     * @return true  if exception is unchecked
     *         false if exception is checked
     */
    private boolean isUnchecked(FullIdent aException)
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
     * Return if two Strings represent the same type, inspecting the
     * import statements if necessary
     *
     * @param aFirst first type declared in throws clause
     * @param aSecond second type declared in thros tag
     * @return true iff type names represent the same type
     */
    private boolean isSameType(String aFirst, String aSecond)
    {
        return aFirst.equals(aSecond)
                || isShortName(aFirst, aSecond)
                || isShortName(aSecond, aFirst);
    }

    /**
     * Calculate if one type name is a shortname for another type name.
     * @param aShortName a shorthand, such as <code>IOException</code>
     * @param aFullName a full name, such as <code>java.io.IOException</code>
     * @return true iff aShortName represents the same type as aFullName
     */
    private boolean isShortName(String aShortName, String aFullName)
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

    /** @return <code>ClassResolver</code> for current tree. */
    private ClassResolver getClassResolver()
    {
        if (mClassResolver == null) {
            mClassResolver = new ClassResolver(
                                  getClassLoader(),
                                  mPackageFullIdent.getText(),
                                  mImports);
        }
        return mClassResolver;
    }

    /** <code>ClassResolver</code> instance for current tree. */
    private ClassResolver mClassResolver;
}
