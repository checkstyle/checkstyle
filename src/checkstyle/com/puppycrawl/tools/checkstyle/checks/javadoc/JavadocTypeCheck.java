////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2005  Oliver Burn
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
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.ScopeUtils;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Utils;
import org.apache.commons.beanutils.ConversionException;
import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;

import java.util.Vector;

/**
 * <p>
 * Checks the Javadoc of a type.
 * By default, does not check for author or version tags.
 * The scope to verify is specified using the {@link Scope} class and
 * defaults to {@link Scope#PRIVATE}. To verify another scope,
 * set property scope to one of the {@link Scope} constants.
 * To define the format for an author tag or a version tag,
 * set property authorFormat or versionFormat respectively to a
 * <a href="http://jakarta.apache.org/regexp/apidocs/org/apache/regexp/RE.html">
 * regular expression</a>.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="JavadocType"/&gt;
 * </pre>
 * <p> An example of how to configure the check for the
 * {@link Scope#PUBLIC} scope is:
 *</p>
 * <pre>
 * &lt;module name="JavadocType"&gt;
 *    &lt;property name="scope" value="public"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p> An example of how to configure the check for an author tag
 *  and a version tag is:
 *</p>
 * <pre>
 * &lt;module name="JavadocType"&gt;
 *    &lt;property name="authorFormat" value="\S"/&gt;
 *    &lt;property name="versionFormat" value="\S"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p> An example of how to configure the check for a
 * CVS revision version tag is:
 *</p>
 * <pre>
 * &lt;module name="JavadocType"&gt;
 *    &lt;property name="versionFormat" value="\$Revision.*\$"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @author Oliver Burn
 * @author Michael Tamm
 * @version 1.1
 */
public class JavadocTypeCheck
    extends Check
{
    /** the scope to check for */
    private Scope mScope = Scope.PRIVATE;
    /** the visibility scope where Javadoc comments shouldn't be checked **/
    private Scope mExcludeScope;
    /** compiled regexp to match author tag content **/
    private RE mAuthorFormatRE;
    /** compiled regexp to match version tag content **/
    private RE mVersionFormatRE;
    /** regexp to match author tag content */
    private String mAuthorFormat;
    /** regexp to match version tag content */
    private String mVersionFormat;

    /**
     * Sets the scope to check.
     * @param aFrom string to set scope from
     */
    public void setScope(String aFrom)
    {
        mScope = Scope.getInstance(aFrom);
    }

    /**
     * Set the excludeScope.
     * @param aScope a <code>String</code> value
     */
    public void setExcludeScope(String aScope)
    {
        mExcludeScope = Scope.getInstance(aScope);
    }

    /**
     * Set the author tag pattern.
     * @param aFormat a <code>String</code> value
     * @throws ConversionException unable to parse aFormat
     */
    public void setAuthorFormat(String aFormat)
        throws ConversionException
    {
        try {
            mAuthorFormat = aFormat;
            mAuthorFormatRE = Utils.getRE(aFormat);
        }
        catch (RESyntaxException e) {
            throw new ConversionException("unable to parse " + aFormat, e);
        }
    }

    /**
     * Set the version format pattern.
     * @param aFormat a <code>String</code> value
     * @throws ConversionException unable to parse aFormat
     */
    public void setVersionFormat(String aFormat)
        throws ConversionException
    {
        try {
            mVersionFormat = aFormat;
            mVersionFormatRE = Utils.getRE(aFormat);
        }
        catch (RESyntaxException e) {
            throw new ConversionException("unable to parse " + aFormat, e);
        }

    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.INTERFACE_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
        };
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST)
    {
        if (shouldCheck(aAST)) {
            final FileContents contents = getFileContents();
            final int lineNo = aAST.getLineNo();
            final TextBlock cmt = contents.getJavadocBefore(lineNo);
            if (cmt == null) {
                log(lineNo, "javadoc.missing");
            }
            else if (ScopeUtils.isOuterMostType(aAST)) {
                // don't check author/version for inner classes
                Vector tags = getJavadocTags(cmt);
                checkTag(lineNo, tags, "author",
                         mAuthorFormatRE, mAuthorFormat);
                checkTag(lineNo, tags, "version",
                         mVersionFormatRE, mVersionFormat);
            }
        }
    }

    /**
     * Whether we should check this node.
     * @param aAST a given node.
     * @return whether we should check a given node.
     */
    private boolean shouldCheck(final DetailAST aAST)
    {
        final DetailAST mods = aAST.findFirstToken(TokenTypes.MODIFIERS);
        final Scope declaredScope = ScopeUtils.getScopeFromMods(mods);
        final Scope scope =
            ScopeUtils.inInterfaceOrAnnotationBlock(aAST)
                ? Scope.PUBLIC : declaredScope;
        final Scope surroundingScope = ScopeUtils.getSurroundingScope(aAST);

        return scope.isIn(mScope)
            && ((surroundingScope == null) || surroundingScope.isIn(mScope))
            && ((mExcludeScope == null)
                || !scope.isIn(mExcludeScope)
                || (surroundingScope != null)
                && !surroundingScope.isIn(mExcludeScope));
    }

    /**
     * Gets all standalone tags from a given javadoc.
     * @param aCmt teh Javadoc comment to process.
     * @return all standalone tags from the given javadoc.
     */
    private Vector getJavadocTags(TextBlock aCmt)
    {
        final String[] text = aCmt.getText();
        Vector tags = new Vector();
        RE tagRE = Utils.getRE("/\\*{2,}\\s*@([:alpha:]+)\\s");
        for (int i = 0; i < text.length; i++) {
            final String s = text[i];
            if (tagRE.match(s)) {
                final String tagName = tagRE.getParen(1);
                String content = s.substring(tagRE.getParenEnd(0));
                if (content.endsWith("*/")) {
                    content = content.substring(0, content.length() - 2);
                }
                tags.add(new JavadocTag(aCmt.getStartLineNo() + i,
                                        tagName,
                                        content.trim()));
            }
            tagRE = Utils.getRE("^\\s*\\**\\s*@([:alpha:]+)\\s");
        }
        return tags;
    }

    /**
     * Verifies that a type definition has a required tag.
     * @param aLineNo the line number for the type definition.
     * @param aTags tags from the Javadoc comment for the type definition.
     * @param aTag the required tag name.
     * @param aFormatRE regexp for the tag value.
     * @param aFormat pattern for the tag value.
     */
    private void checkTag(int aLineNo, Vector aTags, String aTag,
                          RE aFormatRE, String aFormat)
    {
        if (aFormatRE == null) {
            return;
        }

        int tagCount = 0;
        for (int i = aTags.size() - 1; i >= 0; i--) {
            final JavadocTag tag = (JavadocTag) aTags.get(i);
            if (tag.getTag().equals(aTag)) {
                tagCount++;
                if (!aFormatRE.match(tag.getArg1())) {
                    log(aLineNo, "type.tagFormat", "@" + aTag, aFormat);
                }
            }
        }
        if (tagCount == 0) {
            log(aLineNo, "type.missingTag", "@" + aTag);
        }
    }

}
