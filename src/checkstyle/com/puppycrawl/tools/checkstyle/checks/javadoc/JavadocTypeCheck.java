////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2003  Oliver Burn
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
 * @version 1.0
 */
public class JavadocTypeCheck
    extends Check
{
    /** the scope to check for */
    private Scope mScope = Scope.PRIVATE;
    /** compiled regexp to match author tag **/
    private RE mAuthorTagRE;
    /** compiled regexp to match author tag content **/
    private RE mAuthorFormatRE;
    /** compiled regexp to match version tag **/
    private RE mVersionTagRE;
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
     * Set the author tag pattern.
     * @param aFormat a <code>String</code> value
     * @throws ConversionException unable to parse aFormat
     */
    public void setAuthorFormat(String aFormat)
        throws ConversionException
    {
        try {
            mAuthorTagRE = Utils.getRE("@author\\s+(.*$)");
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
            mVersionTagRE = Utils.getRE("@version\\s+(.*$)");
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
        return new int[] {TokenTypes.INTERFACE_DEF, TokenTypes.CLASS_DEF};
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST)
    {
        final DetailAST mods = aAST.findFirstToken(TokenTypes.MODIFIERS);
        final Scope declaredScope = ScopeUtils.getScopeFromMods(mods);
        final Scope typeScope =
            ScopeUtils.inInterfaceBlock(aAST) ? Scope.PUBLIC : declaredScope;
        if (typeScope.isIn(mScope)) {
            final Scope surroundingScope = ScopeUtils.getSurroundingScope(aAST);
            if ((surroundingScope == null) || surroundingScope.isIn(mScope)) {
                final FileContents contents = getFileContents();
                final int lineNo = aAST.getLineNo();
                final TextBlock cmt = contents.getJavadocBefore(lineNo);
                if (cmt == null) {
                    log(lineNo, "javadoc.missing");
                }
                else if (ScopeUtils.isOuterMostType(aAST)) {
                    // don't check author/version for inner classes
                    checkTag(lineNo, cmt.getText(), "@author",
                            mAuthorTagRE, mAuthorFormatRE, mAuthorFormat);
                    checkTag(lineNo, cmt.getText(), "@version",
                            mVersionTagRE, mVersionFormatRE, mVersionFormat);
                }
            }
        }
    }

    /**
     * Verifies that a type definition has a required tag.
     * @param aLineNo the line number for the type definition.
     * @param aCmt the Javadoc comment for the type definition.
     * @param aTag the required tag name.
     * @param aTagRE regexp for the full tag.
     * @param aFormatRE regexp for the tag value.
     * @param aFormat pattern for the tag value.
     */
    private void checkTag(
            int aLineNo,
            String[] aCmt,
            String aTag,
            RE aTagRE,
            RE aFormatRE,
            String aFormat)
    {
        if (aTagRE == null) {
            return;
        }

        int tagCount = 0;
        for (int i = 0; i < aCmt.length; i++) {
            final String s = aCmt[i];
            if (aTagRE.match(s)) {
                tagCount += 1;
                final int contentStart = aTagRE.getParenStart(1);
                final String content = s.substring(contentStart);
                if (!aFormatRE.match(content)) {
                    log(aLineNo, "type.tagFormat", aTag, aFormat);
                }

            }
        }
        if (tagCount == 0) {
            log(aLineNo, "type.missingTag", aTag);
        }

    }

}
