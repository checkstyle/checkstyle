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

package com.puppycrawl.tools.checkstyle.checks.metrics;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * Base class for coupling calculation.
 *
 */
@FileStatefulCheck
public abstract class AbstractClassCouplingCheck extends AbstractCheck {

    /** A package separator - ".". */
    private static final char DOT = '.';

    /** Class names to ignore. */
    private static final Set<String> DEFAULT_EXCLUDED_CLASSES = Set.of(
        // reserved type name
        "var",
        // primitives
        "boolean", "byte", "char", "double", "float", "int",
        "long", "short", "void",
        // wrappers
        "Boolean", "Byte", "Character", "Double", "Float",
        "Integer", "Long", "Short", "Void",
        // java.lang.*
        "Object", "Class",
        "String", "StringBuffer", "StringBuilder",
        // Exceptions
        "ArrayIndexOutOfBoundsException", "Exception",
        "RuntimeException", "IllegalArgumentException",
        "IllegalStateException", "IndexOutOfBoundsException",
        "NullPointerException", "Throwable", "SecurityException",
        "UnsupportedOperationException",
        // java.util.*
        "List", "ArrayList", "Deque", "Queue", "LinkedList",
        "Set", "HashSet", "SortedSet", "TreeSet",
        "Map", "HashMap", "SortedMap", "TreeMap",
        "Override", "Deprecated", "SafeVarargs", "SuppressWarnings", "FunctionalInterface",
        "Collection", "EnumSet", "LinkedHashMap", "LinkedHashSet", "Optional",
        "OptionalDouble", "OptionalInt", "OptionalLong",
        // java.util.stream.*
        "DoubleStream", "IntStream", "LongStream", "Stream"
    );

    /** Package names to ignore. */
    private static final Set<String> DEFAULT_EXCLUDED_PACKAGES = Collections.emptySet();

    /** Pattern to match brackets in a full type name. */
    private static final Pattern BRACKET_PATTERN = Pattern.compile("\\[[^]]*]");

    /** Specify user-configured regular expressions to ignore classes. */
    private final List<Pattern> excludeClassesRegexps = new ArrayList<>();

    /** A map of (imported class name -&gt; class name with package) pairs. */
    private final Map<String, String> importedClassPackages = new HashMap<>();

    /** Stack of class contexts. */
    private final Deque<ClassContext> classesContexts = new ArrayDeque<>();

    /** Specify user-configured class names to ignore. */
    private Set<String> excludedClasses = DEFAULT_EXCLUDED_CLASSES;

    /**
     * Specify user-configured packages to ignore.
     */
    private Set<String> excludedPackages = DEFAULT_EXCLUDED_PACKAGES;

    /** Specify the maximum threshold allowed. */
    private int max;

    /** Current file package. */
    private String packageName;

    /**
     * Creates new instance of the check.
     *
     * @param defaultMax default value for allowed complexity.
     */
    protected AbstractClassCouplingCheck(int defaultMax) {
        max = defaultMax;
        excludeClassesRegexps.add(CommonUtil.createPattern("^$"));
    }

    /**
     * Returns message key we use for log violations.
     *
     * @return message key we use for log violations.
     */
    protected abstract String getLogMessageId();

    @Override
    public final int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    /**
     * Setter to specify the maximum threshold allowed.
     *
     * @param max allowed complexity.
     */
    public final void setMax(int max) {
        this.max = max;
    }

    /**
     * Setter to specify user-configured class names to ignore.
     *
     * @param excludedClasses classes to ignore.
     */
    public final void setExcludedClasses(String... excludedClasses) {
        this.excludedClasses = Set.of(excludedClasses);
    }

    /**
     * Setter to specify user-configured regular expressions to ignore classes.
     *
     * @param from array representing regular expressions of classes to ignore.
     */
    public void setExcludeClassesRegexps(Pattern... from) {
        excludeClassesRegexps.addAll(Arrays.asList(from));
    }

    /**
     * Setter to specify user-configured packages to ignore.
     *
     * @param excludedPackages packages to ignore.
     * @throws IllegalArgumentException if there are invalid identifiers among the packages.
     */
    public final void setExcludedPackages(String... excludedPackages) {
        final List<String> invalidIdentifiers = Arrays.stream(excludedPackages)
            .filter(Predicate.not(CommonUtil::isName))
            .collect(Collectors.toUnmodifiableList());
        if (!invalidIdentifiers.isEmpty()) {
            throw new IllegalArgumentException(
                "the following values are not valid identifiers: " + invalidIdentifiers);
        }

        this.excludedPackages = Set.of(excludedPackages);
    }

    @Override
    public final void beginTree(DetailAST ast) {
        importedClassPackages.clear();
        classesContexts.clear();
        classesContexts.push(new ClassContext("", null));
        packageName = "";
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.PACKAGE_DEF:
                visitPackageDef(ast);
                break;
            case TokenTypes.IMPORT:
                registerImport(ast);
                break;
            case TokenTypes.CLASS_DEF:
            case TokenTypes.INTERFACE_DEF:
            case TokenTypes.ANNOTATION_DEF:
            case TokenTypes.ENUM_DEF:
            case TokenTypes.RECORD_DEF:
                visitClassDef(ast);
                break;
            case TokenTypes.EXTENDS_CLAUSE:
            case TokenTypes.IMPLEMENTS_CLAUSE:
            case TokenTypes.TYPE:
                visitType(ast);
                break;
            case TokenTypes.LITERAL_NEW:
                visitLiteralNew(ast);
                break;
            case TokenTypes.LITERAL_THROWS:
                visitLiteralThrows(ast);
                break;
            case TokenTypes.ANNOTATION:
                visitAnnotationType(ast);
                break;
            default:
                throw new IllegalArgumentException("Unknown type: " + ast);
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        if (TokenUtil.isTypeDeclaration(ast.getType())) {
            leaveClassDef();
        }
    }

    /**
     * Stores package of current class we check.
     *
     * @param pkg package definition.
     */
    private void visitPackageDef(DetailAST pkg) {
        final FullIdent ident = FullIdent.createFullIdent(pkg.getLastChild().getPreviousSibling());
        packageName = ident.getText();
    }

    /**
     * Creates new context for a given class.
     *
     * @param classDef class definition node.
     */
    private void visitClassDef(DetailAST classDef) {
        final String className = classDef.findFirstToken(TokenTypes.IDENT).getText();
        createNewClassContext(className, classDef);
    }

    /** Restores previous context. */
    private void leaveClassDef() {
        checkCurrentClassAndRestorePrevious();
    }

    /**
     * Registers given import. This allows us to track imported classes.
     *
     * @param imp import definition.
     */
    private void registerImport(DetailAST imp) {
        final FullIdent ident = FullIdent.createFullIdent(
            imp.getLastChild().getPreviousSibling());
        final String fullName = ident.getText();
        final int lastDot = fullName.lastIndexOf(DOT);
        importedClassPackages.put(fullName.substring(lastDot + 1), fullName);
    }

    /**
     * Creates new inner class context with given name and location.
     *
     * @param className The class name.
     * @param ast The class ast.
     */
    private void createNewClassContext(String className, DetailAST ast) {
        classesContexts.push(new ClassContext(className, ast));
    }

    /** Restores previous context. */
    private void checkCurrentClassAndRestorePrevious() {
        classesContexts.pop().checkCoupling();
    }

    /**
     * Visits type token for the current class context.
     *
     * @param ast TYPE token.
     */
    private void visitType(DetailAST ast) {
        classesContexts.peek().visitType(ast);
    }

    /**
     * Visits NEW token for the current class context.
     *
     * @param ast NEW token.
     */
    private void visitLiteralNew(DetailAST ast) {
        classesContexts.peek().visitLiteralNew(ast);
    }

    /**
     * Visits THROWS token for the current class context.
     *
     * @param ast THROWS token.
     */
    private void visitLiteralThrows(DetailAST ast) {
        classesContexts.peek().visitLiteralThrows(ast);
    }

    /**
     * Visit ANNOTATION literal and get its type to referenced classes of context.
     *
     * @param annotationAST Annotation ast.
     */
    private void visitAnnotationType(DetailAST annotationAST) {
        final DetailAST children = annotationAST.getFirstChild();
        final DetailAST type = children.getNextSibling();
        classesContexts.peek().addReferencedClassName(type.getText());
    }

    /**
     * Encapsulates information about class coupling.
     *
     */
    private final class ClassContext {

        /**
         * Set of referenced classes.
         * Sorted by name for predictable violation messages in unit tests.
         */
        private final Set<String> referencedClassNames = new TreeSet<>();
        /** Own class name. */
        private final String className;
        /* Location of own class. (Used to log violations) */
        /** AST of class definition. */
        private final DetailAST classAst;

        /**
         * Create new context associated with given class.
         *
         * @param className name of the given class.
         * @param ast ast of class definition.
         */
        private ClassContext(String className, DetailAST ast) {
            this.className = className;
            classAst = ast;
        }

        /**
         * Visits throws clause and collects all exceptions we throw.
         *
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
         *
         * @param ast type to process.
         */
        public void visitType(DetailAST ast) {
            DetailAST child = ast.getFirstChild();
            while (child != null) {
                if (TokenUtil.isOfType(child, TokenTypes.IDENT, TokenTypes.DOT)) {
                    final String fullTypeName = FullIdent.createFullIdent(child).getText();
                    final String trimmed = BRACKET_PATTERN
                            .matcher(fullTypeName).replaceAll("");
                    addReferencedClassName(trimmed);
                }
                child = child.getNextSibling();
            }
        }

        /**
         * Visits NEW.
         *
         * @param ast NEW to process.
         */
        public void visitLiteralNew(DetailAST ast) {

            if (ast.getParent().getType() == TokenTypes.METHOD_REF) {
                addReferencedClassName(ast.getParent().getFirstChild());
            }
            else {
                addReferencedClassName(ast);
            }
        }

        /**
         * Adds new referenced class.
         *
         * @param ast a node which represents referenced class.
         */
        private void addReferencedClassName(DetailAST ast) {
            final String fullIdentName = FullIdent.createFullIdent(ast).getText();
            final String trimmed = BRACKET_PATTERN
                    .matcher(fullIdentName).replaceAll("");
            addReferencedClassName(trimmed);
        }

        /**
         * Adds new referenced class.
         *
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
            referencedClassNames.remove(packageName + DOT + className);

            if (referencedClassNames.size() > max) {
                log(classAst, getLogMessageId(),
                        referencedClassNames.size(), max,
                        referencedClassNames.toString());
            }
        }

        /**
         * Checks if given class shouldn't be ignored and not from java.lang.
         *
         * @param candidateClassName class to check.
         * @return true if we should count this class.
         */
        private boolean isSignificant(String candidateClassName) {
            return !excludedClasses.contains(candidateClassName)
                && !isFromExcludedPackage(candidateClassName)
                && !isExcludedClassRegexp(candidateClassName);
        }

        /**
         * Checks if given class should be ignored as it belongs to excluded package.
         *
         * @param candidateClassName class to check
         * @return true if we should not count this class.
         */
        private boolean isFromExcludedPackage(String candidateClassName) {
            String classNameWithPackage = candidateClassName;
            if (candidateClassName.indexOf(DOT) == -1) {
                classNameWithPackage = getClassNameWithPackage(candidateClassName)
                    .orElse("");
            }
            boolean isFromExcludedPackage = false;
            if (classNameWithPackage.indexOf(DOT) != -1) {
                final int lastDotIndex = classNameWithPackage.lastIndexOf(DOT);
                final String candidatePackageName =
                    classNameWithPackage.substring(0, lastDotIndex);
                isFromExcludedPackage = candidatePackageName.startsWith("java.lang")
                    || excludedPackages.contains(candidatePackageName);
            }
            return isFromExcludedPackage;
        }

        /**
         * Retrieves class name with packages. Uses previously registered imports to
         * get the full class name.
         *
         * @param examineClassName Class name to be retrieved.
         * @return Class name with package name, if found, {@link Optional#empty()} otherwise.
         */
        private Optional<String> getClassNameWithPackage(String examineClassName) {
            return Optional.ofNullable(importedClassPackages.get(examineClassName));
        }

        /**
         * Checks if given class should be ignored as it belongs to excluded class regexp.
         *
         * @param candidateClassName class to check.
         * @return true if we should not count this class.
         */
        private boolean isExcludedClassRegexp(String candidateClassName) {
            boolean result = false;
            for (Pattern pattern : excludeClassesRegexps) {
                if (pattern.matcher(candidateClassName).matches()) {
                    result = true;
                    break;
                }
            }
            return result;
        }
    }
}
