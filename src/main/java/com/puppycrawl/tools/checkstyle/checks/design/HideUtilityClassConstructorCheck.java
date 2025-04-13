///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.checks.design;

import java.util.Collections;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;

/**
 * <div>
 * Makes sure that utility classes (classes that contain only static methods or fields in their API)
 * do not have a public constructor.
 * </div>
 *
 * <p>
 * Rationale: Instantiating utility classes does not make sense.
 * Hence, the constructors should either be private or (if you want to allow subclassing) protected.
 * A common mistake is forgetting to hide the default constructor.
 * </p>
 *
 * <p>
 * If you make the constructor protected you may want to consider the following constructor
 * implementation technique to disallow instantiating subclasses:
 * </p>
 * <pre>
 * public class StringUtils // not final to allow subclassing
 * {
 *   protected StringUtils() {
 *     // prevents calls from subclass
 *     throw new UnsupportedOperationException();
 *   }
 *
 *   public static int count(char c, String s) {
 *     // ...
 *   }
 * }
 * </pre>
 * <ul>
 * <li>
 * Property {@code ignoreAnnotatedBy} - Ignore classes annotated
 * with the specified annotation(s). Annotation names provided in this property
 * must exactly match the annotation names on the classes. If the target class has annotations
 * specified with their fully qualified names (including package), the annotations in this
 * property should also be specified with their fully qualified names. Similarly, if the target
 * class has annotations specified with their simple names, this property should contain the
 * annotations with the same simple names.
 * Type is {@code java.lang.String[]}.
 * Default value is {@code ""}.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code hide.utility.class}
 * </li>
 * </ul>
 *
 * @since 3.1
 */
@StatelessCheck
public class HideUtilityClassConstructorCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "hide.utility.class";

    /**
     * Ignore classes annotated with the specified annotation(s). Annotation names
     * provided in this property must exactly match the annotation names on the classes.
     * If the target class has annotations specified with their fully qualified names
     * (including package), the annotations in this property should also be specified with
     * their fully qualified names. Similarly, if the target class has annotations specified
     * with their simple names, this property should contain the annotations with the same
     * simple names.
     */
    private Set<String> ignoreAnnotatedBy = Collections.emptySet();

    /**
     * Setter to ignore classes annotated with the specified annotation(s). Annotation names
     * provided in this property must exactly match the annotation names on the classes.
     * If the target class has annotations specified with their fully qualified names
     * (including package), the annotations in this property should also be specified with
     * their fully qualified names. Similarly, if the target class has annotations specified
     * with their simple names, this property should contain the annotations with the same
     * simple names.
     *
     * @param annotationNames specified annotation(s)
     * @since 10.20.0
     */
    public void setIgnoreAnnotatedBy(String... annotationNames) {
        ignoreAnnotatedBy = Set.of(annotationNames);
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {TokenTypes.CLASS_DEF};
    }

    @Override
    public void visitToken(DetailAST ast) {
        // abstract class could not have private constructor
        if (!isAbstract(ast) && !shouldIgnoreClass(ast)) {
            final boolean hasStaticModifier = isStatic(ast);

            final Details details = new Details(ast);
            details.invoke();

            final boolean hasDefaultCtor = details.isHasDefaultCtor();
            final boolean hasPublicCtor = details.isHasPublicCtor();
            final boolean hasNonStaticMethodOrField = details.isHasNonStaticMethodOrField();
            final boolean hasNonPrivateStaticMethodOrField =
                    details.isHasNonPrivateStaticMethodOrField();

            final boolean hasAccessibleCtor = hasDefaultCtor || hasPublicCtor;

            // figure out if class extends java.lang.object directly
            // keep it simple for now and get a 99% solution
            final boolean extendsJlo =
                ast.findFirstToken(TokenTypes.EXTENDS_CLAUSE) == null;

            final boolean isUtilClass = extendsJlo
                && !hasNonStaticMethodOrField && hasNonPrivateStaticMethodOrField;

            if (isUtilClass && hasAccessibleCtor && !hasStaticModifier) {
                log(ast, MSG_KEY);
            }
        }
    }

    /**
     * Returns true if given class is abstract or false.
     *
     * @param ast class definition for check.
     * @return true if a given class declared as abstract.
     */
    private static boolean isAbstract(DetailAST ast) {
        return ast.findFirstToken(TokenTypes.MODIFIERS)
            .findFirstToken(TokenTypes.ABSTRACT) != null;
    }

    /**
     * Returns true if given class is static or false.
     *
     * @param ast class definition for check.
     * @return true if a given class declared as static.
     */
    private static boolean isStatic(DetailAST ast) {
        return ast.findFirstToken(TokenTypes.MODIFIERS)
            .findFirstToken(TokenTypes.LITERAL_STATIC) != null;
    }

    /**
     * Checks if class is annotated by specific annotation(s) to skip.
     *
     * @param ast class to check
     * @return true if annotated by ignored annotations
     */
    private boolean shouldIgnoreClass(DetailAST ast) {
        return AnnotationUtil.containsAnnotation(ast, ignoreAnnotatedBy);
    }

    /**
     * Details of class that are required for validation.
     */
    private static final class Details {

        /** Class ast. */
        private final DetailAST ast;
        /** Result of details gathering. */
        private boolean hasNonStaticMethodOrField;
        /** Result of details gathering. */
        private boolean hasNonPrivateStaticMethodOrField;
        /** Result of details gathering. */
        private boolean hasDefaultCtor;
        /** Result of details gathering. */
        private boolean hasPublicCtor;

        /**
         * C-tor.
         *
         * @param ast class ast
         */
        private Details(DetailAST ast) {
            this.ast = ast;
        }

        /**
         * Getter.
         *
         * @return boolean
         */
        public boolean isHasNonStaticMethodOrField() {
            return hasNonStaticMethodOrField;
        }

        /**
         * Getter.
         *
         * @return boolean
         */
        public boolean isHasNonPrivateStaticMethodOrField() {
            return hasNonPrivateStaticMethodOrField;
        }

        /**
         * Getter.
         *
         * @return boolean
         */
        public boolean isHasDefaultCtor() {
            return hasDefaultCtor;
        }

        /**
         * Getter.
         *
         * @return boolean
         */
        public boolean isHasPublicCtor() {
            return hasPublicCtor;
        }

        /**
         * Main method to gather statistics.
         */
        public void invoke() {
            final DetailAST objBlock = ast.findFirstToken(TokenTypes.OBJBLOCK);
            hasDefaultCtor = true;
            DetailAST child = objBlock.getFirstChild();

            while (child != null) {
                final int type = child.getType();
                if (type == TokenTypes.METHOD_DEF
                        || type == TokenTypes.VARIABLE_DEF) {
                    final DetailAST modifiers =
                        child.findFirstToken(TokenTypes.MODIFIERS);
                    final boolean isStatic =
                        modifiers.findFirstToken(TokenTypes.LITERAL_STATIC) != null;

                    if (isStatic) {
                        final boolean isPrivate =
                                modifiers.findFirstToken(TokenTypes.LITERAL_PRIVATE) != null;

                        if (!isPrivate) {
                            hasNonPrivateStaticMethodOrField = true;
                        }
                    }
                    else {
                        hasNonStaticMethodOrField = true;
                    }
                }
                if (type == TokenTypes.CTOR_DEF) {
                    hasDefaultCtor = false;
                    final DetailAST modifiers =
                        child.findFirstToken(TokenTypes.MODIFIERS);
                    if (modifiers.findFirstToken(TokenTypes.LITERAL_PRIVATE) == null
                        && modifiers.findFirstToken(TokenTypes.LITERAL_PROTECTED) == null) {
                        // treat package visible as public
                        // for the purpose of this Check
                        hasPublicCtor = true;
                    }
                }
                child = child.getNextSibling();
            }
        }

    }

}
