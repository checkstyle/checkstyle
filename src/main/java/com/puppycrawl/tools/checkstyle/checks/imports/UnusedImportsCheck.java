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
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.checkerframework.checker.regex.qual.Regex;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTag;
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
 * </ul>
 * <ul>
 * <li>
 * Property {@code processJavadoc} - Control whether to process Javadoc comments.
 * Type is {@code boolean}.
 * Default value is {@code true}.
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
 * {@code import.unused}
 * </li>
 * </ul>
 *
 * @since 3.0
 */
@FileStatefulCheck
public class UnusedImportsCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "import.unused";

    /** Regex to match class names. */
    private static final Pattern CLASS_NAME = CommonUtil.createPattern(
           "((:?[\\p{L}_$][\\p{L}\\p{N}_$]*\\.)*[\\p{L}_$][\\p{L}\\p{N}_$]*)");
    /** Regex to match the first class name. */
    private static final Pattern FIRST_CLASS_NAME = CommonUtil.createPattern(
           "^" + CLASS_NAME);
    /** Regex to match argument names. */
    private static final Pattern ARGUMENT_NAME = CommonUtil.createPattern(
           "[(,]\\s*" + CLASS_NAME.pattern());

    /** Regexp pattern to match java.lang package. */
    private static final Pattern JAVA_LANG_PACKAGE_PATTERN =
        CommonUtil.createPattern("^java\\.lang\\.[a-zA-Z]+$");

    /** Number of groups of reference pattern. */
    private static final int REFERENCE_GROUPS = 3;

    /** Reference pattern. */
    private static final @Regex(REFERENCE_GROUPS) Pattern REFERENCE = Pattern.compile(
            "^([a-z_$][a-z\\d_$<>.]*)?(#(.*))?$",
            Pattern.CASE_INSENSITIVE
    );

    /** Method pattern. */
    private static final Pattern METHOD = Pattern.compile(
            "^([a-z_$#][a-z\\d_$]*)(\\([^)]*\\))?$",
            Pattern.CASE_INSENSITIVE
    );

    /** Suffix for the star import. */
    private static final String STAR_IMPORT_SUFFIX = ".*";

    /** Set of the imports. */
    private final Set<FullIdent> imports = new HashSet<>();

    /** Flag to indicate when time to start collecting references. */
    private boolean collect;
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
        collect = false;
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
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.IDENT,
            TokenTypes.IMPORT,
            TokenTypes.STATIC_IMPORT,
            // Definitions that may contain Javadoc...
            TokenTypes.PACKAGE_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
            // Tokens for creating a new frame
            TokenTypes.OBJBLOCK,
            TokenTypes.SLIST,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.IDENT:
                if (collect) {
                    processIdent(ast);
                }
                break;
            case TokenTypes.IMPORT:
                processImport(ast);
                break;
            case TokenTypes.STATIC_IMPORT:
                processStaticImport(ast);
                break;
            case TokenTypes.OBJBLOCK:
            case TokenTypes.SLIST:
                currentFrame = currentFrame.push();
                break;
            default:
                collect = true;
                if (processJavadoc) {
                    collectReferencesFromJavadoc(ast);
                }
                break;
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

        final boolean isClassOrMethod = parentType == TokenTypes.DOT
                || parentType == TokenTypes.METHOD_DEF || parentType == TokenTypes.METHOD_REF;

        if (TokenUtil.isTypeDeclaration(parentType)) {
            currentFrame.addDeclaredType(ast.getText());
        }
        else if (!isClassOrMethod || isQualifiedIdentifier(ast)) {
            currentFrame.addReferencedType(ast.getText());
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
     * Collects references made in Javadoc comments.
     *
     * @param ast node to inspect for Javadoc
     */
    // suppress deprecation until https://github.com/checkstyle/checkstyle/issues/11166
    @SuppressWarnings("deprecation")
    private void collectReferencesFromJavadoc(DetailAST ast) {
        final FileContents contents = getFileContents();
        final int lineNo = ast.getLineNo();
        final TextBlock textBlock = contents.getJavadocBefore(lineNo);
        if (textBlock != null) {
            currentFrame.addReferencedTypes(collectReferencesFromJavadoc(textBlock));
        }
    }

    /**
     * Process a javadoc {@link TextBlock} and return the set of classes
     * referenced within.
     *
     * @param textBlock The javadoc block to parse
     * @return a set of classes referenced in the javadoc block
     */
    private static Set<String> collectReferencesFromJavadoc(TextBlock textBlock) {
        // Process INLINE tags
        final List<JavadocTag> inlineTags = getTargetTags(textBlock,
                JavadocUtil.JavadocTagType.INLINE);
        // Process BLOCK tags
        final List<JavadocTag> blockTags = getTargetTags(textBlock,
                JavadocUtil.JavadocTagType.BLOCK);
        final List<JavadocTag> targetTags = Stream.concat(inlineTags.stream(), blockTags.stream())
                .collect(Collectors.toUnmodifiableList());

        final Set<String> references = new HashSet<>();

        targetTags.stream()
            .filter(JavadocTag::canReferenceImports)
            .forEach(tag -> references.addAll(processJavadocTag(tag)));
        return references;
    }

    /**
     * Returns the list of valid tags found in a javadoc {@link TextBlock}.
     * Filters tags based on whether they are inline or block tags, ensuring they match
     * the correct format supported.
     *
     * @param cmt The javadoc block to parse
     * @param javadocTagType The type of tags we're interested in
     * @return the list of tags
     */
    private static List<JavadocTag> getTargetTags(TextBlock cmt,
            JavadocUtil.JavadocTagType javadocTagType) {
        return JavadocUtil.getJavadocTags(cmt, javadocTagType)
            .getValidTags()
            .stream()
            .filter(tag -> isMatchingTagType(tag, javadocTagType))
            .map(UnusedImportsCheck::bestTryToMatchReference)
            .flatMap(Optional::stream)
            .collect(Collectors.toUnmodifiableList());
    }

    /**
     * Returns a list of references that found in a javadoc {@link JavadocTag}.
     *
     * @param tag The javadoc tag to parse
     * @return A list of references that found in this tag
     */
    private static Set<String> processJavadocTag(JavadocTag tag) {
        final Set<String> references = new HashSet<>();
        final String identifier = tag.getFirstArg();
        for (Pattern pattern : new Pattern[]
        {FIRST_CLASS_NAME, ARGUMENT_NAME}) {
            references.addAll(matchPattern(identifier, pattern));
        }
        return references;
    }

    /**
     * Extracts a set of texts matching a {@link Pattern} from a
     * {@link String}.
     *
     * @param identifier The String to match the pattern against
     * @param pattern The Pattern used to extract the texts
     * @return A set of texts which matched the pattern
     */
    private static Set<String> matchPattern(String identifier, Pattern pattern) {
        final Set<String> references = new HashSet<>();
        final Matcher matcher = pattern.matcher(identifier);
        while (matcher.find()) {
            references.add(topLevelType(matcher.group(1)));
        }
        return references;
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
     * Checks if a Javadoc tag matches the expected type based on its extraction format.
     * This method checks if an inline tag is extracted as a block tag or vice versa.
     * It ensures that block tags are correctly recognized as block tags and inline tags
     * as inline tags during processing.
     *
     * @param tag The Javadoc tag to check.
     * @param javadocTagType The expected type of the tag (BLOCK or INLINE).
     * @return {@code true} if the tag matches the expected type, otherwise {@code false}.
     */
    private static boolean isMatchingTagType(JavadocTag tag,
                                             JavadocUtil.JavadocTagType javadocTagType) {
        final boolean isInlineTag = tag.isInlineTag();
        final boolean isBlockTagType = javadocTagType == JavadocUtil.JavadocTagType.BLOCK;

        return isBlockTagType != isInlineTag;
    }

    /**
     * Attempts to match a reference string against a predefined pattern
     * and extracts valid reference.
     *
     * @param tag the input tag to check
     * @return Optional of extracted references
     */
    public static Optional<JavadocTag> bestTryToMatchReference(JavadocTag tag) {
        final String content = tag.getFirstArg();
        final int referenceIndex = extractReferencePart(content);
        Optional<JavadocTag> validTag = Optional.empty();

        if (referenceIndex != -1) {
            final String referenceString;
            if (referenceIndex == 0) {
                referenceString = content;
            }
            else {
                referenceString = content.substring(0, referenceIndex);
            }
            final Matcher matcher = REFERENCE.matcher(referenceString);
            if (matcher.matches()) {
                final int methodIndex = 3;
                final String methodPart = matcher.group(methodIndex);
                final boolean isValid = methodPart == null
                        || METHOD.matcher(methodPart).matches();
                if (isValid) {
                    validTag = Optional.of(tag);
                }
            }
        }
        return validTag;
    }

    /**
     * Extracts the reference part from an input string while ensuring balanced parentheses.
     *
     * @param input the input string
     * @return -1 if parentheses are unbalanced, 0 if no method is found,
     *         or the index of the first space outside parentheses.
     */
    private static int extractReferencePart(String input) {
        int parenthesesCount = 0;
        int firstSpaceOutsideParens = -1;
        for (int index = 0; index < input.length(); index++) {
            final char currentCharacter = input.charAt(index);

            if (currentCharacter == '(') {
                parenthesesCount++;
            }
            else if (currentCharacter == ')') {
                parenthesesCount--;
            }
            else if (currentCharacter == ' ' && parenthesesCount == 0) {
                firstSpaceOutsideParens = index;
                break;
            }
        }

        int methodIndex = -1;
        if (parenthesesCount == 0) {
            if (firstSpaceOutsideParens == -1) {
                methodIndex = 0;
            }
            else {
                methodIndex = firstSpaceOutsideParens;
            }
        }
        return methodIndex;
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
