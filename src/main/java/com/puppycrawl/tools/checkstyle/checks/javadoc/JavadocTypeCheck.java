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

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.JavadocTagInfo;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.ScopeUtils;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Utils;
import com.puppycrawl.tools.checkstyle.checks.CheckUtils;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.apache.commons.beanutils.ConversionException;

/**
 * Checks the Javadoc of a type.
 *
 * @author Oliver Burn
 * @author Michael Tamm
 */
public class JavadocTypeCheck
    extends Check
{
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
    public void setScope(String from)
    {
        scope = Scope.getInstance(from);
    }

    /**
     * Set the excludeScope.
     * @param scope a <code>String</code> value
     */
    public void setExcludeScope(String scope)
    {
        excludeScope = Scope.getInstance(scope);
    }

    /**
     * Set the author tag pattern.
     * @param format a <code>String</code> value
     * @throws ConversionException unable to parse aFormat
     */
    public void setAuthorFormat(String format)
        throws ConversionException
    {
        try {
            authorFormat = format;
            authorFormatPattern = Utils.getPattern(format);
        }
        catch (final PatternSyntaxException e) {
            throw new ConversionException("unable to parse " + format, e);
        }
    }

    /**
     * Set the version format pattern.
     * @param format a <code>String</code> value
     * @throws ConversionException unable to parse aFormat
     */
    public void setVersionFormat(String format)
        throws ConversionException
    {
        try {
            versionFormat = format;
            versionFormatPattern = Utils.getPattern(format);
        }
        catch (final PatternSyntaxException e) {
            throw new ConversionException("unable to parse " + format, e);
        }

    }

    /**
     * Controls whether to allow a type which has type parameters to
     * omit matching param tags in the javadoc. Defaults to false.
     *
     * @param flag a <code>Boolean</code> value
     */
    public void setAllowMissingParamTags(boolean flag)
    {
        allowMissingParamTags = flag;
    }

    /**
     * Controls whether to flag errors for unknown tags. Defaults to false.
     * @param flag a <code>Boolean</code> value
     */
    public void setAllowUnknownTags(boolean flag)
    {
        allowUnknownTags = flag;
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.INTERFACE_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
        };
    }

    @Override
    public int[] getAcceptableTokens()
    {
        return new int[] {
            TokenTypes.INTERFACE_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
        };
    }

    @Override
    public void visitToken(DetailAST ast)
    {
        if (shouldCheck(ast)) {
            final FileContents contents = getFileContents();
            final int lineNo = ast.getLineNo();
            final TextBlock cmt = contents.getJavadocBefore(lineNo);
            if (cmt == null) {
                log(lineNo, "javadoc.missing");
            }
            else if (ScopeUtils.isOuterMostType(ast)) {
                // don't check author/version for inner classes
                final List<JavadocTag> tags = getJavadocTags(cmt);
                checkTag(lineNo, tags, JavadocTagInfo.AUTHOR.getName(),
                         authorFormatPattern, authorFormat);
                checkTag(lineNo, tags, JavadocTagInfo.VERSION.getName(),
                         versionFormatPattern, versionFormat);

                final List<String> typeParamNames =
                    CheckUtils.getTypeParameterNames(ast);

                if (!allowMissingParamTags) {
                    //Check type parameters that should exist, do
                    for (final String string : typeParamNames) {
                        checkTypeParamTag(
                            lineNo, tags, string);
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
    private boolean shouldCheck(final DetailAST ast)
    {
        final DetailAST mods = ast.findFirstToken(TokenTypes.MODIFIERS);
        final Scope declaredScope = ScopeUtils.getScopeFromMods(mods);
        final Scope scope =
            ScopeUtils.inInterfaceOrAnnotationBlock(ast)
                ? Scope.PUBLIC : declaredScope;
        final Scope surroundingScope = ScopeUtils.getSurroundingScope(ast);

        return scope.isIn(this.scope)
            && ((surroundingScope == null) || surroundingScope.isIn(this.scope))
            && ((excludeScope == null)
                || !scope.isIn(excludeScope)
                || ((surroundingScope != null)
                && !surroundingScope.isIn(excludeScope)));
    }

    /**
     * Gets all standalone tags from a given javadoc.
     * @param cmt the Javadoc comment to process.
     * @return all standalone tags from the given javadoc.
     */
    private List<JavadocTag> getJavadocTags(TextBlock cmt)
    {
        final JavadocTags tags = JavadocUtils.getJavadocTags(cmt,
            JavadocUtils.JavadocTagType.BLOCK);
        if (!allowUnknownTags) {
            for (final InvalidJavadocTag tag : tags.getInvalidTags()) {
                log(tag.getLine(), tag.getCol(), "javadoc.unknownTag",
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
                          Pattern formatPattern, String format)
    {
        if (formatPattern == null) {
            return;
        }

        int tagCount = 0;
        for (int i = tags.size() - 1; i >= 0; i--) {
            final JavadocTag tag = tags.get(i);
            if (tag.getTagName().equals(tagName)) {
                tagCount++;
                if (!formatPattern.matcher(tag.getArg1()).find()) {
                    log(lineNo, "type.tagFormat", "@" + tagName, format);
                }
            }
        }
        if (tagCount == 0) {
            log(lineNo, "type.missingTag", "@" + tagName);
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
            final List<JavadocTag> tags, final String typeParamName)
    {
        boolean found = false;
        for (int i = tags.size() - 1; i >= 0; i--) {
            final JavadocTag tag = tags.get(i);
            if (tag.isParamTag()
                && (tag.getArg1() != null)
                && (tag.getArg1().indexOf("<" + typeParamName + ">") == 0))
            {
                found = true;
            }
        }
        if (!found) {
            log(lineNo, "type.missingTag",
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
        final List<String> typeParamNames)
    {
        final Pattern pattern = Utils.getPattern("\\s*<([^>]+)>.*");
        for (int i = tags.size() - 1; i >= 0; i--) {
            final JavadocTag tag = tags.get(i);
            if (tag.isParamTag()) {

                if (tag.getArg1() != null) {

                    final Matcher matcher = pattern.matcher(tag.getArg1());
                    String typeParamName = null;

                    if (matcher.matches()) {
                        typeParamName = matcher.group(1).trim();
                        if (!typeParamNames.contains(typeParamName)) {
                            log(tag.getLineNo(), tag.getColumnNo(),
                                "javadoc.unusedTag",
                                JavadocTagInfo.PARAM.getText(),
                                "<" + typeParamName + ">");
                        }
                    }
                    else {
                        log(tag.getLineNo(), tag.getColumnNo(),
                            "javadoc.unusedTagGeneral");
                    }
                }
                else {
                    log(tag.getLineNo(), tag.getColumnNo(),
                        "javadoc.unusedTagGeneral");
                }
            }
        }
    }
}
