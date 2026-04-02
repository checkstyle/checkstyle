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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.JavadocDetailNodeParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks for missing Javadoc comments for a method or constructor. The scope to verify is
 * specified using the {@code Scope} class and defaults to {@code Scope.PUBLIC}. To verify
 * another scope, set property scope to a different
 * <a href="https://checkstyle.org/property_types.html#Scope">scope</a>.
 * </div>
 *
 * <p>
 * Javadoc is not required on a method that is tagged with the {@code @Override} annotation.
 * However, under Java 5 it is not possible to mark a method required for an interface (this
 * was <i>corrected</i> under Java 6). Hence, Checkstyle supports using the convention of using
 * a single {@code {@inheritDoc}} tag instead of all the other tags.
 * </p>
 *
 * <p>
 * For getters and setters for the property {@code allowMissingPropertyJavadoc}, the methods must
 * match exactly the structures below.
 * </p>
 * <div class="wrapper"><pre class="prettyprint"><code class="language-java">
 * public void setNumber(final int number)
 * {
 *     mNumber = number;
 * }
 *
 * public int getNumber()
 * {
 *     return mNumber;
 * }
 *
 * public boolean isSomething()
 * {
 *     return false;
 * }
 * </code></pre></div>
 *
 * @since 8.21
 */
@FileStatefulCheck
public class MissingJavadocMethodCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_JAVADOC_MISSING = "javadoc.missing";

    /** Maximum children allowed in setter/getter. */
    private static final int SETTER_GETTER_MAX_CHILDREN = 7;

    /** Pattern matching names of getter methods. */
    private static final Pattern GETTER_PATTERN = Pattern.compile("^(is|get)[A-Z].*");

    /** Pattern matching names of setter methods. */
    private static final Pattern SETTER_PATTERN = Pattern.compile("^set[A-Z].*");

    /** Pattern matching a comment-only single-line comment. */
    private static final Pattern SINGLE_LINE_COMMENT_PATTERN = Pattern.compile("^\\s*//.*$");

    /** Maximum nodes allowed in a body of setter. */
    private static final int SETTER_BODY_SIZE = 3;

    /** Default value of minimal amount of lines in method to allow no documentation.*/
    private static final int DEFAULT_MIN_LINE_COUNT = -1;

    /** Specify the visibility scope where Javadoc comments are checked. */
    private Scope scope = Scope.PUBLIC;

    /** Specify the visibility scope where Javadoc comments are not checked. */
    private Scope excludeScope;

    /** Control the minimal amount of lines in method to allow no documentation.*/
    private int minLineCount = DEFAULT_MIN_LINE_COUNT;

    /**
     * Control whether to allow missing Javadoc on accessor methods for
     * properties (setters and getters).
     */
    private boolean allowMissingPropertyJavadoc;

    /** Ignore method whose names are matching specified regex. */
    private Pattern ignoreMethodNamesRegex;

    /** Configure annotations that allow missed documentation. */
    private Set<String> allowedAnnotations = Set.of("Override");

    /** Parser used to recognize Javadoc comments through the Javadoc AST pipeline. */
    private final JavadocDetailNodeParser javadocParser = new JavadocDetailNodeParser();

    /** Line numbers keyed the same way as FileContents#getJavadocBefore(int). */
    private final Set<Integer> javadocCommentEndLines = new HashSet<>();

    /** Block comments used to skip comment-only lines when searching for nearby Javadocs. */
    private final List<BlockCommentPosition> blockComments = new ArrayList<>();

    /**
     * Setter to configure annotations that allow missed documentation.
     *
     * @param userAnnotations user's value.
     * @since 8.21
     */
    public void setAllowedAnnotations(String... userAnnotations) {
        allowedAnnotations = Set.of(userAnnotations);
    }

    /**
     * Setter to ignore method whose names are matching specified regex.
     *
     * @param pattern a pattern.
     * @since 8.21
     */
    public void setIgnoreMethodNamesRegex(Pattern pattern) {
        ignoreMethodNamesRegex = pattern;
    }

    /**
     * Setter to control the minimal amount of lines in method to allow no documentation.
     *
     * @param value user's value.
     * @since 8.21
     */
    public void setMinLineCount(int value) {
        minLineCount = value;
    }

    /**
     * Setter to control whether to allow missing Javadoc on accessor methods for properties
     * (setters and getters).
     *
     * @param flag a {@code Boolean} value
     * @since 8.21
     */
    public void setAllowMissingPropertyJavadoc(final boolean flag) {
        allowMissingPropertyJavadoc = flag;
    }

    /**
     * Setter to specify the visibility scope where Javadoc comments are checked.
     *
     * @param scope a scope.
     * @since 8.21
     */
    public void setScope(Scope scope) {
        this.scope = scope;
    }

    /**
     * Setter to specify the visibility scope where Javadoc comments are not checked.
     *
     * @param excludeScope a scope.
     * @since 8.21
     */
    public void setExcludeScope(Scope excludeScope) {
        this.excludeScope = excludeScope;
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
        };
    }

    @Override
    public int[] getDefaultJavadocTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        super.beginTree(rootAST);
        javadocCommentEndLines.clear();
        blockComments.clear();
        collectComments(rootAST);
    }

    @Override
    public void visitToken(DetailAST ast) {
        final Scope theScope = ScopeUtil.getScope(ast);
        if (shouldCheck(ast, theScope)
                && !hasJavadocBefore(ast.getLineNo())
                && !isMissingJavadocAllowed(ast)) {
            log(ast, MSG_JAVADOC_MISSING);
        }
    }

    /**
     * Collects all block comments in the file.
     *
     * @param ast the current node.
     */
    private void collectComments(DetailAST ast) {
        DetailAST node = ast;
        while (node != null) {
            if (node.getType() == TokenTypes.BLOCK_COMMENT_BEGIN) {
                collectBlockComment(node);
            }
            collectComments(node.getFirstChild());
            node = node.getNextSibling();
        }
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        // No token-level Javadoc traversal is required for presence-only checks.
    }

    /**
     * Collects information about a block comment.
     *
     * @param blockComment the block comment to collect.
     */
    private void collectBlockComment(DetailAST blockComment) {
        final int startLineNo = blockComment.getLineNo();
        final int endLineNo = blockComment.getLastChild().getLineNo();
        final boolean javadocComment = isJavadocComment(blockComment);

        blockComments.add(new BlockCommentPosition(startLineNo, endLineNo, javadocComment));
        if (javadocComment) {
            javadocCommentEndLines.add(endLineNo - 1);
        }
    }

    /**
     * Checks whether a block comment is recognized as a Javadoc comment by the Javadoc parser.
     *
     * @param blockComment the block comment to inspect
     * @return {@code true} if the block comment is recognized as a Javadoc comment
     */
    private boolean isJavadocComment(DetailAST blockComment) {
        boolean result = false;
        if (JavadocUtil.isJavadocComment(JavadocUtil.getBlockCommentContent(blockComment))) {
            final JavadocDetailNodeParser.ParseStatus parseStatus =
                    javadocParser.parseJavadocComment(blockComment);
            result = parseStatus.getTree() != null
                    || parseStatus.getParseErrorMessage() != null;
        }
        return result;
    }

    /**
     * Checks whether there is a Javadoc comment before the specified line.
     *
     * @param lineNoBefore the line number to check before.
     * @return {@code true} if there is a Javadoc comment before the line.
     */
    private boolean hasJavadocBefore(int lineNoBefore) {
        int lineNo = lineNoBefore - 2;

        while (lineNo > 0 && (lineIsBlank(lineNo) || lineIsComment(lineNo)
                || lineInsideBlockComment(lineNo + 1))) {
            lineNo--;
        }
        return javadocCommentEndLines.contains(lineNo);
    }

    /**
     * Checks if the specified line is blank.
     *
     * @param lineNo the zero-based line number.
     * @return {@code true} if the specified line is blank.
     */
    private boolean lineIsBlank(int lineNo) {
        return CommonUtil.isBlank(getLines()[lineNo]);
    }

    /**
     * Checks if the specified line is a single-line comment without code.
     *
     * @param lineNo the zero-based line number.
     * @return {@code true} if the specified line is a single-line comment without code.
     */
    private boolean lineIsComment(int lineNo) {
        return SINGLE_LINE_COMMENT_PATTERN.matcher(getLines()[lineNo]).matches();
    }

    /**
     * Checks if the specified line number is inside a non-Javadoc block comment.
     *
     * @param lineNo the one-based line number to check.
     * @return {@code true} if the line is inside a non-Javadoc block comment.
     */
    private boolean lineInsideBlockComment(int lineNo) {
        return blockComments.stream()
            .filter(comment -> !comment.javadocComment)
            .anyMatch(comment -> isLineBlockComment(lineNo, comment));
    }

    /**
     * Checks if the given line is inside a block comment and the comment occupies
     * both the start and end lines.
     *
     * @param lineNo the one-based line number to check.
     * @param comment the block comment to inspect.
     * @return {@code true} if the line is inside the block comment.
     */
    private boolean isLineBlockComment(int lineNo, BlockCommentPosition comment) {
        final boolean lineInsideBlockComment = lineNo >= comment.startLineNo
                && lineNo <= comment.endLineNo;
        boolean lineHasOnlyBlockComment = true;
        final String startLine = getLines()[comment.startLineNo - 1].trim();
        if (!startLine.startsWith("/*")) {
            lineHasOnlyBlockComment = false;
        }

        final String endLine = getLines()[comment.endLineNo - 1].trim();
        if (!endLine.endsWith("*/")) {
            lineHasOnlyBlockComment = false;
        }
        return lineInsideBlockComment && lineHasOnlyBlockComment;
    }

    /**
     * Some javadoc.
     *
     * @param methodDef Some javadoc.
     * @return Some javadoc.
     */
    private static int getMethodsNumberOfLine(DetailAST methodDef) {
        final int numberOfLines;
        final DetailAST lcurly = methodDef.getLastChild();
        final DetailAST rcurly = lcurly.getLastChild();
        final DetailAST firstChild = getFirstChildSkipComments(lcurly);

        if (firstChild == rcurly) {
            numberOfLines = 1;
        }
        else {
            numberOfLines = rcurly.getLineNo() - lcurly.getLineNo() - 1;
        }
        return numberOfLines;
    }

    /**
     * Checks if a missing Javadoc is allowed by the check's configuration.
     *
     * @param ast the tree node for the method or constructor.
     * @return True if this method or constructor doesn't need Javadoc.
     */
    private boolean isMissingJavadocAllowed(final DetailAST ast) {
        return allowMissingPropertyJavadoc
                && (isSetterMethod(ast) || isGetterMethod(ast))
            || matchesSkipRegex(ast)
            || isContentsAllowMissingJavadoc(ast);
    }

    /**
     * Checks if the Javadoc can be missing if the method or constructor is
     * below the minimum line count or has a special annotation.
     *
     * @param ast the tree node for the method or constructor.
     * @return True if this method or constructor doesn't need Javadoc.
     */
    private boolean isContentsAllowMissingJavadoc(DetailAST ast) {
        return ast.getType() != TokenTypes.ANNOTATION_FIELD_DEF
                && (getMethodsNumberOfLine(ast) <= minLineCount
                    || AnnotationUtil.containsAnnotation(ast, allowedAnnotations));
    }

    /**
     * Checks if the given method name matches the regex. In that case
     * we skip enforcement of javadoc for this method
     *
     * @param methodDef {@link TokenTypes#METHOD_DEF METHOD_DEF}
     * @return true if given method name matches the regex.
     */
    private boolean matchesSkipRegex(DetailAST methodDef) {
        boolean result = false;
        if (ignoreMethodNamesRegex != null) {
            final DetailAST ident = methodDef.findFirstToken(TokenTypes.IDENT);
            final String methodName = ident.getText();

            final Matcher matcher = ignoreMethodNamesRegex.matcher(methodName);
            if (matcher.matches()) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Whether we should check this node.
     *
     * @param ast a given node.
     * @param nodeScope the scope of the node.
     * @return whether we should check a given node.
     */
    private boolean shouldCheck(final DetailAST ast, final Scope nodeScope) {
        return ScopeUtil.getSurroundingScope(ast)
            .map(surroundingScope -> {
                return nodeScope != excludeScope
                    && surroundingScope != excludeScope
                    && nodeScope.isIn(scope)
                    && surroundingScope.isIn(scope);
            })
            .orElse(Boolean.FALSE);
    }

    /**
     * Gets the first non-comment child of the given node.
     *
     * @param ast the node to inspect.
     * @return the first non-comment child, or {@code null} if none exist.
     */
    private static DetailAST getFirstChildSkipComments(DetailAST ast) {
        DetailAST child = ast.getFirstChild();
        while (child != null && TokenUtil.isCommentType(child.getType())) {
            child = child.getNextSibling();
        }
        return child;
    }

    /**
     * Gets the number of non-comment children of the given node.
     *
     * @param ast the node to inspect.
     * @return the number of non-comment children.
     */
    private static int getChildCountSkipComments(DetailAST ast) {
        int count = 0;
        DetailAST child = ast.getFirstChild();
        while (child != null) {
            if (!TokenUtil.isCommentType(child.getType())) {
                count++;
            }
            child = child.getNextSibling();
        }
        return count;
    }

    /**
     * Returns whether an AST represents a getter method.
     *
     * @param ast the AST to check with
     * @return whether the AST represents a getter method
     */
    public static boolean isGetterMethod(final DetailAST ast) {
        boolean getterMethod = false;

        // Check have a method with exactly 7 children which are all that
        // is allowed in a proper getter method which does not throw any
        // exceptions.
        if (ast.getType() == TokenTypes.METHOD_DEF
                && ast.getChildCount() == SETTER_GETTER_MAX_CHILDREN) {
            final DetailAST type = ast.findFirstToken(TokenTypes.TYPE);
            final String name = type.getNextSibling().getText();
            final boolean matchesGetterFormat = GETTER_PATTERN.matcher(name).matches();

            final DetailAST params = ast.findFirstToken(TokenTypes.PARAMETERS);
            final boolean noParams = params.getChildCount(TokenTypes.PARAMETER_DEF) == 0;

            if (matchesGetterFormat && noParams) {
                // Now verify that the body consists of:
                // SLIST -> RETURN
                // RCURLY
                final DetailAST slist = ast.findFirstToken(TokenTypes.SLIST);

                if (slist != null) {
                    final DetailAST expr = getFirstChildSkipComments(slist);
                    getterMethod = expr.getType() == TokenTypes.LITERAL_RETURN;
                }
            }
        }
        return getterMethod;
    }

    /**
     * Returns whether an AST represents a setter method.
     *
     * @param ast the AST to check with
     * @return whether the AST represents a setter method
     */
    public static boolean isSetterMethod(final DetailAST ast) {
        boolean setterMethod = false;

        // Check have a method with exactly 7 children which are all that
        // is allowed in a proper setter method which does not throw any
        // exceptions.
        if (ast.getType() == TokenTypes.METHOD_DEF
                && ast.getChildCount() == SETTER_GETTER_MAX_CHILDREN) {
            final DetailAST type = ast.findFirstToken(TokenTypes.TYPE);
            final String name = type.getNextSibling().getText();
            final boolean matchesSetterFormat = SETTER_PATTERN.matcher(name).matches();

            final DetailAST params = ast.findFirstToken(TokenTypes.PARAMETERS);
            final boolean singleParam = params.getChildCount(TokenTypes.PARAMETER_DEF) == 1;

            if (matchesSetterFormat && singleParam) {
                // Now verify that the body consists of:
                // SLIST -> EXPR -> ASSIGN
                // SEMI
                // RCURLY
                final DetailAST slist = ast.findFirstToken(TokenTypes.SLIST);

                if (slist != null && getChildCountSkipComments(slist) == SETTER_BODY_SIZE) {
                    final DetailAST expr = getFirstChildSkipComments(slist);
                    setterMethod = expr.getFirstChild().getType() == TokenTypes.ASSIGN;
                }
            }
        }
        return setterMethod;
    }

    /**
     * Line range information for a block comment.
     *
     * @param startLineNo the one-based start line number
     * @param endLineNo the one-based end line number
     * @param javadocComment whether the block comment is a Javadoc comment
     */
    private record BlockCommentPosition(int startLineNo, int endLineNo,
                                        boolean javadocComment) {
    }
}
