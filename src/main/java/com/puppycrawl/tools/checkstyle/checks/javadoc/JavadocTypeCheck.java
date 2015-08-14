////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import com.puppycrawl.tools.checkstyle.ScopeUtils;
import com.puppycrawl.tools.checkstyle.Utils;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.JavadocTagInfo;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.CheckUtils;

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
    extends Check {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String JAVADOC_MISSING = "javadoc.missing";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String UNKNOWN_TAG = "javadoc.unknownTag";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String TAG_FORMAT = "type.tagFormat";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MISSING_TAG = "type.missingTag";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String UNUSED_TAG = "javadoc.unusedTag";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String UNUSED_TAG_GENERAL = "javadoc.unusedTagGeneral";

    /** the scope to check for */
    private Scope scope = Scope.PRIVATE;
    /** the visibility scope where Javadoc comments shouldn't be checked **/
    private Scope excludeScope;
    /** compiled regexp to match author tag content **/
    private Pattern authorFormatPattern;
    /** compiled regexp to match version tag content **/
    private Pattern versionFormatPattern;
    /** regexp to match author tag content */
    private String authorFormat;
    /** regexp to match version tag content */
    private String versionFormat;
    /**
     * controls whether to ignore errors when a method has type parameters but
     * does not have matching param tags in the javadoc. Defaults to false.
     */
    private boolean allowMissingParamTags;
    /** controls whether to flag errors for unknown tags. Defaults to false. */
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
        authorFormatPattern = Utils.createPattern(format);
    }

    /**
     * Set the version format pattern.
     * @param format a {@code String} value
     */
    public void setVersionFormat(String format) {
        versionFormat = format;
        versionFormatPattern = Utils.createPattern(format);
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
        return new int[] {
            TokenTypes.INTERFACE_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
        };
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
    public void visitToken(DetailAST ast) {
        if (shouldCheck(ast)) {
            final FileContents contents = getFileContents();
            final int lineNo = ast.getLineNo();
            final TextBlock cmt = contents.getJavadocBefore(lineNo);
            if (cmt == null) {
                log(lineNo, JAVADOC_MISSING);
            }
            else {
                final List<JavadocTag> tags = getJavadocTags(cmt);
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
        final Scope customScope =
            ScopeUtils.inInterfaceOrAnnotationBlock(ast)
                ? Scope.PUBLIC : declaredScope;
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
     * @param cmt the Javadoc comment to process.
     * @return all standalone tags from the given javadoc.
     */
    private List<JavadocTag> getJavadocTags(TextBlock cmt) {
        final JavadocTags tags = JavadocUtils.getJavadocTags(cmt,
            JavadocUtils.JavadocTagType.BLOCK);
        if (!allowUnknownTags) {
            for (final InvalidJavadocTag tag : tags.getInvalidTags()) {
                log(tag.getLine(), tag.getCol(), UNKNOWN_TAG,
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
        if (formatPattern == null) {
            return;
        }

        int tagCount = 0;
        for (int i = tags.size() - 1; i >= 0; i--) {
            final JavadocTag tag = tags.get(i);
            if (tag.getTagName().equals(tagName)) {
                tagCount++;
                if (!formatPattern.matcher(tag.getFirstArg()).find()) {
                    log(lineNo, TAG_FORMAT, "@" + tagName, format);
                }
            }
        }
        if (tagCount == 0) {
            log(lineNo, MISSING_TAG, "@" + tagName);
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
                && tag.getFirstArg().indexOf("<" + typeParamName + ">") == 0) {
                found = true;
            }
        }
        if (!found) {
            log(lineNo, MISSING_TAG,
                JavadocTagInfo.PARAM.getText() + " <" + typeParamName + ">");
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
        final Pattern pattern = Pattern.compile("\\s*<([^>]+)>.*");
        for (int i = tags.size() - 1; i >= 0; i--) {
            final JavadocTag tag = tags.get(i);
            if (tag.isParamTag()) {

                final Matcher matcher = pattern.matcher(tag.getFirstArg());
                matcher.find();
                final String typeParamName = matcher.group(1).trim();
                if (!typeParamNames.contains(typeParamName)) {
                    log(tag.getLineNo(), tag.getColumnNo(),
                        UNUSED_TAG,
                        JavadocTagInfo.PARAM.getText(),
                        "<" + typeParamName + ">");
                }
            }
        }
    }
}
