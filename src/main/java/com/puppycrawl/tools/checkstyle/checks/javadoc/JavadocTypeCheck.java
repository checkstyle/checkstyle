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
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks the Javadoc comments for type definitions. By default, does
 * not check for author or version tags. The scope to verify is specified using the {@code Scope}
 * class and defaults to {@code Scope.PRIVATE}. To verify another scope, set property
 * scope to one of the {@code Scope} constants. To define the format for an author
 * tag or a version tag, set property authorFormat or versionFormat respectively to a
 * <a href="https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html">
 * pattern</a>.
 * </div>
 *
 * <p>
 * Does not perform checks for author and version tags for inner classes,
 * as they should be redundant because of outer class.
 * </p>
 *
 * <p>
 * Does not perform checks for type definitions that do not have any Javadoc comments.
 * </p>
 *
 * <p>
 * Error messages about type parameters and record components for which no param tags are present
 * can be suppressed by defining property {@code allowMissingParamTags}.
 * </p>
 *
 * @since 3.0
 */
@FileStatefulCheck
public class JavadocTypeCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_UNKNOWN_TAG = "javadoc.unknownTag";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_TAG_FORMAT = "type.tagFormat";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_MISSING_TAG = "type.missingTag";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_MISSING_TAG_WITH_QUOTES =
            "type.missingTagWithQuotes";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_UNUSED_TAG = "javadoc.unusedTag";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_UNUSED_TAG_GENERAL = "javadoc.unusedTagGeneral";

    /** Open angle bracket literal. */
    private static final String OPEN_ANGLE_BRACKET = "<";

    /** Close angle bracket literal. */
    private static final String CLOSE_ANGLE_BRACKET = ">";

    /** Specify the visibility scope where Javadoc comments are checked. */
    private Scope scope = Scope.PRIVATE;
    /** Specify the visibility scope where Javadoc comments are not checked. */
    private Scope excludeScope;
    /** Specify the pattern for {@code @author} tag. */
    private Pattern authorFormat;
    /** Specify the pattern for {@code @version} tag. */
    private Pattern versionFormat;
    /**
     * Control whether to ignore violations when a class has type parameters but
     * does not have matching param tags in the Javadoc.
     */
    private boolean allowMissingParamTags;
    /** Control whether to ignore violations when a Javadoc tag is not recognised. */
    private boolean allowUnknownTags;

    /**
     * Specify annotations that allow skipping validation at all.
     * Only short names are allowed, e.g. {@code Generated}.
     */
    private Set<String> allowedAnnotations = Set.of("Generated");

    /** Java AST node whose attached Javadoc is currently being processed. */
    private DetailAST currentAst;

    /** Javadoc tags collected from the current Javadoc tree. */
    private final List<JavadocTag> javadocTags = new ArrayList<>();

    /** Whether an {@code @author} tag was found in the current Javadoc tree. */
    private boolean authorTagIsPresent = false;

    /** Whether a {@code @version} tag was found in the current Javadoc tree. */
    private boolean versionTagIsPresent = false;

    /**
     * Creates a new {@code JavadocTypeCheck} instance.
     */
    public JavadocTypeCheck() {
        // no code by default
    }

    /**
     * Setter to specify the visibility scope where Javadoc comments are checked.
     *
     * @param scope a scope.
     * @since 3.0
     */
    public void setScope(Scope scope) {
        this.scope = scope;
    }

    /**
     * Setter to specify the visibility scope where Javadoc comments are not checked.
     *
     * @param excludeScope a scope.
     * @since 3.4
     */
    public void setExcludeScope(Scope excludeScope) {
        this.excludeScope = excludeScope;
    }

    /**
     * Setter to specify the pattern for {@code @author} tag.
     *
     * @param pattern a pattern.
     * @since 3.0
     */
    public void setAuthorFormat(Pattern pattern) {
        authorFormat = pattern;
    }

    /**
     * Setter to specify the pattern for {@code @version} tag.
     *
     * @param pattern a pattern.
     * @since 3.0
     */
    public void setVersionFormat(Pattern pattern) {
        versionFormat = pattern;
    }

    /**
     * Setter to control whether to ignore violations when a class has type parameters but
     * does not have matching param tags in the Javadoc.
     *
     * @param flag a {@code Boolean} value
     * @since 4.0
     */
    public void setAllowMissingParamTags(boolean flag) {
        allowMissingParamTags = flag;
    }

    /**
     * Setter to control whether to ignore violations when a Javadoc tag is not recognised.
     *
     * @param flag a {@code Boolean} value
     * @since 5.1
     */
    public void setAllowUnknownTags(boolean flag) {
        allowUnknownTags = flag;
    }

    /**
     * Setter to specify annotations that allow skipping validation at all.
     * Only short names are allowed, e.g. {@code Generated}.
     *
     * @param userAnnotations user's value.
     * @since 8.15
     */
    public void setAllowedAnnotations(String... userAnnotations) {
        allowedAnnotations = Set.of(userAnnotations);
    }

    /**
     * Setter to control when to print violations if the Javadoc being examined by this check
     * violates the tight html rules defined at
     * <a href="https://checkstyle.org/writingjavadocchecks.html#Tight-HTML_rules">
     *     Tight-HTML Rules</a>.
     *
     * @param shouldReportViolation value to which the field shall be set to
     * @since 8.3
     * @propertySince 13.9.0
     */
    @Override
    public void setViolateExecutionOnNonTightHtml(boolean shouldReportViolation) {
        super.setViolateExecutionOnNonTightHtml(shouldReportViolation);
    }

    @Override
    public void beginJavadocTree(DetailNode rootAst) {
        javadocTags.clear();
        authorTagIsPresent = false;
        versionTagIsPresent = false;
    }

    @Override
    public void finishJavadocTree(DetailNode rootAst) {
        if (authorFormat != null && ScopeUtil.isOuterMostType(currentAst)
                && !authorTagIsPresent) {
            log(currentAst, MSG_MISSING_TAG, "@author");
        }
        if (versionFormat != null && ScopeUtil.isOuterMostType(currentAst)
                && !versionTagIsPresent) {
            log(currentAst, MSG_MISSING_TAG, "@version");
        }
        checkCollectedParamTags();
    }

    @Override
    public int[] getDefaultJavadocTokens() {
        return getRequiredJavadocTokens();
    }

    @Override
    public int[] getRequiredJavadocTokens() {
        return new int[] {
            JavadocCommentsTokenTypes.PARAM_BLOCK_TAG,
            JavadocCommentsTokenTypes.AUTHOR_BLOCK_TAG,
            JavadocCommentsTokenTypes.VERSION_BLOCK_TAG,
            JavadocCommentsTokenTypes.CUSTOM_BLOCK_TAG
        };
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        switch (ast.getType()) {
            case JavadocCommentsTokenTypes.PARAM_BLOCK_TAG -> collectParam(ast);
            case JavadocCommentsTokenTypes.AUTHOR_BLOCK_TAG -> {
                authorTagIsPresent = true;
                checkTagFormat(ast, "@author", authorFormat);
            }
            case JavadocCommentsTokenTypes.VERSION_BLOCK_TAG -> {
                versionTagIsPresent = true;
                checkTagFormat(ast, "@version", versionFormat);
            }
            case JavadocCommentsTokenTypes.CUSTOM_BLOCK_TAG -> checkUnknownTag(ast);
            default -> throw new IllegalArgumentException("Unknown javadoc token type " + ast);
        }
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.INTERFACE_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.RECORD_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (shouldCheck(ast)) {
            final DetailAST blockCommentNode = JavadocUtil.getAttachedJavadocComment(ast);
            if (blockCommentNode != null) {
                currentAst = ast;
                super.visitToken(blockCommentNode);
            }
        }
    }

    /**
     * Whether we should check this node.
     *
     * @param ast a given node.
     * @return whether we should check a given node.
     */
    private boolean shouldCheck(DetailAST ast) {
        return ScopeUtil.getSurroundingScope(ast)
            .map(surroundingScope -> {
                return surroundingScope.isIn(scope)
                    && (excludeScope == null || !surroundingScope.isIn(excludeScope))
                    && !AnnotationUtil.containsAnnotation(ast, allowedAnnotations);
            })
            .orElse(Boolean.FALSE);
    }

    /**
     * Collects a param tag.
     *
     * @param ast the param tag node
     */
    private void collectParam(DetailNode ast) {
        final DetailNode parameterName = JavadocUtil.findFirstToken(
                ast, JavadocCommentsTokenTypes.PARAMETER_NAME);
        if (parameterName != null) {
            javadocTags.add(new JavadocTag(ast.getLineNumber(), ast.getColumnNumber(),
                    "param", parameterName.getText()));
        }
        else {
            log(ast.getLineNumber(), ast.getColumnNumber(), MSG_UNUSED_TAG_GENERAL);
        }
    }

    /**
     * Checks an unknown Javadoc tag.
     *
     * @param ast the unknown tag node
     */
    private void checkUnknownTag(DetailNode ast) {
        if (!allowUnknownTags) {
            final String tagName = JavadocUtil.findFirstToken(
                    ast, JavadocCommentsTokenTypes.TAG_NAME).getText();
            log(ast.getLineNumber(), ast.getColumnNumber(), MSG_UNKNOWN_TAG, tagName);
        }
    }

    /**
     * Checks a Javadoc tag description against the expected format.
     *
     * @param ast the Javadoc tag node
     * @param tagName the tag name
     * @param format expected format for the tag description
     */
    private void checkTagFormat(DetailNode ast, String tagName, Pattern format) {
        if (format != null && ScopeUtil.isOuterMostType(currentAst)) {
            String description = "";
            final DetailNode descriptionNode = JavadocUtil.findFirstToken(
                    ast, JavadocCommentsTokenTypes.DESCRIPTION);
            if (descriptionNode != null) {
                description = descriptionNode.getFirstChild().getText().trim();
            }
            if (!format.matcher(description).find()) {
                log(currentAst, MSG_TAG_FORMAT, tagName, format.pattern());
            }
        }
    }

    /**
     * Checks collected Javadoc param tags against the current AST node.
     */
    private void checkCollectedParamTags() {
        final List<DetailAST> params = getRecordComponents(currentAst);
        final List<DetailAST> typeParams = CheckUtil.getTypeParameters(currentAst);

        for (JavadocTag tag : javadocTags) {
            final String paramName = tag.getFirstArg();
            boolean found = removeMatchingParam(params, paramName);
            if (paramName.startsWith(OPEN_ANGLE_BRACKET)
                    && paramName.endsWith(CLOSE_ANGLE_BRACKET)) {
                final String typeParamName = paramName.substring(1, paramName.length() - 1);
                found = removeMatchingTypeParam(typeParams, typeParamName) || found;
            }

            if (!found) {
                log(tag.getLineNo(), tag.getColumnNo(),
                    MSG_UNUSED_TAG, JavadocTagInfo.PARAM.getText(), paramName);
            }
        }

        if (!allowMissingParamTags) {
            params.forEach(param -> {
                log(currentAst, MSG_MISSING_TAG_WITH_QUOTES,
                    JavadocTagInfo.PARAM.getText(), param.getText());
            });
            typeParams.forEach(typeParam -> {
                final DetailAST ident = typeParam.findFirstToken(TokenTypes.IDENT);
                log(currentAst, MSG_MISSING_TAG_WITH_QUOTES,
                    JavadocTagInfo.PARAM.getText(),
                    OPEN_ANGLE_BRACKET + ident.getText() + CLOSE_ANGLE_BRACKET);
            });
        }
    }

    /**
     * Removes the matching record component from the list.
     *
     * @param params record component identifiers to check
     * @param paramName Javadoc param tag argument
     * @return true if the matching record component was found and removed
     */
    private static boolean removeMatchingParam(List<DetailAST> params, String paramName) {
        boolean found = false;
        int index = 0;
        while (!found && index < params.size()) {
            if (params.get(index).getText().equals(paramName)) {
                params.remove(index);
                found = true;
            }
            index++;
        }
        return found;
    }

    /**
     * Removes the matching type parameter from the list.
     *
     * @param typeParams type parameter nodes to check
     * @param typeParamName type parameter name from the Javadoc param tag
     * @return true if the matching type parameter was found and removed
     */
    private static boolean removeMatchingTypeParam(List<DetailAST> typeParams,
                                                   String typeParamName) {
        boolean found = false;
        int index = 0;
        while (!found && index < typeParams.size()) {
            final DetailAST ident = typeParams.get(index).findFirstToken(TokenTypes.IDENT);
            if (ident.getText().equals(typeParamName)) {
                typeParams.remove(index);
                found = true;
            }
            index++;
        }
        return found;
    }

    /**
     * Collects the record components in a record definition.
     *
     * @param node the possible record definition AST
     * @return the record components in this record definition
     */
    private static List<DetailAST> getRecordComponents(DetailAST node) {
        final DetailAST components = node.findFirstToken(TokenTypes.RECORD_COMPONENTS);
        final List<DetailAST> componentList = new ArrayList<>();

        if (components != null) {
            TokenUtil.forEachChild(components,
                TokenTypes.RECORD_COMPONENT_DEF, component -> {
                    final DetailAST ident = component.findFirstToken(TokenTypes.IDENT);
                    componentList.add(ident);
                });
        }

        return componentList;
    }

}
