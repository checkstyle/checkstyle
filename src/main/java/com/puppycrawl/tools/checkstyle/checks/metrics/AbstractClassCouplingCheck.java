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

package com.puppycrawl.tools.checkstyle.checks.metrics;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.CheckUtils;

/**
 * Base class for coupling calculation.
 *
 * @author <a href="mailto:simon@redhillconsulting.com.au">Simon Harris</a>
 * @author o_sukhodolsky
 */
public abstract class AbstractClassCouplingCheck extends Check {
    /** Class names to ignore. */
    private static final Set<String> DEFAULT_EXCLUDED_CLASSES =
                ImmutableSet.<String>builder()
                // primitives
                .add("boolean", "byte", "char", "double", "float", "int")
                .add("long", "short", "void")
                // wrappers
                .add("Boolean", "Byte", "Character", "Double", "Float")
                .add("Integer", "Long", "Short", "Void")
                // java.lang.*
                .add("Object", "Class")
                .add("String", "StringBuffer", "StringBuilder")
                // Exceptions
                .add("ArrayIndexOutOfBoundsException", "Exception")
                .add("RuntimeException", "IllegalArgumentException")
                .add("IllegalStateException", "IndexOutOfBoundsException")
                .add("NullPointerException", "Throwable", "SecurityException")
                .add("UnsupportedOperationException")
                // java.util.*
                .add("List", "ArrayList", "Deque", "Queue", "LinkedList")
                .add("Set", "HashSet", "SortedSet", "TreeSet")
                .add("Map", "HashMap", "SortedMap", "TreeMap")
                .build();
    /** User-configured class names to ignore. */
    private Set<String> excludedClasses = DEFAULT_EXCLUDED_CLASSES;
    /** Allowed complexity. */
    private int max;
    /** package of the file we check. */
    private String packageName;

    /** Stack of contexts. */
    private final Deque<Context> contextStack = new ArrayDeque<>();
    /** Current context. */
    private Context context = new Context("", 0, 0);

    /**
     * Creates new instance of the check.
     * @param defaultMax default value for allowed complexity.
     */
    protected AbstractClassCouplingCheck(int defaultMax) {
        max = defaultMax;
    }

    @Override
    public final int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    /** @return allowed complexity. */
    public final int getMax() {
        return max;
    }

    /**
     * Sets maximum allowed complexity.
     * @param max allowed complexity.
     */
    public final void setMax(int max) {
        this.max = max;
    }

    /**
     * Sets user-excluded classes to ignore.
     * @param excludedClasses the list of classes to ignore.
     */
    public final void setExcludedClasses(String... excludedClasses) {
        this.excludedClasses = ImmutableSet.copyOf(excludedClasses);
    }

    @Override
    public final void beginTree(DetailAST ast) {
        packageName = "";
    }

    /** @return message key we use for log violations. */
    protected abstract String getLogMessageId();

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.PACKAGE_DEF:
                visitPackageDef(ast);
                break;
            case TokenTypes.CLASS_DEF:
            case TokenTypes.INTERFACE_DEF:
            case TokenTypes.ANNOTATION_DEF:
            case TokenTypes.ENUM_DEF:
                visitClassDef(ast);
                break;
            case TokenTypes.TYPE:
                context.visitType(ast);
                break;
            case TokenTypes.LITERAL_NEW:
                context.visitLiteralNew(ast);
                break;
            case TokenTypes.LITERAL_THROWS:
                context.visitLiteralThrows(ast);
                break;
            default:
                throw new IllegalArgumentException("Unknown type: " + ast);
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.CLASS_DEF:
            case TokenTypes.INTERFACE_DEF:
            case TokenTypes.ANNOTATION_DEF:
            case TokenTypes.ENUM_DEF:
                leaveClassDef();
                break;
            default:
                // Do nothing
        }
    }

    /**
     * Stores package of current class we check.
     * @param pkg package definition.
     */
    private void visitPackageDef(DetailAST pkg) {
        final FullIdent ident = FullIdent.createFullIdent(pkg.getLastChild()
                .getPreviousSibling());
        packageName = ident.getText();
    }

    /**
     * Creates new context for a given class.
     * @param classDef class definition node.
     */
    private void visitClassDef(DetailAST classDef) {
        contextStack.push(context);
        final String className =
            classDef.findFirstToken(TokenTypes.IDENT).getText();
        context = new Context(className,
                               classDef.getLineNo(),
                               classDef.getColumnNo());
    }

    /** Restores previous context. */
    private void leaveClassDef() {
        context.checkCoupling();
        context = contextStack.pop();
    }

    /**
     * Incapsulates information about class coupling.
     *
     * @author <a href="mailto:simon@redhillconsulting.com.au">Simon Harris</a>
     * @author o_sukhodolsky
     */
    private class Context {
        /**
         * Set of referenced classes.
         * Sorted by name for predictable error messages in unit tests.
         */
        private final Set<String> referencedClassNames = Sets.newTreeSet();
        /** Own class name. */
        private final String className;
        /* Location of own class. (Used to log violations) */
        /** Line number of class definition. */
        private final int lineNo;
        /** Column number of class definition. */
        private final int columnNo;

        /**
         * Create new context associated with given class.
         * @param className name of the given class.
         * @param lineNo line of class definition.
         * @param columnNo column of class definition.
         */
        public Context(String className, int lineNo, int columnNo) {
            this.className = className;
            this.lineNo = lineNo;
            this.columnNo = columnNo;
        }

        /**
         * Visits throws clause and collects all exceptions we throw.
         * @param literalThrows throws to process.
         */
        public void visitLiteralThrows(DetailAST literalThrows) {
            for (DetailAST childAST = literalThrows.getFirstChild();
                 childAST != null;
                 childAST = childAST.getNextSibling()) {
                if (childAST.getType() != TokenTypes.COMMA) {
                    addReferencedClassName(childAST);
                }
            }
        }

        /**
         * Visits type.
         * @param ast type to process.
         */
        public void visitType(DetailAST ast) {
            final String fullTypeName = CheckUtils.createFullType(ast).getText();
            context.addReferencedClassName(fullTypeName);
        }

        /**
         * Visits NEW.
         * @param ast NEW to process.
         */
        public void visitLiteralNew(DetailAST ast) {
            context.addReferencedClassName(ast.getFirstChild());
        }

        /**
         * Adds new referenced class.
         * @param ast a node which represents referenced class.
         */
        private void addReferencedClassName(DetailAST ast) {
            final String fullIdentName = FullIdent.createFullIdent(ast).getText();
            addReferencedClassName(fullIdentName);
        }

        /**
         * Adds new referenced class.
         * @param referencedClassName class name of the referenced class.
         */
        private void addReferencedClassName(String referencedClassName) {
            if (isSignificant(referencedClassName)) {
                referencedClassNames.add(referencedClassName);
            }
        }

        /** Checks if coupling less than allowed or not. */
        public void checkCoupling() {
            referencedClassNames.remove(className);
            referencedClassNames.remove(packageName + "." + className);

            if (referencedClassNames.size() > max) {
                log(lineNo, columnNo, getLogMessageId(),
                        referencedClassNames.size(), getMax(),
                        referencedClassNames.toString());
            }
        }

        /**
         * Checks if given class shouldn't be ignored and not from java.lang.
         * @param candidateClassName class to check.
         * @return true if we should count this class.
         */
        private boolean isSignificant(String candidateClassName) {
            return !excludedClasses.contains(candidateClassName)
                    && !candidateClassName.startsWith("java.lang.");
        }
    }
}
