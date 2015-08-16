////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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
import com.puppycrawl.tools.checkstyle.api.Check;
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
 * class are potentially unstable.
 */
@Deprecated
public abstract class AbstractTypeAwareCheck extends Check {
    /** imports details **/
    private final Set<String> imports = Sets.newHashSet();

    /** full identifier for package of the method **/
    private FullIdent packageFullIdent;

    /** Name of current class. */
    private String currentClass;

    /** {@code ClassResolver} instance for current tree. */
    private ClassResolver classResolver;

    /** Stack of maps for type params. */
    private final Deque<Map<String, AbstractClassInfo>> typeParams = new ArrayDeque<>();

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
    private boolean logLoadErrors = true;

    /**
     * Whether to show class loading errors in the checkstyle report.
     * Request ID 1491630
     */
    private boolean suppressLoadErrors;

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

    /**
     * Called to process an AST when visiting it.
     * @param ast the AST to process. Guaranteed to not be PACKAGE_DEF or
     *             IMPORT tokens.
     */
    protected abstract void processAST(DetailAST ast);

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
        currentClass = "";
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
            int dotIdx = currentClass.lastIndexOf('$');
            if (dotIdx == -1) {
                // perhaps just a class
                dotIdx = currentClass.lastIndexOf('.');
            }
            if (dotIdx == -1) {
                // looks like a topmost class
                currentClass = "";
            }
            else {
                currentClass = currentClass.substring(0, dotIdx);
            }
            typeParams.pop();
        }
        else if (ast.getType() == TokenTypes.METHOD_DEF) {
            typeParams.pop();
        }
    }

    /**
     * Is exception is unchecked (subclass of {@code RuntimeException}
     * or {@code Error}
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
     * Checks if one class is subclass of another
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
            &&  parent.isAssignableFrom(child);
    }

    /** @return {@code ClassResolver} for current tree. */
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
     * @param currentClassName name of surrounding class.
     * @return the resolved class or {@code null}
     *          if unable to resolve the class.
     */
    protected final Class<?> resolveClass(String resolvableClassName,
            String currentClassName) {
        try {
            return getClassResolver().resolve(resolvableClassName, currentClassName);
        }
        catch (final ClassNotFoundException ignored) {
            return null;
        }
    }

    /**
     * Tries to load class. Logs error if unable.
     * @param ident name of class which we try to load.
     * @param currentClassName name of surrounding class.
     * @return {@code Class} for a ident.
     */
    protected final Class<?> tryLoadClass(Token ident, String currentClassName) {
        final Class<?> clazz = resolveClass(ident.getText(), currentClassName);
        if (clazz == null) {
            logLoadError(ident);
        }
        return clazz;
    }

    /**
     * Logs error if unable to load class information.
     * Abstract, should be overrided in subclasses.
     * @param ident class name for which we can no load class.
     */
    protected abstract void logLoadError(Token ident);

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

        if (params == null) {
            return;
        }

        for (DetailAST child = params.getFirstChild();
             child != null;
             child = child.getNextSibling()) {
            if (child.getType() == TokenTypes.TYPE_PARAMETER) {
                final DetailAST param = child;
                final String alias =
                    param.findFirstToken(TokenTypes.IDENT).getText();
                final DetailAST bounds =
                    param.findFirstToken(TokenTypes.TYPE_UPPER_BOUNDS);
                if (bounds != null) {
                    final FullIdent name =
                        FullIdent.createFullIdentBelow(bounds);
                    final AbstractClassInfo ci =
                        createClassInfo(new Token(name), getCurrentClassName());
                    paramsMap.put(alias, ci);
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

        if (!currentClass.isEmpty()) {
            innerClass = "$" + innerClass;
        }
        currentClass += innerClass;
        processTypeParams(ast);
    }

    /**
     * Returns current class.
     * @return name of current class.
     */
    protected final String getCurrentClassName() {
        return currentClass;
    }

    /**
     * Creates class info for given name.
     * @param name name of type.
     * @param surroundingClass name of surrounding class.
     * @return class infor for given name.
     */
    protected final AbstractClassInfo createClassInfo(final Token name,
                                              final String surroundingClass) {
        final AbstractClassInfo ci = findClassAlias(name.getText());
        if (ci != null) {
            return new ClassAlias(name, ci);
        }
        return new RegularClass(name, surroundingClass, this);
    }

    /**
     * Looking if a given name is alias.
     * @param name given name
     * @return ClassInfo for alias if it exists, null otherwise
     */
    protected final AbstractClassInfo findClassAlias(final String name) {
        AbstractClassInfo ci = null;
        final Iterator<Map<String, AbstractClassInfo>> iterator = typeParams.descendingIterator();
        while (iterator.hasNext()) {
            final Map<String, AbstractClassInfo> paramMap = iterator.next();
            ci = paramMap.get(name);
            if (ci != null) {
                break;
            }
        }
        return ci;
    }

    /**
     * Contains class's {@code Token}.
     */
    protected abstract static class AbstractClassInfo {
        /** {@code FullIdent} associated with this class. */
        private final Token name;

        /**
         * Creates new instance of class inforamtion object.
         * @param className token which represents class name.
         */
        protected AbstractClassInfo(final Token className) {
            if (className == null) {
                throw new IllegalArgumentException(
                    "ClassInfo's name should be non-null");
            }
            name = className;
        }

        /** @return class name */
        public final Token getName() {
            return name;
        }

        /** @return {@code Class} associated with an object. */
        public abstract Class<?> getClazz();
    }

    /** Represents regular classes/enumes. */
    @SuppressWarnings("deprecation")
    private static final class RegularClass extends AbstractClassInfo {
        /** name of surrounding class. */
        private final String surroundingClass;
        /** is class loadable. */
        private boolean loadable = true;
        /** {@code Class} object of this class if it's loadable. */
        private Class<?> classObj;
        /** the check we use to resolve classes. */
        private final AbstractTypeAwareCheck check;

        /**
         * Creates new instance of of class information object.
         * @param name {@code FullIdent} associated with new object.
         * @param surroundingClass name of current surrounding class.
         * @param check the check we use to load class.
         */
        public RegularClass(final Token name,
                             final String surroundingClass,
                             final AbstractTypeAwareCheck check) {
            super(name);
            this.surroundingClass = surroundingClass;
            this.check = check;
        }
        /** @return if class is loadable ot not. */
        private boolean isLoadable() {
            return loadable;
        }

        @Override
        public Class<?> getClazz() {
            if (isLoadable() && classObj == null) {
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
         * Creates nnew instance of the class.
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
            return "ClassAlias[alias " + getName()
                + " for " + classInfo + "]";
        }
    }

    /**
     * Represents text element with location in the text.
     */
    protected static class Token {
        /** token's column number. */
        private final int column;
        /** token's line number. */
        private final int line;
        /** token's text. */
        private final String text;

        /**
         * Creates token.
         * @param text token's text
         * @param line token's line number
         * @param column token's column number
         */
        public Token(String text, int line, int column) {
            this.text = text;
            this.line = line;
            this.column = column;
        }

        /**
         * Converts FullIdent to Token.
         * @param fullIdent full ident to convert.
         */
        public Token(FullIdent fullIdent) {
            text = fullIdent.getText();
            line = fullIdent.getLineNo();
            column = fullIdent.getColumnNo();
        }

        /** @return line number of the token */
        public int getLineNo() {
            return line;
        }

        /** @return column number of the token */
        public int getColumnNo() {
            return column;
        }

        /** @return text of the token */
        public String getText() {
            return text;
        }

        @Override
        public String toString() {
            return "Token[" + getText() + "(" + getLineNo()
                + "x" + getColumnNo() + ")]";
        }
    }
}
