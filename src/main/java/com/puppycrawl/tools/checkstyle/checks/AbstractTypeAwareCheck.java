////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Abstract class that endeavours to maintain type information for the Java
 * file being checked. It provides helper methods for performing type
 * information functions.
 *
 * @author Oliver Burn
 * @deprecated Checkstyle is not type aware tool and all Checks derived from this
 *     class are potentially unstable.
 */
@Deprecated
public abstract class AbstractTypeAwareCheck extends AbstractCheck {
    /** Stack of maps for type params. */
    private final Deque<Map<String, AbstractClassInfo>> typeParams = new ArrayDeque<>();

    /** Imports details. **/
    private final Set<String> imports = Sets.newHashSet();

    /** Full identifier for package of the method. **/
    private FullIdent packageFullIdent;

    /** Name of current class. */
    private String currentClassName;

    /** {@code ClassResolver} instance for current tree. */
    private ClassResolver classResolver;

    /**
     * Whether to log class loading errors to the checkstyle report
     * instead of throwing a RTE.
     *
     * <p>Logging errors will avoid stopping checkstyle completely
     * because of a typo in javadoc. However, with modern IDEs that
     * support automated refactoring and generate javadoc this will
     * occur rarely, so by default we assume a configuration problem
     * in the checkstyle classpath and throw an exception.
     *
     * <p>This configuration option was triggered by bug 1422462.
     */
    private boolean logLoadErrors = true;

    /**
     * Whether to show class loading errors in the checkstyle report.
     * Request ID 1491630
     */
    private boolean suppressLoadErrors;

    /**
     * Called to process an AST when visiting it.
     * @param ast the AST to process. Guaranteed to not be PACKAGE_DEF or
     *             IMPORT tokens.
     */
    protected abstract void processAST(DetailAST ast);

    /**
     * Logs error if unable to load class information.
     * Abstract, should be overridden in subclasses.
     * @param ident class name for which we can no load class.
     */
    protected abstract void logLoadError(Token ident);

    /**
     * Controls whether to log class loading errors to the checkstyle report
     * instead of throwing a RTE.
     *
     * @param logLoadErrors true if errors should be logged
     */
    public final void setLogLoadErrors(boolean logLoadErrors) {
        this.logLoadErrors = logLoadErrors;
    }

    /**
     * Controls whether to show class loading errors in the checkstyle report.
     *
     * @param suppressLoadErrors true if errors shouldn't be shown
     */
    public final void setSuppressLoadErrors(boolean suppressLoadErrors) {
        this.suppressLoadErrors = suppressLoadErrors;
    }

    @Override
    public final int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.PACKAGE_DEF,
            TokenTypes.IMPORT,
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
        };
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        packageFullIdent = FullIdent.createFullIdent(null);
        imports.clear();
        // add java.lang.* since it's always imported
        imports.add("java.lang.*");
        classResolver = null;
        currentClassName = "";
        typeParams.clear();
    }

    @Override
    public final void visitToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.PACKAGE_DEF) {
            processPackage(ast);
        }
        else if (ast.getType() == TokenTypes.IMPORT) {
            processImport(ast);
        }
        else if (ast.getType() == TokenTypes.CLASS_DEF
                 || ast.getType() == TokenTypes.INTERFACE_DEF
                 || ast.getType() == TokenTypes.ENUM_DEF) {
            processClass(ast);
        }
        else {
            if (ast.getType() == TokenTypes.METHOD_DEF) {
                processTypeParams(ast);
            }
            processAST(ast);
        }
    }

    @Override
    public final void leaveToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.CLASS_DEF
            || ast.getType() == TokenTypes.ENUM_DEF) {
            // perhaps it was inner class
            int dotIdx = currentClassName.lastIndexOf('$');
            if (dotIdx == -1) {
                // perhaps just a class
                dotIdx = currentClassName.lastIndexOf('.');
            }
            if (dotIdx == -1) {
                // looks like a topmost class
                currentClassName = "";
            }
            else {
                currentClassName = currentClassName.substring(0, dotIdx);
            }
            typeParams.pop();
        }
        else if (ast.getType() == TokenTypes.METHOD_DEF) {
            typeParams.pop();
        }
    }

    /**
     * Is exception is unchecked (subclass of {@code RuntimeException}
     * or {@code Error}.
     *
     * @param exception {@code Class} of exception to check
     * @return true  if exception is unchecked
     *         false if exception is checked
     */
    protected static boolean isUnchecked(Class<?> exception) {
        return isSubclass(exception, RuntimeException.class)
            || isSubclass(exception, Error.class);
    }

    /**
     * Checks if one class is subclass of another.
     *
     * @param child {@code Class} of class
     *               which should be child
     * @param parent {@code Class} of class
     *                which should be parent
     * @return true  if aChild is subclass of aParent
     *         false otherwise
     */
    protected static boolean isSubclass(Class<?> child, Class<?> parent) {
        return parent != null && child != null
            && parent.isAssignableFrom(child);
    }

    /**
     * @return {@code ClassResolver} for current tree.
     */
    private ClassResolver getClassResolver() {
        if (classResolver == null) {
            classResolver =
                new ClassResolver(getClassLoader(),
                                  packageFullIdent.getText(),
                                  imports);
        }
        return classResolver;
    }

    /**
     * Attempts to resolve the Class for a specified name.
     * @param resolvableClassName name of the class to resolve
     * @param className name of surrounding class.
     * @return the resolved class or {@code null}
     *          if unable to resolve the class.
     */
    protected final Class<?> resolveClass(String resolvableClassName,
            String className) {
        try {
            return getClassResolver().resolve(resolvableClassName, className);
        }
        catch (final ClassNotFoundException ignored) {
            return null;
        }
    }

    /**
     * Tries to load class. Logs error if unable.
     * @param ident name of class which we try to load.
     * @param className name of surrounding class.
     * @return {@code Class} for a ident.
     */
    protected final Class<?> tryLoadClass(Token ident, String className) {
        final Class<?> clazz = resolveClass(ident.getText(), className);
        if (clazz == null) {
            logLoadError(ident);
        }
        return clazz;
    }

    /**
     * Common implementation for logLoadError() method.
     * @param lineNo line number of the problem.
     * @param columnNo column number of the problem.
     * @param msgKey message key to use.
     * @param values values to fill the message out.
     */
    protected final void logLoadErrorImpl(int lineNo, int columnNo,
                                          String msgKey, Object... values) {
        if (!logLoadErrors) {
            final LocalizedMessage msg = new LocalizedMessage(lineNo,
                                                    columnNo,
                                                    getMessageBundle(),
                                                    msgKey,
                                                    values,
                                                    getSeverityLevel(),
                                                    getId(),
                                                    getClass(),
                                                    null);
            throw new IllegalStateException(msg.getMessage());
        }

        if (!suppressLoadErrors) {
            log(lineNo, columnNo, msgKey, values);
        }
    }

    /**
     * Collects the details of a package.
     * @param ast node containing the package details
     */
    private void processPackage(DetailAST ast) {
        final DetailAST nameAST = ast.getLastChild().getPreviousSibling();
        packageFullIdent = FullIdent.createFullIdent(nameAST);
    }

    /**
     * Collects the details of imports.
     * @param ast node containing the import details
     */
    private void processImport(DetailAST ast) {
        final FullIdent name = FullIdent.createFullIdentBelow(ast);
        imports.add(name.getText());
    }

    /**
     * Process type params (if any) for given class, enum or method.
     * @param ast class, enum or method to process.
     */
    private void processTypeParams(DetailAST ast) {
        final DetailAST params =
            ast.findFirstToken(TokenTypes.TYPE_PARAMETERS);

        final Map<String, AbstractClassInfo> paramsMap = Maps.newHashMap();
        typeParams.push(paramsMap);

        if (params != null) {
            for (DetailAST child = params.getFirstChild();
                 child != null;
                 child = child.getNextSibling()) {
                if (child.getType() == TokenTypes.TYPE_PARAMETER) {
                    final String alias =
                        child.findFirstToken(TokenTypes.IDENT).getText();
                    final DetailAST bounds =
                        child.findFirstToken(TokenTypes.TYPE_UPPER_BOUNDS);
                    if (bounds != null) {
                        final FullIdent name =
                            FullIdent.createFullIdentBelow(bounds);
                        final AbstractClassInfo classInfo =
                            createClassInfo(new Token(name), currentClassName);
                        paramsMap.put(alias, classInfo);
                    }
                }
            }
        }
    }

    /**
     * Processes class definition.
     * @param ast class definition to process.
     */
    private void processClass(DetailAST ast) {
        final DetailAST ident = ast.findFirstToken(TokenTypes.IDENT);
        String innerClass = ident.getText();

        if (!currentClassName.isEmpty()) {
            innerClass = "$" + innerClass;
        }
        currentClassName += innerClass;
        processTypeParams(ast);
    }

    /**
     * Returns current class.
     * @return name of current class.
     */
    protected final String getCurrentClassName() {
        return currentClassName;
    }

    /**
     * Creates class info for given name.
     * @param name name of type.
     * @param surroundingClass name of surrounding class.
     * @return class info for given name.
     */
    protected final AbstractClassInfo createClassInfo(final Token name,
                                              final String surroundingClass) {
        final AbstractClassInfo classInfo = findClassAlias(name.getText());
        if (classInfo != null) {
            return new ClassAlias(name, classInfo);
        }
        return new RegularClass(name, surroundingClass, this);
    }

    /**
     * Looking if a given name is alias.
     * @param name given name
     * @return ClassInfo for alias if it exists, null otherwise
     */
    protected final AbstractClassInfo findClassAlias(final String name) {
        AbstractClassInfo classInfo = null;
        final Iterator<Map<String, AbstractClassInfo>> iterator = typeParams.descendingIterator();
        while (iterator.hasNext()) {
            final Map<String, AbstractClassInfo> paramMap = iterator.next();
            classInfo = paramMap.get(name);
            if (classInfo != null) {
                break;
            }
        }
        return classInfo;
    }

    /**
     * Contains class's {@code Token}.
     */
    protected abstract static class AbstractClassInfo {
        /** {@code FullIdent} associated with this class. */
        private final Token name;

        /**
         * Creates new instance of class information object.
         * @param className token which represents class name.
         */
        protected AbstractClassInfo(final Token className) {
            if (className == null) {
                throw new IllegalArgumentException(
                    "ClassInfo's name should be non-null");
            }
            name = className;
        }

        /**
         * @return {@code Class} associated with an object.
         */
        public abstract Class<?> getClazz();

        /**
         * Gets class name.
         * @return class name
         */
        public final Token getName() {
            return name;
        }
    }

    /** Represents regular classes/enums. */
    private static final class RegularClass extends AbstractClassInfo {
        /** Name of surrounding class. */
        private final String surroundingClass;
        /** The check we use to resolve classes. */
        private final AbstractTypeAwareCheck check;
        /** Is class loadable. */
        private boolean loadable = true;
        /** {@code Class} object of this class if it's loadable. */
        private Class<?> classObj;

        /**
         * Creates new instance of of class information object.
         * @param name {@code FullIdent} associated with new object.
         * @param surroundingClass name of current surrounding class.
         * @param check the check we use to load class.
         */
        RegularClass(final Token name,
                             final String surroundingClass,
                             final AbstractTypeAwareCheck check) {
            super(name);
            this.surroundingClass = surroundingClass;
            this.check = check;
        }

        @Override
        public Class<?> getClazz() {
            if (loadable && classObj == null) {
                setClazz(check.tryLoadClass(getName(), surroundingClass));
            }
            return classObj;
        }

        /**
         * Associates {@code Class} with an object.
         * @param clazz {@code Class} to associate with.
         */
        private void setClazz(Class<?> clazz) {
            classObj = clazz;
            loadable = clazz != null;
        }

        @Override
        public String toString() {
            return "RegularClass[name=" + getName()
                + ", in class=" + surroundingClass
                + ", loadable=" + loadable
                + ", class=" + classObj + "]";
        }
    }

    /** Represents type param which is "alias" for real type. */
    private static class ClassAlias extends AbstractClassInfo {
        /** Class information associated with the alias. */
        private final AbstractClassInfo classInfo;

        /**
         * Creates new instance of the class.
         * @param name token which represents name of class alias.
         * @param classInfo class information associated with the alias.
         */
        ClassAlias(final Token name, AbstractClassInfo classInfo) {
            super(name);
            this.classInfo = classInfo;
        }

        @Override
        public final Class<?> getClazz() {
            return classInfo.getClazz();
        }

        @Override
        public String toString() {
            return "ClassAlias[alias " + getName() + " for " + classInfo.getName() + "]";
        }
    }

    /**
     * Represents text element with location in the text.
     */
    protected static class Token {
        /** Token's column number. */
        private final int columnNo;
        /** Token's line number. */
        private final int lineNo;
        /** Token's text. */
        private final String text;

        /**
         * Creates token.
         * @param text token's text
         * @param lineNo token's line number
         * @param columnNo token's column number
         */
        public Token(String text, int lineNo, int columnNo) {
            this.text = text;
            this.lineNo = lineNo;
            this.columnNo = columnNo;
        }

        /**
         * Converts FullIdent to Token.
         * @param fullIdent full ident to convert.
         */
        public Token(FullIdent fullIdent) {
            text = fullIdent.getText();
            lineNo = fullIdent.getLineNo();
            columnNo = fullIdent.getColumnNo();
        }

        /**
         * Gets line number of the token.
         * @return line number of the token
         */
        public int getLineNo() {
            return lineNo;
        }

        /**
         * Gets column number of the token.
         * @return column number of the token
         */
        public int getColumnNo() {
            return columnNo;
        }

        /**
         * Gets text of the token.
         * @return text of the token
         */
        public String getText() {
            return text;
        }

        @Override
        public String toString() {
            return "Token[" + text + "(" + lineNo
                + "x" + columnNo + ")]";
        }
    }
}
