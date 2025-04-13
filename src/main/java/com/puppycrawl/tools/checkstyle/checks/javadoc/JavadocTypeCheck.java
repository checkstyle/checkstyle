////
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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
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
 * <ul>
 * <li>
 * Property {@code allowMissingParamTags} - Control whether to ignore violations
 * when a class has type parameters but does not have matching param tags in the Javadoc.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code allowUnknownTags} - Control whether to ignore violations when
 * a Javadoc tag is not recognised.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code allowedAnnotations} - Specify annotations that allow
 * skipping validation at all. Only short names are allowed, e.g. {@code Generated}.
 * Type is {@code java.lang.String[]}.
 * Default value is {@code Generated}.
 * </li>
 * <li>
 * Property {@code authorFormat} - Specify the pattern for {@code @author} tag.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code excludeScope} - Specify the visibility scope where Javadoc
 * comments are not checked.
 * Type is {@code com.puppycrawl.tools.checkstyle.api.Scope}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code scope} - Specify the visibility scope where Javadoc comments are checked.
 * Type is {@code com.puppycrawl.tools.checkstyle.api.Scope}.
 * Default value is {@code private}.
 * </li>
 * <li>
 * Property {@code versionFormat} - Specify the pattern for {@code @version} tag.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#INTERFACE_DEF">
 * INTERFACE_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CLASS_DEF">
 * CLASS_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ENUM_DEF">
 * ENUM_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ANNOTATION_DEF">
 * ANNOTATION_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#RECORD_DEF">
 * RECORD_DEF</a>.
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
 * {@code javadoc.unknownTag}
 * </li>
 * <li>
 * {@code javadoc.unusedTag}
 * </li>
 * <li>
 * {@code javadoc.unusedTagGeneral}
 * </li>
 * <li>
 * {@code type.missingTag}
 * </li>
 * <li>
 * {@code type.tagFormat}
 * </li>
 * </ul>
 *
 * @since 3.0
 *
 */
@StatelessCheck
public class JavadocTypeCheck
    extends AbstractCheck {

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

    /** Space literal. */
    private static final String SPACE = " ";

    /** Pattern to match type name within angle brackets in javadoc param tag. */
    private static final Pattern TYPE_NAME_IN_JAVADOC_TAG =
        Pattern.compile("^<([^>]+)");

    /** Pattern to split type name field in javadoc param tag. */
    private static final Pattern TYPE_NAME_IN_JAVADOC_TAG_SPLITTER =
        Pattern.compile("\\s+");

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

    // suppress deprecation until https://github.com/checkstyle/checkstyle/issues/11166
    @SuppressWarnings("deprecation")
    @Override
    public void visitToken(DetailAST ast) {
        if (shouldCheck(ast)) {
            final FileContents contents = getFileContents();
            final int lineNo = ast.getLineNo();
            final TextBlock textBlock = contents.getJavadocBefore(lineNo);
            if (textBlock != null) {
                final List<JavadocTag> tags = getJavadocTags(textBlock);
                if (ScopeUtil.isOuterMostType(ast)) {
                    // don't check author/version for inner classes
                    checkTag(ast, tags, JavadocTagInfo.AUTHOR.getName(),
                        authorFormat);
                    checkTag(ast, tags, JavadocTagInfo.VERSION.getName(),
                        versionFormat);
                }

                final List<String> typeParamNames =
                    CheckUtil.getTypeParameterNames(ast);
                final List<String> recordComponentNames =
                    getRecordComponentNames(ast);

                if (!allowMissingParamTags) {

                    typeParamNames.forEach(typeParamName -> {
                        checkTypeParamTag(ast, tags, typeParamName);
                    });

                    recordComponentNames.forEach(componentName -> {
                        checkComponentParamTag(ast, tags, componentName);
                    });
                }

                checkUnusedParamTags(tags, typeParamNames, recordComponentNames);
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
        final Scope surroundingScope = ScopeUtil.getSurroundingScope(ast);

        return surroundingScope.isIn(scope)
            && (excludeScope == null || !surroundingScope.isIn(excludeScope))
            && !AnnotationUtil.containsAnnotation(ast, allowedAnnotations);
    }

    /**
     * Gets all standalone tags from a given javadoc.
     *
     * @param textBlock the Javadoc comment to process.
     * @return all standalone tags from the given javadoc.
     */
    private List<JavadocTag> getJavadocTags(TextBlock textBlock) {
        final JavadocTags tags = JavadocUtil.getJavadocTags(textBlock,
            JavadocUtil.JavadocTagType.BLOCK);
        if (!allowUnknownTags) {
            tags.getInvalidTags().forEach(tag -> {
                log(tag.getLine(), tag.getCol(), MSG_UNKNOWN_TAG, tag.getName());
            });
        }
        return tags.getValidTags();
    }

    /**
     * Verifies that a type definition has a required tag.
     *
     * @param ast the AST node for the type definition.
     * @param tags tags from the Javadoc comment for the type definition.
     * @param tagName the required tag name.
     * @param formatPattern regexp for the tag value.
     */
    private void checkTag(DetailAST ast, Iterable<JavadocTag> tags, String tagName,
                          Pattern formatPattern) {
        if (formatPattern != null) {
            boolean hasTag = false;
            final String tagPrefix = "@";

            for (final JavadocTag tag : tags) {
                if (tag.getTagName().equals(tagName)) {
                    hasTag = true;
                    if (!formatPattern.matcher(tag.getFirstArg()).find()) {
                        log(ast, MSG_TAG_FORMAT, tagPrefix + tagName, formatPattern.pattern());
                    }
                }
            }
            if (!hasTag) {
                log(ast, MSG_MISSING_TAG, tagPrefix + tagName);
            }
        }
    }

    /**
     * Verifies that a record definition has the specified param tag for
     * the specified record component name.
     *
     * @param ast the AST node for the record definition.
     * @param tags tags from the Javadoc comment for the record definition.
     * @param recordComponentName the name of the type parameter
     */
    private void checkComponentParamTag(DetailAST ast,
                                        Collection<JavadocTag> tags,
                                        String recordComponentName) {

        final boolean found = tags
            .stream()
            .filter(JavadocTag::isParamTag)
            .anyMatch(tag -> tag.getFirstArg().indexOf(recordComponentName) == 0);

        if (!found) {
            log(ast, MSG_MISSING_TAG, JavadocTagInfo.PARAM.getText()
                + SPACE + recordComponentName);
        }
    }

    /**
     * Verifies that a type definition has the specified param tag for
     * the specified type parameter name.
     *
     * @param ast the AST node for the type definition.
     * @param tags tags from the Javadoc comment for the type definition.
     * @param typeParamName the name of the type parameter
     */
    private void checkTypeParamTag(DetailAST ast,
                                   Collection<JavadocTag> tags, String typeParamName) {
        final String typeParamNameWithBrackets =
            OPEN_ANGLE_BRACKET + typeParamName + CLOSE_ANGLE_BRACKET;

        final boolean found = tags
            .stream()
            .filter(JavadocTag::isParamTag)
            .anyMatch(tag -> tag.getFirstArg().indexOf(typeParamNameWithBrackets) == 0);

        if (!found) {
            log(ast, MSG_MISSING_TAG, JavadocTagInfo.PARAM.getText()
                + SPACE + typeParamNameWithBrackets);
        }
    }

    /**
     * Checks for unused param tags for type parameters and record components.
     *
     * @param tags tags from the Javadoc comment for the type definition
     * @param typeParamNames names of type parameters
     * @param recordComponentNames record component names in this definition
     */
    private void checkUnusedParamTags(
        List<JavadocTag> tags,
        List<String> typeParamNames,
        List<String> recordComponentNames) {

        for (final JavadocTag tag : tags) {
            if (tag.isParamTag()) {
                final String paramName = extractParamNameFromTag(tag);
                final boolean found = typeParamNames.contains(paramName)
                    || recordComponentNames.contains(paramName);

                if (!found) {
                    if (paramName.isEmpty()) {
                        log(tag.getLineNo(), tag.getColumnNo(), MSG_UNUSED_TAG_GENERAL);
                    }
                    else {
                        final String actualParamName =
                            TYPE_NAME_IN_JAVADOC_TAG_SPLITTER.split(tag.getFirstArg())[0];
                        log(tag.getLineNo(), tag.getColumnNo(),
                            MSG_UNUSED_TAG,
                            JavadocTagInfo.PARAM.getText(), actualParamName);
                    }
                }
            }
        }

    }

    /**
     * Extracts parameter name from tag.
     *
     * @param tag javadoc tag to extract parameter name
     * @return extracts type parameter name from tag
     */
    private static String extractParamNameFromTag(JavadocTag tag) {
        final String typeParamName;
        final Matcher matchInAngleBrackets =
            TYPE_NAME_IN_JAVADOC_TAG.matcher(tag.getFirstArg());
        if (matchInAngleBrackets.find()) {
            typeParamName = matchInAngleBrackets.group(1).trim();
        }
        else {
            typeParamName = TYPE_NAME_IN_JAVADOC_TAG_SPLITTER.split(tag.getFirstArg())[0];
        }
        return typeParamName;
    }

    /**
     * Collects the record components in a record definition.
     *
     * @param node the possible record definition ast.
     * @return the record components in this record definition.
     */
    private static List<String> getRecordComponentNames(DetailAST node) {
        final DetailAST components = node.findFirstToken(TokenTypes.RECORD_COMPONENTS);
        final List<String> componentList = new ArrayList<>();

        if (components != null) {
            TokenUtil.forEachChild(components,
                TokenTypes.RECORD_COMPONENT_DEF, component -> {
                    final DetailAST ident = component.findFirstToken(TokenTypes.IDENT);
                    componentList.add(ident.getText());
                });
        }

        return componentList;
    }
}
