////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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

/**
 * <p>
 * Checks the Javadoc comments for annotation/enum/class/interface definitions. By default, does
 * not check for author or version tags. The scope to verify is specified using the {@code Scope}
 * class and defaults to {@code Scope.PRIVATE}. To verify another scope, set property
 * scope to one of the {@code Scope} constants. To define the format for an author
 * tag or a version tag, set property authorFormat or versionFormat respectively to a
 * <a href="https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html">
 * regular expression</a>.
 * </p>
 * <p>
 * Does not perform checks for author and version tags for inner classes,
 * as they should be redundant because of outer class.
 * </p>
 * <p>
 * Error messages about type parameters for which no param tags are present
 * can be suppressed by defining property {@code allowMissingParamTags}.
 * </p>
 * <ul>
 * <li>
 * Property {@code scope} - Specify the visibility scope where Javadoc comments are checked.
 * Default value is {@code private}.
 * </li>
 * <li>
 * Property {@code excludeScope} - Specify the visibility scope where Javadoc
 * comments are not checked.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code authorFormat} - Specify the pattern for {@code @author} tag.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code versionFormat} - Specify the pattern for {@code @version} tag.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code allowMissingParamTags} - Control whether to ignore violations
 * when a class has type parameters but does not have matching param tags in the Javadoc.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code allowUnknownTags} - Control whether to ignore violations when
 * a Javadoc tag is not recognised.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code allowedAnnotations} - Specify the list of annotations that allow
 * missed documentation. Only short names are allowed, e.g. {@code Generated}.
 * Default value is {@code Generated}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#INTERFACE_DEF">
 * INTERFACE_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CLASS_DEF">
 * CLASS_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ENUM_DEF">
 * ENUM_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ANNOTATION_DEF">
 * ANNOTATION_DEF</a>.
 * </li>
 * </ul>
 * <p>
 * To configure the default check:
 * </p>
 * <pre>
 * &lt;module name="JavadocType"/&gt;
 * </pre>
 * <p>
 * To configure the check for {@code public} scope:
 * </p>
 * <pre>
 * &lt;module name="JavadocType"&gt;
 *   &lt;property name="scope" value="public"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * To configure the check for an {@code @author} tag:
 * </p>
 * <pre>
 * &lt;module name="JavadocType"&gt;
 *   &lt;property name="authorFormat" value="\S"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * To configure the check for a CVS revision version tag:
 * </p>
 * <pre>
 * &lt;module name="JavadocType"&gt;
 *   &lt;property name="versionFormat" value="\$Revision.*\$"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * To configure the check for {@code private} classes only:
 * </p>
 * <pre>
 * &lt;module name="JavadocType"&gt;
 *   &lt;property name="scope" value="private"/&gt;
 *   &lt;property name="excludeScope" value="package"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example that allows missing comments for classes annotated with
 * {@code @SpringBootApplication} and {@code @Configuration}:
 * </p>
 * <pre>
 * &#64;SpringBootApplication // no violations about missing comment on class
 * public class Application {}
 *
 * &#64;Configuration // no violations about missing comment on class
 * class DatabaseConfiguration {}
 * </pre>
 * <p>
 * Use following configuration:
 * </p>
 * <pre>
 * &lt;module name="JavadocType"&gt;
 *   &lt;property name="allowedAnnotations" value="SpringBootApplication,Configuration"/&gt;
 * &lt;/module&gt;
 * </pre>
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

    /** Pattern to match type name within angle brackets in javadoc param tag. */
    private static final Pattern TYPE_NAME_IN_JAVADOC_TAG =
            Pattern.compile("\\s*<([^>]+)>.*");

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
     * Specify the list of annotations that allow missed documentation.
     * Only short names are allowed, e.g. {@code Generated}.
     */
    private List<String> allowedAnnotations = Collections.singletonList("Generated");

    /**
     * Setter to specify the visibility scope where Javadoc comments are checked.
     *
     * @param scope a scope.
     */
    public void setScope(Scope scope) {
        this.scope = scope;
    }

    /**
     * Setter to specify the visibility scope where Javadoc comments are not checked.
     *
     * @param excludeScope a scope.
     */
    public void setExcludeScope(Scope excludeScope) {
        this.excludeScope = excludeScope;
    }

    /**
     * Setter to specify the pattern for {@code @author} tag.
     *
     * @param pattern a pattern.
     */
    public void setAuthorFormat(Pattern pattern) {
        authorFormat = pattern;
    }

    /**
     * Setter to specify the pattern for {@code @version} tag.
     *
     * @param pattern a pattern.
     */
    public void setVersionFormat(Pattern pattern) {
        versionFormat = pattern;
    }

    /**
     * Setter to control whether to ignore violations when a class has type parameters but
     * does not have matching param tags in the Javadoc.
     *
     * @param flag a {@code Boolean} value
     */
    public void setAllowMissingParamTags(boolean flag) {
        allowMissingParamTags = flag;
    }

    /**
     * Setter to control whether to ignore violations when a Javadoc tag is not recognised.
     *
     * @param flag a {@code Boolean} value
     */
    public void setAllowUnknownTags(boolean flag) {
        allowUnknownTags = flag;
    }

    /**
     * Setter to specify the list of annotations that allow missed documentation.
     * Only short names are allowed, e.g. {@code Generated}.
     *
     * @param userAnnotations user's value.
     */
    public void setAllowedAnnotations(String... userAnnotations) {
        allowedAnnotations = Arrays.asList(userAnnotations);
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
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

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
                    checkTag(lineNo, tags, JavadocTagInfo.AUTHOR.getName(),
                            authorFormat);
                    checkTag(lineNo, tags, JavadocTagInfo.VERSION.getName(),
                            versionFormat);
                }

                final List<String> typeParamNames =
                    CheckUtil.getTypeParameterNames(ast);

                if (!allowMissingParamTags) {
                    // Check type parameters that should exist, do
                    for (final String typeParamName : typeParamNames) {
                        checkTypeParamTag(
                            lineNo, tags, typeParamName);
                    }
                }

                checkUnusedTypeParamTags(tags, typeParamNames);
            }
        }
    }

    /**
     * Whether we should check this node.
     * @param ast a given node.
     * @return whether we should check a given node.
     */
    private boolean shouldCheck(final DetailAST ast) {
        final Scope customScope;

        if (ScopeUtil.isInInterfaceOrAnnotationBlock(ast)) {
            customScope = Scope.PUBLIC;
        }
        else {
            final DetailAST mods = ast.findFirstToken(TokenTypes.MODIFIERS);
            customScope = ScopeUtil.getScopeFromMods(mods);
        }
        final Scope surroundingScope = ScopeUtil.getSurroundingScope(ast);

        return customScope.isIn(scope)
            && (surroundingScope == null || surroundingScope.isIn(scope))
            && (excludeScope == null
                || !customScope.isIn(excludeScope)
                || surroundingScope != null
                && !surroundingScope.isIn(excludeScope))
            && !AnnotationUtil.containsAnnotation(ast, allowedAnnotations);
    }

    /**
     * Gets all standalone tags from a given javadoc.
     * @param textBlock the Javadoc comment to process.
     * @return all standalone tags from the given javadoc.
     */
    private List<JavadocTag> getJavadocTags(TextBlock textBlock) {
        final JavadocTags tags = JavadocUtil.getJavadocTags(textBlock,
            JavadocUtil.JavadocTagType.BLOCK);
        if (!allowUnknownTags) {
            for (final InvalidJavadocTag tag : tags.getInvalidTags()) {
                log(tag.getLine(), tag.getCol(), MSG_UNKNOWN_TAG,
                    tag.getName());
            }
        }
        return tags.getValidTags();
    }

    /**
     * Verifies that a type definition has a required tag.
     * @param lineNo the line number for the type definition.
     * @param tags tags from the Javadoc comment for the type definition.
     * @param tagName the required tag name.
     * @param formatPattern regexp for the tag value.
     */
    private void checkTag(int lineNo, List<JavadocTag> tags, String tagName,
                          Pattern formatPattern) {
        if (formatPattern != null) {
            boolean hasTag = false;
            final String tagPrefix = "@";
            for (int i = tags.size() - 1; i >= 0; i--) {
                final JavadocTag tag = tags.get(i);
                if (tag.getTagName().equals(tagName)) {
                    hasTag = true;
                    if (!formatPattern.matcher(tag.getFirstArg()).find()) {
                        log(lineNo, MSG_TAG_FORMAT, tagPrefix + tagName, formatPattern.pattern());
                    }
                }
            }
            if (!hasTag) {
                log(lineNo, MSG_MISSING_TAG, tagPrefix + tagName);
            }
        }
    }

    /**
     * Verifies that a type definition has the specified param tag for
     * the specified type parameter name.
     * @param lineNo the line number for the type definition.
     * @param tags tags from the Javadoc comment for the type definition.
     * @param typeParamName the name of the type parameter
     */
    private void checkTypeParamTag(final int lineNo,
            final List<JavadocTag> tags, final String typeParamName) {
        boolean found = false;
        for (int i = tags.size() - 1; i >= 0; i--) {
            final JavadocTag tag = tags.get(i);
            if (tag.isParamTag()
                && tag.getFirstArg().indexOf(OPEN_ANGLE_BRACKET
                        + typeParamName + CLOSE_ANGLE_BRACKET) == 0) {
                found = true;
                break;
            }
        }
        if (!found) {
            log(lineNo, MSG_MISSING_TAG, JavadocTagInfo.PARAM.getText()
                + " " + OPEN_ANGLE_BRACKET + typeParamName + CLOSE_ANGLE_BRACKET);
        }
    }

    /**
     * Checks for unused param tags for type parameters.
     * @param tags tags from the Javadoc comment for the type definition.
     * @param typeParamNames names of type parameters
     */
    private void checkUnusedTypeParamTags(
        final List<JavadocTag> tags,
        final List<String> typeParamNames) {
        for (int i = tags.size() - 1; i >= 0; i--) {
            final JavadocTag tag = tags.get(i);
            if (tag.isParamTag()) {
                final String typeParamName = extractTypeParamNameFromTag(tag);

                if (!typeParamNames.contains(typeParamName)) {
                    log(tag.getLineNo(), tag.getColumnNo(),
                            MSG_UNUSED_TAG,
                            JavadocTagInfo.PARAM.getText(),
                            OPEN_ANGLE_BRACKET + typeParamName + CLOSE_ANGLE_BRACKET);
                }
            }
        }
    }

    /**
     * Extracts type parameter name from tag.
     * @param tag javadoc tag to extract parameter name
     * @return extracts type parameter name from tag
     */
    private static String extractTypeParamNameFromTag(JavadocTag tag) {
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

}
