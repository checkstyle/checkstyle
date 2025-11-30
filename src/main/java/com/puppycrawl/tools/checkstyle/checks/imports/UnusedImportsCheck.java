///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.imports;

import java.util.Collection;
import java.util.HashSet;
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
 * {@code java.util.List} would be considered referenced with the Javadoc
 * comment {@code {@link List}}. The alternative to avoid introducing a compile-time
 * dependency would be to write the Javadoc comment as {@code {&#64;link java.util.List}}.
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
 * @noinspection JavadocReference
 * @noinspectionreason IntelliJ treats {@code {@link List}} in this example as a real reference,
 *     but the import is intentionally omitted since it is an unused-import case.
 * @since 3.0
 */
@FileStatefulCheck
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
     * Setter to control whether to process Javadoc comments.
     *
     * @param value Flag for processing Javadoc comments.
     * @since 5.4
     */
    public void setProcessJavadoc(boolean value) {
        processJavadoc = value;
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
    public int[] getDefaultJavadocTokens() {
        return new int[] {
            JavadocCommentsTokenTypes.REFERENCE,
            JavadocCommentsTokenTypes.PARAMETER_TYPE,
            JavadocCommentsTokenTypes.THROWS_BLOCK_TAG,
        };
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        switch (ast.getType()) {
            case JavadocCommentsTokenTypes.REFERENCE -> processReference(ast);
            case JavadocCommentsTokenTypes.PARAMETER_TYPE -> processParameterType(ast);
            case JavadocCommentsTokenTypes.THROWS_BLOCK_TAG -> processThrows(ast);
            default -> throw new IllegalArgumentException("Unknown token type " + ast);
        }
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
            default -> throw new IllegalArgumentException("Unknown javadoc token type " + ast);
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
     * Validates the class and optional member part before adding
     * the type to the current frame.
     *
     * @param ast the Javadoc reference node
     */
    private void processReference(DetailNode ast) {
        final String referenceText = topLevelType(ast.getFirstChild().getText());
        final boolean isClassValid = isValidIdentifier(referenceText);

        final DetailNode memberReference = ast
                .findFirstToken(JavadocCommentsTokenTypes.MEMBER_REFERENCE);
        // Default to true if no member exists
        boolean isMemberValid = true;

        if (memberReference != null) {
            final String memberText = memberReference.getFirstChild().getText();
            isMemberValid = isValidIdentifier(memberText);
        }

        if (isClassValid && isMemberValid) {
            currentFrame.addReferencedType(referenceText);
        }
    }

    /**
     * Processes a Javadoc parameter type tag to record referenced type.
     *
     * @param ast the Javadoc parameter type node
     */
    private void processParameterType(DetailNode ast) {
        String parameterTypeText = topLevelType(ast.getText());
        if (parameterTypeText.endsWith("[]")) {
            parameterTypeText = parameterTypeText.substring(0, parameterTypeText.length() - 2);
        }
        currentFrame.addReferencedType(parameterTypeText);
    }

    /**
     * Processes a Javadoc throws tag to record referenced type.
     *
     * @param ast the Javadoc throws node
     */
    private void processThrows(DetailNode ast) {
        final DetailNode ident = ast.findFirstToken(JavadocCommentsTokenTypes.IDENTIFIER);
        if (ident != null) {
            currentFrame.addReferencedType(ident.getText());
        }
    }

    /**
     * Checks if the provided string starts as a valid Java identifier.
     *
     * @param identifier the string to check
     * @return true if the string starts as a valid Java identifier, false otherwise
     */
    private static boolean isValidIdentifier(String identifier) {
        boolean isValid = false;
        if (!identifier.isEmpty()) {
            final int character = identifier.codePointAt(0);
            isValid = Character.isJavaIdentifierStart(character);
        }
        return isValid;
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
        final String topLevelType;
        final int dotIndex = type.indexOf('.');
        if (dotIndex == -1) {
            topLevelType = type;
        }
        else {
            topLevelType = type.substring(0, dotIndex);
        }
        return topLevelType;
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
        public void addDeclaredType(String type) {
            declaredTypes.add(type);
        }

        /**
         * Adds new type reference to the current frame.
         *
         * @param type the type name
         */
        public void addReferencedType(String type) {
            referencedTypes.add(type);
        }

        /**
         * Adds new inner types.
         *
         * @param types the type names
         */
        public void addReferencedTypes(Collection<String> types) {
            referencedTypes.addAll(types);
        }

        /**
         * Filters out all references to locally defined types.
         *
         */
        public void finish() {
            referencedTypes.removeAll(declaredTypes);
        }

        /**
         * Creates new inner frame.
         *
         * @return a new frame.
         */
        public Frame push() {
            return new Frame(this);
        }

        /**
         * Pulls all referenced types up, except those that are declared in this scope.
         *
         * @return the parent frame
         */
        public Frame pop() {
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
        public boolean isReferencedType(String type) {
            return referencedTypes.contains(type);
        }

        /**
         * Creates a new top-level frame for the compilation unit.
         *
         * @return a new frame.
         */
        public static Frame compilationUnit() {
            return new Frame(null);
        }

    }

}
