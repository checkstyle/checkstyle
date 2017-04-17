////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtils;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * Base class for coupling calculation.
 *
 * @author <a href="mailto:simon@redhillconsulting.com.au">Simon Harris</a>
 * @author o_sukhodolsky
 */
public abstract class AbstractClassCouplingCheck extends AbstractCheck {
    /** A package separator - "." */
    private static final String DOT = ".";

    /** Class names to ignore. */
    private static final Set<String> DEFAULT_EXCLUDED_CLASSES = Collections.unmodifiableSet(
        Arrays.stream(new String[] {
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
        }).collect(Collectors.toSet()));

    /** Package names to ignore. */
    private static final Set<String> DEFAULT_EXCLUDED_PACKAGES = Collections.emptySet();

    /** User-configured regular expressions to ignore classes. */
    private final List<Pattern> excludeClassesRegexps = new ArrayList<>();

    /** User-configured class names to ignore. */
    private Set<String> excludedClasses = DEFAULT_EXCLUDED_CLASSES;
    /** User-configured package names to ignore. */
    private Set<String> excludedPackages = DEFAULT_EXCLUDED_PACKAGES;
    /** Allowed complexity. */
    private int max;

    /** Current file context. */
    private FileContext fileContext;

    /**
     * Creates new instance of the check.
     * @param defaultMax default value for allowed complexity.
     */
    protected AbstractClassCouplingCheck(int defaultMax) {
        max = defaultMax;
        excludeClassesRegexps.add(CommonUtils.createPattern("^$"));
    }

    /**
     * Returns message key we use for log violations.
     * @return message key we use for log violations.
     */
    protected abstract String getLogMessageId();

    @Override
    public final int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    /**
     * Returns allowed complexity.
     * @return allowed complexity.
     */
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
        this.excludedClasses =
            Collections.unmodifiableSet(Arrays.stream(excludedClasses).collect(Collectors.toSet()));
    }

    /**
     * Sets user-excluded regular expression of classes to ignore.
     * @param from array representing regular expressions of classes to ignore.
     */
    public void setExcludeClassesRegexps(String... from) {
        excludeClassesRegexps.clear();
        excludeClassesRegexps.addAll(Arrays.stream(from.clone())
                .map(CommonUtils::createPattern)
                .collect(Collectors.toSet()));
    }

    /**
     * Sets user-excluded pakcages to ignore. All exlcuded packages should end with a period,
     * so it also appends a dot to a package name.
     * @param excludedPackages the list of packages to ignore.
     */
    public final void setExcludedPackages(String... excludedPackages) {
        final List<String> invalidIdentifiers = Arrays.stream(excludedPackages)
            .filter(x -> !CommonUtils.isName(x))
            .collect(Collectors.toList());
        if (!invalidIdentifiers.isEmpty()) {
            throw new IllegalArgumentException(
                "the following values are not valid identifiers: "
                    + invalidIdentifiers.stream().collect(Collectors.joining(", ", "[", "]")));
        }

        this.excludedPackages = Collections.unmodifiableSet(
            Arrays.stream(excludedPackages).collect(Collectors.toSet()));
    }

    @Override
    public final void beginTree(DetailAST ast) {
        fileContext = new FileContext();
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.PACKAGE_DEF:
                visitPackageDef(ast);
                break;
            case TokenTypes.IMPORT:
                fileContext.registerImport(ast);
                break;
            case TokenTypes.CLASS_DEF:
            case TokenTypes.INTERFACE_DEF:
            case TokenTypes.ANNOTATION_DEF:
            case TokenTypes.ENUM_DEF:
                visitClassDef(ast);
                break;
            case TokenTypes.TYPE:
                fileContext.visitType(ast);
                break;
            case TokenTypes.LITERAL_NEW:
                fileContext.visitLiteralNew(ast);
                break;
            case TokenTypes.LITERAL_THROWS:
                fileContext.visitLiteralThrows(ast);
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
        final FullIdent ident = FullIdent.createFullIdent(pkg.getLastChild().getPreviousSibling());
        fileContext.setPackageName(ident.getText());
    }

    /**
     * Creates new context for a given class.
     * @param classDef class definition node.
     */
    private void visitClassDef(DetailAST classDef) {
        final String className = classDef.findFirstToken(TokenTypes.IDENT).getText();
        fileContext.createNewClassContext(className, classDef.getLineNo(), classDef.getColumnNo());
    }

    /** Restores previous context. */
    private void leaveClassDef() {
        fileContext.checkCurrentClassAndRestorePrevious();
    }

    /**
     * Encapsulates information about classes coupling inside single file.
     */
    private class FileContext {
        /** A map of (imported class name -> class name with package) pairs. */
        private final Map<String, String> importedClassPackage = new HashMap<>();

        /** Stack of class contexts. */
        private final Deque<ClassContext> classesContexts = new ArrayDeque<>();

        /** Current file package. */
        private String packageName = "";

        /** Current context. */
        private ClassContext classContext = new ClassContext(this, "", 0, 0);

        /**
         * Retrieves current file package name.
         * @return Package name.
         */
        public String getPackageName() {
            return packageName;
        }

        /**
         * Sets current context package name.
         * @param packageName Package name to be set.
         */
        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        /**
         * Registers given import. This allows us to track imported classes.
         * @param imp import definition.
         */
        public void registerImport(DetailAST imp) {
            final FullIdent ident = FullIdent.createFullIdent(
                imp.getLastChild().getPreviousSibling());
            final String fullName = ident.getText();
            if (fullName.charAt(fullName.length() - 1) != '*') {
                final int lastDot = fullName.lastIndexOf(DOT);
                importedClassPackage.put(fullName.substring(lastDot + 1), fullName);
            }
        }

        /**
         * Retrieves class name with packages. Uses previously registered imports to
         * get the full class name.
         * @param className Class name to be retrieved.
         * @return Class name with package name, if found, {@link Optional#empty()} otherwise.
         */
        public Optional<String> getClassNameWithPackage(String className) {
            return Optional.ofNullable(importedClassPackage.get(className));
        }

        /**
         * Creates new inner class context with given name and location.
         * @param className The class name.
         * @param lineNo The class line number.
         * @param columnNo The class column number.
         */
        public void createNewClassContext(String className, int lineNo, int columnNo) {
            classesContexts.push(classContext);
            classContext = new ClassContext(this, className, lineNo, columnNo);
        }

        /** Restores previous context. */
        public void checkCurrentClassAndRestorePrevious() {
            classContext.checkCoupling();
            classContext = classesContexts.pop();
        }

        /**
         * Visits type token for the current class context.
         * @param ast TYPE token.
         */
        public void visitType(DetailAST ast) {
            classContext.visitType(ast);
        }

        /**
         * Visits NEW token for the current class context.
         * @param ast NEW token.
         */
        public void visitLiteralNew(DetailAST ast) {
            classContext.visitLiteralNew(ast);
        }

        /**
         * Visits THROWS token for the current class context.
         * @param ast THROWS token.
         */
        public void visitLiteralThrows(DetailAST ast) {
            classContext.visitLiteralThrows(ast);
        }
    }

    /**
     * Encapsulates information about class coupling.
     *
     * @author <a href="mailto:simon@redhillconsulting.com.au">Simon Harris</a>
     * @author o_sukhodolsky
     */
    private class ClassContext {
        /** Parent file context. */
        private final FileContext parentContext;
        /**
         * Set of referenced classes.
         * Sorted by name for predictable error messages in unit tests.
         */
        private final Set<String> referencedClassNames = new TreeSet<>();
        /** Own class name. */
        private final String className;
        /* Location of own class. (Used to log violations) */
        /** Line number of class definition. */
        private final int lineNo;
        /** Column number of class definition. */
        private final int columnNo;

        /**
         * Create new context associated with given class.
         * @param parentContext Parent file context.
         * @param className name of the given class.
         * @param lineNo line of class definition.
         * @param columnNo column of class definition.
         */
        ClassContext(FileContext parentContext, String className, int lineNo, int columnNo) {
            this.parentContext = parentContext;
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
            addReferencedClassName(fullTypeName);
        }

        /**
         * Visits NEW.
         * @param ast NEW to process.
         */
        public void visitLiteralNew(DetailAST ast) {
            addReferencedClassName(ast.getFirstChild());
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
            referencedClassNames.remove(parentContext.getPackageName() + DOT + className);

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
            boolean result = !excludedClasses.contains(candidateClassName)
                && !isFromExcludedPackage(candidateClassName);
            if (result) {
                for (Pattern pattern : excludeClassesRegexps) {
                    if (pattern.matcher(candidateClassName).matches()) {
                        result = false;
                        break;
                    }
                }
            }
            return result;
        }

        /**
         * Checks if given class should be ignored as it belongs to excluded package.
         * @param candidateClassName class to check
         * @return true if we should not count this class.
         */
        private boolean isFromExcludedPackage(String candidateClassName) {
            String classNameWithPackage = candidateClassName;
            if (!candidateClassName.contains(DOT)) {
                classNameWithPackage = parentContext.getClassNameWithPackage(candidateClassName)
                    .orElse("");
            }
            boolean isFromExcludedPackage = false;
            if (classNameWithPackage.contains(DOT)) {
                final int lastDotIndex = classNameWithPackage.lastIndexOf(DOT);
                final String packageName = classNameWithPackage.substring(0, lastDotIndex);
                isFromExcludedPackage = packageName.startsWith("java.lang")
                    || excludedPackages.contains(packageName);
            }
            return isFromExcludedPackage;
        }
    }
}
