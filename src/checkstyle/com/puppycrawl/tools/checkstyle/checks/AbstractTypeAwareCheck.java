////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2004  Oliver Burn
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
    private FullIdent mPackageFullIdent;

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
     * @param aException <code>Class</code> of exception to check
     * @return true  if exception is unchecked
     *         false if exception is checked
     */
    protected boolean isUnchecked(Class aException)
    {
        return isSubclass(aException, RuntimeException.class)
            || isSubclass(aException, Error.class);
    }

    /**
     * Checks if one class is subclass of another
     *
     * @param aChild <code>Class</code> of class
     *               which should be child
     * @param aParent <code>Class</code> of class
     *                which should be parent
     * @return true  if aChild is subclass of aParent
     *         false otherwise
     */
    protected boolean isSubclass(Class aChild, Class aParent)
    {
        return (aParent != null) && (aChild != null)
            &&  aParent.isAssignableFrom(aChild);
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
    private ClassResolver getClassResolver()
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
     * Attempts to resolve the Class for a specified name.
     * @param aClassName name of the class to resolve
     * @return the resolved class or <code>null</code>
     *          if unable to resolve the class.
     */
    protected final Class resolveClass(String aClassName)
    {
        try {
            return getClassResolver().resolve(aClassName);
        }
        catch (ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Tries to load class. Logs error if unable.
     * @param aIdent name of class which we try to load.
     * @return <code>Class</code> for a ident.
     */
    protected final Class tryLoadClass(FullIdent aIdent)
    {
        final Class clazz = resolveClass(aIdent.getText());
        if (clazz == null) {
            logLoadError(aIdent);
        }
        return clazz;
    }

    /**
     * Logs error if unable to load class information.
     * Abstract, should be overrided in subclasses.
     * @param aIdent class name for which we can no load class.
     */
    protected abstract void logLoadError(FullIdent aIdent);

    /**
     * Collects the details of a package.
     * @param aAST node containing the package details
     */
    private void processPackage(DetailAST aAST)
    {
        final DetailAST nameAST =
            (DetailAST) aAST.getLastChild().getPreviousSibling();
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

    /**
     * Contains class's <code>FullIdent</code>
     * and <code>Class</code> object if we can load it.
     */
    protected class ClassInfo
    {
        /** <code>FullIdent</code> associated with this class. */
        private FullIdent mName;
        /** <code>Class</code> object of this class if it's loadable. */
        private Class mClass;
        /** is class loadable. */
        private boolean mIsLoadable;

        /**
         * Creates new instance of of class information object.
         * @param aName <code>FullIdent</code> associated with new object.
         * @param aClass <code>Class</code> associated with new object
         *               or null id class is not loadable.
         */
        public ClassInfo(FullIdent aName, Class aClass)
        {
            if (aName == null && aClass == null) {
                throw new NullPointerException(
                    "ClassInfo's name or class should be non-null");
            }
            mName = aName;
            setClazz(aClass);
        }

        /**
         * Creates new instance of of class information object.
         * @param aName <code>FullIdent</code> associated with new object.
         */
        public ClassInfo(FullIdent aName)
        {
            if (aName == null) {
                throw new NullPointerException(
                    "ClassInfo's name should be non-null");
            }
            mName = aName;
            mIsLoadable = true;
        }

        /** @return class name */
        public final FullIdent getName()
        {
            return mName;
        }

        /** @return if class is loadable ot not. */
        public final boolean isLoadable()
        {
            return mIsLoadable;
        }

        /** @return <code>Class</code> associated with an object. */
        public final Class getClazz()
        {
            if (isLoadable() && mClass == null) {
                setClazz(AbstractTypeAwareCheck.this.tryLoadClass(getName()));
            }
            return mClass;
        }

        /**
         * Associates <code> Class</code> with an object.
         * @param aClass <code>Class</code> to associate with.
         */
        public final void setClazz(Class aClass)
        {
            mClass = aClass;
            mIsLoadable = (mClass != null);
        }
    }
}
