////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FastStack;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import java.util.Map;
import java.util.Set;

/**
 * Abstract class that endeavours to maintain type information for the Java
 * file being checked. It provides helper methods for performing type
 * information functions.
 *
 * @author Oliver Burn
 * @version 1.0
 */
public abstract class AbstractTypeAwareCheck extends Check
{
    /** imports details **/
    private final Set<String> mImports = Sets.newHashSet();

    /** full identifier for package of the method **/
    private FullIdent mPackageFullIdent;

    /** Name of current class. */
    private String mCurrentClass;

    /** <code>ClassResolver</code> instance for current tree. */
    private ClassResolver mClassResolver;

    /** Stack of maps for type params. */
    private final FastStack<Map<String, ClassInfo>> mTypeParams =
        FastStack.newInstance();

    /**
     * Whether to log class loading errors to the checkstyle report
     * instead of throwing a RTE.
     *
     * Logging errors will avoid stopping checkstyle completely
     * because of a typo in javadoc. However, with modern IDEs that
     * support automated refactoring and generate javadoc this will
     * occur rarely, so by default we assume a configuration problem
     * in the checkstyle classpath and throw an execption.
     *
     * This configuration option was triggered by bug 1422462.
     */
    private boolean mLogLoadErrors = true;

    /**
     * Controls whether to log class loading errors to the checkstyle report
     * instead of throwing a RTE.
     *
     * @param aLogLoadErrors true if errors should be logged
     */
    public final void setLogLoadErrors(boolean aLogLoadErrors)
    {
        mLogLoadErrors = aLogLoadErrors;
    }

    /**
     * Whether to show class loading errors in the checkstyle report.
     * Request ID 1491630
     */
    private boolean mSuppressLoadErrors;

    /**
     * Controls whether to show class loading errors in the checkstyle report.
     *
     * @param aSuppressLoadErrors true if errors shouldn't be shown
     */
    public final void setSuppressLoadErrors(boolean aSuppressLoadErrors)
    {
        mSuppressLoadErrors = aSuppressLoadErrors;
    }

    /**
     * Called to process an AST when visiting it.
     * @param aAST the AST to process. Guaranteed to not be PACKAGE_DEF or
     *             IMPORT tokens.
     */
    protected abstract void processAST(DetailAST aAST);

    @Override
    public final int[] getRequiredTokens()
    {
        return new int[] {
            TokenTypes.PACKAGE_DEF,
            TokenTypes.IMPORT,
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
        };
    }

    @Override
    public void beginTree(DetailAST aRootAST)
    {
        mPackageFullIdent = FullIdent.createFullIdent(null);
        mImports.clear();
        // add java.lang.* since it's always imported
        mImports.add("java.lang.*");
        mClassResolver = null;
        mCurrentClass = "";
        mTypeParams.clear();
    }

    @Override
    public final void visitToken(DetailAST aAST)
    {
        if (aAST.getType() == TokenTypes.PACKAGE_DEF) {
            processPackage(aAST);
        }
        else if (aAST.getType() == TokenTypes.IMPORT) {
            processImport(aAST);
        }
        else if ((aAST.getType() == TokenTypes.CLASS_DEF)
                 || (aAST.getType() == TokenTypes.ENUM_DEF))
        {
            processClass(aAST);
        }
        else {
            if (aAST.getType() == TokenTypes.METHOD_DEF) {
                processTypeParams(aAST);
            }
            processAST(aAST);
        }
    }

    @Override
    public final void leaveToken(DetailAST aAST)
    {
        if ((aAST.getType() == TokenTypes.CLASS_DEF)
            || (aAST.getType() == TokenTypes.ENUM_DEF))
        {
            // perhaps it was inner class
            int dotIdx = mCurrentClass.lastIndexOf("$");
            if (dotIdx == -1) {
                // perhaps just a class
                dotIdx = mCurrentClass.lastIndexOf(".");
            }
            if (dotIdx == -1) {
                // looks like a topmost class
                mCurrentClass = "";
            }
            else {
                mCurrentClass = mCurrentClass.substring(0, dotIdx);
            }
            mTypeParams.pop();
        }
        else if (aAST.getType() == TokenTypes.METHOD_DEF) {
            mTypeParams.pop();
        }
        else if ((aAST.getType() != TokenTypes.PACKAGE_DEF)
                 && (aAST.getType() != TokenTypes.IMPORT))
        {
            leaveAST(aAST);
        }
    }

    /**
     * Called when exiting an AST. A no-op by default, extending classes
     * may choose to override this to augment their processing.
     * @param aAST the AST we are departing. Guaranteed to not be PACKAGE_DEF,
     *             CLASS_DEF, or IMPORT
     */
    protected void leaveAST(DetailAST aAST)
    {
    }

    /**
     * Is exception is unchecked (subclass of <code>RuntimeException</code>
     * or <code>Error</code>
     *
     * @param aException <code>Class</code> of exception to check
     * @return true  if exception is unchecked
     *         false if exception is checked
     */
    protected boolean isUnchecked(Class<?> aException)
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
    protected boolean isSubclass(Class<?> aChild, Class<?> aParent)
    {
        return (aParent != null) && (aChild != null)
            &&  aParent.isAssignableFrom(aChild);
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
     * @param aCurrentClass name of surrounding class.
     * @return the resolved class or <code>null</code>
     *          if unable to resolve the class.
     */
    protected final Class<?> resolveClass(String aClassName,
            String aCurrentClass)
    {
        try {
            return getClassResolver().resolve(aClassName, aCurrentClass);
        }
        catch (final ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Tries to load class. Logs error if unable.
     * @param aIdent name of class which we try to load.
     * @param aCurrentClass name of surrounding class.
     * @return <code>Class</code> for a ident.
     */
    protected final Class<?> tryLoadClass(Token aIdent, String aCurrentClass)
    {
        final Class<?> clazz = resolveClass(aIdent.getText(), aCurrentClass);
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
    protected abstract void logLoadError(Token aIdent);

    /**
     * Common implementation for logLoadError() method.
     * @param aLineNo line number of the problem.
     * @param aColumnNo column number of the problem.
     * @param aMsgKey message key to use.
     * @param aValues values to fill the message out.
     */
    protected final void logLoadErrorImpl(int aLineNo, int aColumnNo,
                                          String aMsgKey, Object... aValues)
    {
        if (!mLogLoadErrors) {
            final LocalizedMessage msg = new LocalizedMessage(aLineNo,
                                                    aColumnNo,
                                                    getMessageBundle(),
                                                    aMsgKey,
                                                    aValues,
                                                    getSeverityLevel(),
                                                    getId(),
                                                    this.getClass(),
                                                    null);
            throw new RuntimeException(msg.getMessage());
        }

        if (!mSuppressLoadErrors) {
            log(aLineNo, aColumnNo, aMsgKey, aValues);
        }
    }

    /**
     * Collects the details of a package.
     * @param aAST node containing the package details
     */
    private void processPackage(DetailAST aAST)
    {
        final DetailAST nameAST = aAST.getLastChild().getPreviousSibling();
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
     * Process type params (if any) for given class, enum or method.
     * @param aAST class, enum or method to process.
     */
    private void processTypeParams(DetailAST aAST)
    {
        final DetailAST typeParams =
            aAST.findFirstToken(TokenTypes.TYPE_PARAMETERS);

        final Map<String, ClassInfo> paramsMap = Maps.newHashMap();
        mTypeParams.push(paramsMap);

        if (typeParams == null) {
            return;
        }

        for (DetailAST child = typeParams.getFirstChild();
             child != null;
             child = child.getNextSibling())
        {
            if (child.getType() == TokenTypes.TYPE_PARAMETER) {
                final DetailAST param = child;
                final String alias =
                    param.findFirstToken(TokenTypes.IDENT).getText();
                final DetailAST bounds =
                    param.findFirstToken(TokenTypes.TYPE_UPPER_BOUNDS);
                if (bounds != null) {
                    final FullIdent name =
                        FullIdent.createFullIdentBelow(bounds);
                    final ClassInfo ci =
                        createClassInfo(new Token(name), getCurrentClassName());
                    paramsMap.put(alias, ci);
                }
            }
        }
    }

    /**
     * Processes class definition.
     * @param aAST class definition to process.
     */
    private void processClass(DetailAST aAST)
    {
        final DetailAST ident = aAST.findFirstToken(TokenTypes.IDENT);
        mCurrentClass += ("".equals(mCurrentClass) ? "" : "$")
            + ident.getText();

        processTypeParams(aAST);
    }

    /**
     * Returns current class.
     * @return name of current class.
     */
    protected final String getCurrentClassName()
    {
        return mCurrentClass;
    }

    /**
     * Creates class info for given name.
     * @param aName name of type.
     * @param aSurroundingClass name of surrounding class.
     * @return class infor for given name.
     */
    protected final ClassInfo createClassInfo(final Token aName,
                                              final String aSurroundingClass)
    {
        final ClassInfo ci = findClassAlias(aName.getText());
        if (ci != null) {
            return new ClassAlias(aName, ci);
        }
        return new RegularClass(aName, aSurroundingClass, this);
    }

    /**
     * Looking if a given name is alias.
     * @param aName given name
     * @return ClassInfo for alias if it exists, null otherwise
     */
    protected final ClassInfo findClassAlias(final String aName)
    {
        ClassInfo ci = null;
        for (int i = mTypeParams.size() - 1; i >= 0; i--) {
            final Map<String, ClassInfo> paramMap = mTypeParams.peek(i);
            ci = paramMap.get(aName);
            if (ci != null) {
                break;
            }
        }
        return ci;
    }

    /**
     * Contains class's <code>Token</code>.
     */
    protected abstract static class ClassInfo
    {
        /** <code>FullIdent</code> associated with this class. */
        private final Token mName;

        /** @return class name */
        public final Token getName()
        {
            return mName;
        }

        /** @return <code>Class</code> associated with an object. */
        public abstract Class<?> getClazz();

        /**
         * Creates new instance of class inforamtion object.
         * @param aName token which represents class name.
         */
        protected ClassInfo(final Token aName)
        {
            if (aName == null) {
                throw new NullPointerException(
                    "ClassInfo's name should be non-null");
            }
            mName = aName;
        }
    }

    /** Represents regular classes/enumes. */
    private static final class RegularClass extends ClassInfo
    {
        /** name of surrounding class. */
        private final String mSurroundingClass;
        /** is class loadable. */
        private boolean mIsLoadable = true;
        /** <code>Class</code> object of this class if it's loadable. */
        private Class<?> mClass;
        /** the check we use to resolve classes. */
        private final AbstractTypeAwareCheck mCheck;

        /**
         * Creates new instance of of class information object.
         * @param aName <code>FullIdent</code> associated with new object.
         * @param aSurroundingClass name of current surrounding class.
         * @param aCheck the check we use to load class.
         */
        private RegularClass(final Token aName,
                             final String aSurroundingClass,
                             final AbstractTypeAwareCheck aCheck)
        {
            super(aName);
            mSurroundingClass = aSurroundingClass;
            mCheck = aCheck;
        }
        /** @return if class is loadable ot not. */
        private boolean isLoadable()
        {
            return mIsLoadable;
        }

        @Override
        public Class<?> getClazz()
        {
            if (isLoadable() && (mClass == null)) {
                setClazz(mCheck.tryLoadClass(getName(), mSurroundingClass));
            }
            return mClass;
        }

        /**
         * Associates <code> Class</code> with an object.
         * @param aClass <code>Class</code> to associate with.
         */
        private void setClazz(Class<?> aClass)
        {
            mClass = aClass;
            mIsLoadable = (mClass != null);
        }

        @Override
        public String toString()
        {
            return "RegularClass[name=" + getName()
                + ", in class=" + mSurroundingClass
                + ", loadable=" + mIsLoadable
                + ", class=" + mClass + "]";
        }
    }

    /** Represents type param which is "alias" for real type. */
    private static class ClassAlias extends ClassInfo
    {
        /** Class information associated with the alias. */
        private final ClassInfo mClassInfo;

        /**
         * Creates nnew instance of the class.
         * @param aName token which represents name of class alias.
         * @param aClassInfo class information associated with the alias.
         */
        ClassAlias(final Token aName, ClassInfo aClassInfo)
        {
            super(aName);
            mClassInfo = aClassInfo;
        }

        @Override
        public final Class<?> getClazz()
        {
            return mClassInfo.getClazz();
        }

        @Override
        public String toString()
        {
            return "ClassAlias[alias " + getName()
                + " for " + mClassInfo + "]";
        }
    }

    /**
     * Represents text element with location in the text.
     */
    protected static class Token
    {
        /** token's column number. */
        private final int mColumn;
        /** token's line number. */
        private final int mLine;
        /** token's text. */
        private final String mText;

        /**
         * Creates token.
         * @param aText token's text
         * @param aLine token's line number
         * @param aColumn token's column number
         */
        public Token(String aText, int aLine, int aColumn)
        {
            mText = aText;
            mLine = aLine;
            mColumn = aColumn;
        }

        /**
         * Converts FullIdent to Token.
         * @param aFullIdent full ident to convert.
         */
        public Token(FullIdent aFullIdent)
        {
            mText = aFullIdent.getText();
            mLine = aFullIdent.getLineNo();
            mColumn = aFullIdent.getColumnNo();
        }

        /** @return line number of the token */
        public int getLineNo()
        {
            return mLine;
        }

        /** @return column number of the token */
        public int getColumnNo()
        {
            return mColumn;
        }

        /** @return text of the token */
        public String getText()
        {
            return mText;
        }

        @Override
        public String toString()
        {
            return "Token[" + getText() + "(" + getLineNo()
                + "x" + getColumnNo() + ")]";
        }
    }
}
