////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtils;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtils;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtils;

/**
 * Checks the Javadoc of a type.
 *
 * <p>Does not perform checks for author and version tags for inner classes, as
 * they should be redundant because of outer class.
 *
 * @author Oliver Burn
 * @author Michael Tamm
 */
public class JavadocTypeCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_JAVADOC_MISSING = "javadoc.missing";

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

    /** The scope to check for. */
    private Scope scope = Scope.PRIVATE;
    /** The visibility scope where Javadoc comments shouldn't be checked. **/
    private Scope excludeScope;
    /** Compiled regexp to match author tag content. **/
    private Pattern authorFormatPattern;
    /** Compiled regexp to match version tag content. **/
    private Pattern versionFormatPattern;
    /** Regexp to match author tag content. */
    private String authorFormat;
    /** Regexp to match version tag content. */
    private String versionFormat;
    /**
     * Controls whether to ignore errors when a method has type parameters but
     * does not have matching param tags in the javadoc. Defaults to false.
     */
    private boolean allowMissingParamTags;
    /** Controls whether to flag errors for unknown tags. Defaults to false. */
    private boolean allowUnknownTags;

    /**
     * Sets the scope to check.
     * @param from string to set scope from
     */
    public void setScope(String from) {
        scope = Scope.getInstance(from);
    }

    /**
     * Set the excludeScope.
     * @param excludeScope a {@code String} value
     */
    public void setExcludeScope(String excludeScope) {
        this.excludeScope = Scope.getInstance(excludeScope);
    }

    /**
     * Set the author tag pattern.
     * @param format a {@code String} value
     */
    public void setAuthorFormat(String format) {
        authorFormat = format;
        authorFormatPattern = CommonUtils.createPattern(format);
    }

    /**
     * Set the version format pattern.
     * @param format a {@code String} value
     */
    public void setVersionFormat(String format) {
        versionFormat = format;
        versionFormatPattern = CommonUtils.createPattern(format);
    }

    /**
     * Controls whether to allow a type which has type parameters to
     * omit matching param tags in the javadoc. Defaults to false.
     *
     * @param flag a {@code Boolean} value
     */
    public void setAllowMissingParamTags(boolean flag) {
        allowMissingParamTags = flag;
    }

    /**
     * Controls whether to flag errors for unknown tags. Defaults to false.
     * @param flag a {@code Boolean} value
     */
    public void setAllowUnknownTags(boolean flag) {
        allowUnknownTags = flag;
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
        return CommonUtils.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (shouldCheck(ast)) {
            final FileContents contents = getFileContents();
            final int lineNo = ast.getLineNo();
            final TextBlock textBlock = contents.getJavadocBefore(lineNo);
            if (textBlock == null) {
                log(lineNo, MSG_JAVADOC_MISSING);
            }
            else {
                final List<JavadocTag> tags = getJavadocTags(textBlock);
                if (ScopeUtils.isOuterMostType(ast)) {
                    // don't check author/version for inner classes
                    checkTag(lineNo, tags, JavadocTagInfo.AUTHOR.getName(),
                            authorFormatPattern, authorFormat);
                    checkTag(lineNo, tags, JavadocTagInfo.VERSION.getName(),
                            versionFormatPattern, versionFormat);
                }

                final List<String> typeParamNames =
                    CheckUtils.getTypeParameterNames(ast);

                if (!allowMissingParamTags) {
                    //Check type parameters that should exist, do
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
        final DetailAST mods = ast.findFirstToken(TokenTypes.MODIFIERS);
        final Scope declaredScope = ScopeUtils.getScopeFromMods(mods);
        final Scope customScope;

        if (ScopeUtils.isInInterfaceOrAnnotationBlock(ast)) {
            customScope = Scope.PUBLIC;
        }
        else {
            customScope = declaredScope;
        }
        final Scope surroundingScope = ScopeUtils.getSurroundingScope(ast);

        return customScope.isIn(scope)
            && (surroundingScope == null || surroundingScope.isIn(scope))
            && (excludeScope == null
                || !customScope.isIn(excludeScope)
                || surroundingScope != null
                && !surroundingScope.isIn(excludeScope));
    }

    /**
     * Gets all standalone tags from a given javadoc.
     * @param textBlock the Javadoc comment to process.
     * @return all standalone tags from the given javadoc.
     */
    private List<JavadocTag> getJavadocTags(TextBlock textBlock) {
        final JavadocTags tags = JavadocUtils.getJavadocTags(textBlock,
            JavadocUtils.JavadocTagType.BLOCK);
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
     * @param format pattern for the tag value.
     */
    private void checkTag(int lineNo, List<JavadocTag> tags, String tagName,
                          Pattern formatPattern, String format) {
        if (formatPattern != null) {
            int tagCount = 0;
            final String tagPrefix = "@";
            for (int i = tags.size() - 1; i >= 0; i--) {
                final JavadocTag tag = tags.get(i);
                if (tag.getTagName().equals(tagName)) {
                    tagCount++;
                    if (!formatPattern.matcher(tag.getFirstArg()).find()) {
                        log(lineNo, MSG_TAG_FORMAT, tagPrefix + tagName, format);
                    }
                }
            }
            if (tagCount == 0) {
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
