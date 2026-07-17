///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.imports;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks for unused import statements. An import statement
 * is considered unused if:
 * </div>
 *
 * <ul>
 * <li>
 * It is not referenced in the file. The algorithm does not support wild-card
 * imports like {@code import java.io.*;}. Most IDE's provide very sophisticated
 * checks for imports that handle wild-card imports.
 * </li>
 * <li>
 * The class imported is from the {@code java.lang} package. For example
 * importing {@code java.lang.String}.
 * </li>
 * <li>
 * The class imported is from the same package.
 * </li>
 * <li>
 * A static method is imported when used as method reference. In that case,
 * only the type needs to be imported and that's enough to resolve the method.
 * </li>
 * <li>
 * <b>Optionally:</b> it is referenced in Javadoc comments. This check is on by
 * default, but it is considered bad practice to introduce a compile-time
 * dependency for documentation purposes only. As an example, the import
 * {@code java.util.Set} would be considered referenced with the Javadoc
 * comment {@code {@link Set}}. The alternative to avoid introducing a compile-time
 * dependency would be to write the Javadoc comment as {@code {@link Set}}.
 * </li>
 * </ul>
 *
 * <p>
 * The main limitation of this check is handling the cases where:
 * </p>
 * <ul>
 * <li>
 * An imported type has the same name as a declaration, such as a member variable.
 * </li>
 * <li>
 * There are two or more static imports with the same method name
 * (javac can distinguish imports with same name but different parameters, but checkstyle can not
 * due to <a href="https://checkstyle.org/writingchecks.html#Limitations">limitation.</a>)
 * </li>
 * <li>
 * Module import declarations are used. Checkstyle does not resolve modules and therefore cannot
 * determine which packages or types are brought into scope by an {@code import module} declaration.
 * See <a href="https://checkstyle.org/writingchecks.html#Limitations">limitations.</a>
 * </li>
 * </ul>
 *
 * @since 3.0
 */
@FileStatefulCheck
@SuppressWarnings("UnrecognisedJavadocTag")
public class UnusedImportsCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "import.unused";

    /** Regexp pattern to match java.lang package. */
    private static final Pattern JAVA_LANG_PACKAGE_PATTERN =
        CommonUtil.createPattern("^java\\.lang\\.[a-zA-Z]+$");

    /** Suffix for the star import. */
    private static final String STAR_IMPORT_SUFFIX = ".*";

    /** Prefix for wildcard extends bound. */
    private static final String WILDCARD_EXTENDS_PREFIX = "? extends ";

    /** Prefix for wildcard super bound. */
    private static final String WILDCARD_SUPER_PREFIX = "? super ";

    /** Pattern for a valid Java identifier (parameter name). */
    private static final Pattern PARAM_NAME_PATTERN =
            Pattern.compile("[a-zA-Z_$][a-zA-Z0-9_$]*");

    /** Set of the imports. */
    private final Set<FullIdent> imports = new HashSet<>();

    /** Control whether to process Javadoc comments. */
    private boolean processJavadoc = true;

    /**
     * The scope is being processed.
     * Types declared in a scope can shadow imported types.
     */
    private Frame currentFrame;

    /**
     * Creates a new {@code UnusedImportsCheck} instance.
     */
    public UnusedImportsCheck() {
        // no code by default
    }
    /**
     * Setter to control whether to process Javadoc comments.
     *
     * @param value Flag for processing Javadoc comments.
     * @since 5.4
     */

    public void setProcessJavadoc(boolean value) {
        processJavadoc = value;
    }

    /**
     * Setter to control when to print violations if the Javadoc being examined by this check
     * violates the tight html rules defined at
     * <a href="https://checkstyle.org/writingjavadocchecks.html#Tight-HTML_rules">
     *     Tight-HTML Rules</a>.
     *
     * @param shouldReportViolation value to which the field shall be set to
     * @since 8.3
     * @propertySince 13.4.0
     */
    @Override
    public void setViolateExecutionOnNonTightHtml(boolean shouldReportViolation) {
        super.setViolateExecutionOnNonTightHtml(shouldReportViolation);
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        super.beginTree(rootAST);
        currentFrame = Frame.compilationUnit();
        imports.clear();
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        currentFrame.finish();
        // loop over all the imports to see if referenced.
        imports.stream()
            .filter(imprt -> isUnusedImport(imprt.getText()))
            .forEach(imprt -> log(imprt.getDetailAst(), MSG_KEY, imprt.getText()));
    }

    @Override
    public int[] getRequiredJavadocTokens() {
        return new int[] {
            JavadocCommentsTokenTypes.REFERENCE,
            JavadocCommentsTokenTypes.PARAMETER_TYPE,
            JavadocCommentsTokenTypes.THROWS_BLOCK_TAG,
            JavadocCommentsTokenTypes.EXCEPTION_BLOCK_TAG,
        };
    }

    @Override
    public int[] getDefaultJavadocTokens() {
        return getRequiredJavadocTokens();
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        switch (ast.getType()) {
            case JavadocCommentsTokenTypes.REFERENCE -> processReference(ast);
            case JavadocCommentsTokenTypes.PARAMETER_TYPE -> processParameterType(ast);
            case JavadocCommentsTokenTypes.THROWS_BLOCK_TAG,
                 JavadocCommentsTokenTypes.EXCEPTION_BLOCK_TAG -> processException(ast);
            default -> throw new IllegalArgumentException("Unknown javadoc token type " + ast);
        }

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
        return new int[] {
            TokenTypes.IDENT,
            TokenTypes.IMPORT,
            TokenTypes.STATIC_IMPORT,
            // Tokens for creating a new frame
            TokenTypes.OBJBLOCK,
            TokenTypes.SLIST,
            // Javadoc
            TokenTypes.BLOCK_COMMENT_BEGIN,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.IDENT -> processIdent(ast);
            case TokenTypes.IMPORT -> processImport(ast);
            case TokenTypes.STATIC_IMPORT -> processStaticImport(ast);
            case TokenTypes.OBJBLOCK, TokenTypes.SLIST -> currentFrame = currentFrame.push();
            case TokenTypes.BLOCK_COMMENT_BEGIN -> {
                if (processJavadoc) {
                    super.visitToken(ast);
                }
            }
            default -> throw new IllegalArgumentException("Unknown token type " + ast);
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        if (TokenUtil.isOfType(ast, TokenTypes.OBJBLOCK, TokenTypes.SLIST)) {
            currentFrame = currentFrame.pop();
        }
    }

    /**
     * Checks whether an import is unused.
     *
     * @param imprt an import.
     * @return true if an import is unused.
     */
    private boolean isUnusedImport(String imprt) {
        final Matcher javaLangPackageMatcher = JAVA_LANG_PACKAGE_PATTERN.matcher(imprt);
        return !currentFrame.isReferencedType(CommonUtil.baseClassName(imprt))
            || javaLangPackageMatcher.matches();
    }

    /**
     * Collects references made by IDENT.
     *
     * @param ast the IDENT node to process
     */
    private void processIdent(DetailAST ast) {
        final DetailAST parent = ast.getParent();
        final int parentType = parent.getType();

        // Ignore IDENTs that are part of the import statement itself
        final boolean collect = parentType != TokenTypes.IMPORT
                && parentType != TokenTypes.STATIC_IMPORT;

        if (collect) {
            final boolean isClassOrMethod = parentType == TokenTypes.DOT
                || parentType == TokenTypes.METHOD_DEF || parentType == TokenTypes.METHOD_REF;

            if (TokenUtil.isTypeDeclaration(parentType)) {
                currentFrame.addDeclaredType(ast.getText());
            }
            else if (!isClassOrMethod || isQualifiedIdentifier(ast)) {
                currentFrame.addReferencedType(ast.getText());
            }
        }
    }

    /**
     * Checks whether ast is a fully qualified identifier.
     *
     * @param ast to check
     * @return true if given ast is a fully qualified identifier
     */
    private static boolean isQualifiedIdentifier(DetailAST ast) {
        final DetailAST parent = ast.getParent();
        final int parentType = parent.getType();

        final boolean isQualifiedIdent = parentType == TokenTypes.DOT
                && !TokenUtil.isOfType(ast.getPreviousSibling(), TokenTypes.DOT)
                && ast.getNextSibling() != null;
        final boolean isQualifiedIdentFromMethodRef = parentType == TokenTypes.METHOD_REF
                && ast.getNextSibling() != null;
        return isQualifiedIdent || isQualifiedIdentFromMethodRef;
    }

    /**
     * Collects the details of imports.
     *
     * @param ast node containing the import details
     */
    private void processImport(DetailAST ast) {
        final FullIdent name = FullIdent.createFullIdentBelow(ast);
        if (!name.getText().endsWith(STAR_IMPORT_SUFFIX)) {
            imports.add(name);
        }
    }

    /**
     * Collects the details of static imports.
     *
     * @param ast node containing the static import details
     */
    private void processStaticImport(DetailAST ast) {
        final FullIdent name =
            FullIdent.createFullIdent(
                ast.getFirstChild().getNextSibling());
        if (!name.getText().endsWith(STAR_IMPORT_SUFFIX)) {
            imports.add(name);
        }
    }

    /**
     * Processes a Javadoc reference to record referenced types.
     *
     * @param ast the Javadoc reference node
     */
    private void processReference(DetailNode ast) {
        final String referenceText = topLevelType(ast.getFirstChild().getText());
        currentFrame.addReferencedType(referenceText);
    }

    /**
     * Processes a Javadoc parameter type tag to record referenced type.
     *
     * @param ast the Javadoc parameter type node
     */
    private void processParameterType(DetailNode ast) {
        addReferencedTypesFromType(ast.getText());
    }

    /**
     * Registers all type names referenced in a type string.
     * Handles generic type arguments, wildcard bounds, and array suffixes.
     *
     * @param type the type string to process
     */
    private void addReferencedTypesFromType(String type) {
        String currentType = type;
        if (currentType.startsWith(WILDCARD_EXTENDS_PREFIX)) {
            currentType = currentType.substring(WILDCARD_EXTENDS_PREFIX.length());
        }
        else if (currentType.startsWith(WILDCARD_SUPER_PREFIX)) {
            currentType = currentType.substring(WILDCARD_SUPER_PREFIX.length());
        }
        else {
            currentType = stripTrailingParameterName(currentType);
        }
        if (currentType.endsWith("[]")) {
            currentType = currentType.substring(0, currentType.length() - 2);
        }
        String outerType = stripTypeArguments(currentType);
        outerType = stripTrailingGt(outerType);
        outerType = topLevelType(outerType);
        currentFrame.addReferencedType(outerType);
        final int openIndex = currentType.indexOf('<');
        if (openIndex != -1) {
            final int closeIndex = findMatchingCloseAngle(currentType, openIndex);
            if (closeIndex != -1) {
                final String typeArgs = currentType.substring(openIndex + 1, closeIndex);
                for (String arg : splitTypeArguments(typeArgs)) {
                    addReferencedTypesFromType(arg);
                }
            }
        }
    }

    /**
     * Processes a Javadoc throws or exception tag to record referenced type.
     *
     * @param ast the Javadoc throws or exception node
     */
    private void processException(DetailNode ast) {
        final DetailNode ident =
                JavadocUtil.findFirstToken(ast, JavadocCommentsTokenTypes.IDENTIFIER);
        if (ident != null) {
            currentFrame.addReferencedType(ident.getText());
        }
    }

    /**
     * If the given type string contains "." (e.g. "Map.Entry"), returns the
     * top level type (e.g. "Map"), as that is what must be imported for the
     * type to resolve. Otherwise, returns the type as-is.
     *
     * @param type A possibly qualified type name
     * @return The simple name of the top level type
     */
    private static String topLevelType(String type) {
        String result = type;
        final int dotIndex = type.indexOf('.');
        if (dotIndex != -1) {
            result = type.substring(0, dotIndex);
        }
        return result;
    }

    /**
     * Strips generic type arguments from a type string.
     *
     * @param type A type string possibly containing type arguments
     * @return The type string with type arguments removed
     */
    private static String stripTypeArguments(final String type) {
        final int index = type.indexOf('<');
        final String result;
        if (index == -1) {
            result = type;
        }
        else {
            result = type.substring(0, index);
        }
        return result;
    }

    /**
     * Strips trailing {@code >} characters from a type string.
     * This handles tokenization artifacts where the closing angle bracket
     * of an enclosing generic is attached to the last parameter type.
     *
     * @param type A type string possibly ending with {@code >}
     * @return The type string with trailing {@code >} characters removed
     */
    private static String stripTrailingGt(String type) {
        String result = type;
        while (result.endsWith(">")) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    /**
     * Strips a trailing parameter name (e.g. &quot;outputTarget&quot; in
     * &quot;Result outputTarget&quot;) from a type token when the
     * Javadoc lexer merges the type and parameter name into a
     * single PARAMETER_TYPE token.
     *
     * <p>Only strips if the substring after the last space is a valid
     * Java identifier, which is true for a parameter name but false
     * for generic content such as {@code BigDecimal>} in
     * {@code Class<? extends BigDecimal>}.</p>
     *
     * @param type the raw token text
     * @return the type portion with any trailing parameter name removed
     */
    private static String stripTrailingParameterName(String type) {
        final int lastSpace = type.lastIndexOf(' ');
        String result = type;
        if (lastSpace != -1) {
            final String after = type.substring(lastSpace + 1);
            if (PARAM_NAME_PATTERN.matcher(after).matches()) {
                result = type.substring(0, lastSpace);
            }
        }
        return result;
    }

    /**
     * Finds the matching close angle bracket for the angle bracket at the given index.
     * Correctly handles nested angle brackets by tracking depth.
     *
     * @param str the string to search in
     * @param openIndex the index of the opening angle bracket
     * @return the index of the matching close angle bracket, or -1 if not found
     */
    private static int findMatchingCloseAngle(String str, int openIndex) {
        int depth = 0;
        int result = -1;
        for (int idx = openIndex; idx < str.length(); idx++) {
            if (str.charAt(idx) == '<') {
                depth++;
            }
            else if (str.charAt(idx) == '>') {
                depth--;
                if (depth == 0) {
                    result = idx;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Splits a type argument string into individual type argument strings.
     * Comma handling is deferred to a follow-up issue that absorbs commas into
     * PARAMETER_TYPE; currently commas are token boundaries so only one type
     * argument ever appears here.
     *
     * @param typeArgs the type argument string (content between angle brackets)
     * @return the list of individual type argument strings
     */
    private static List<String> splitTypeArguments(String typeArgs) {
        final List<String> result = new ArrayList<>();
        result.add(typeArgs);
        return result;
    }

    /**
     * Holds the names of referenced types and names of declared inner types.
     */
    private static final class Frame {

        /** Parent frame. */
        private final Frame parent;

        /** Nested types declared in the current scope. */
        private final Set<String> declaredTypes;

        /** Set of references - possibly to imports or locally declared types. */
        private final Set<String> referencedTypes;

        /**
         * Private constructor. Use {@link #compilationUnit()} to create a new top-level frame.
         *
         * @param parent the parent frame
         */
        private Frame(Frame parent) {
            this.parent = parent;
            declaredTypes = new HashSet<>();
            referencedTypes = new HashSet<>();
        }

        /**
         * Adds new inner type.
         *
         * @param type the type name
         */
        /* package */ void addDeclaredType(String type) {
            declaredTypes.add(type);
        }

        /**
         * Adds new type reference to the current frame.
         *
         * @param type the type name
         */
        /* package */ void addReferencedType(String type) {
            referencedTypes.add(type);
        }

        /**
         * Adds new inner types.
         *
         * @param types the type names
         */
        /* package */ void addReferencedTypes(Collection<String> types) {
            referencedTypes.addAll(types);
        }

        /**
         * Filters out all references to locally defined types.
         *
         */
        /* package */ void finish() {
            referencedTypes.removeAll(declaredTypes);
        }

        /**
         * Creates new inner frame.
         *
         * @return a new frame.
         */
        /* package */ Frame push() {
            return new Frame(this);
        }

        /**
         * Pulls all referenced types up, except those that are declared in this scope.
         *
         * @return the parent frame
         */
        /* package */ Frame pop() {
            finish();
            parent.addReferencedTypes(referencedTypes);
            return parent;
        }

        /**
         * Checks whether this type name is used in this frame.
         *
         * @param type the type name
         * @return {@code true} if the type is used
         */
        /* package */ boolean isReferencedType(String type) {
            return referencedTypes.contains(type);
        }

        /**
         * Creates a new top-level frame for the compilation unit.
         *
         * @return a new frame.
         */
        /* package */ static Frame compilationUnit() {
            return new Frame(null);
        }

    }

}
