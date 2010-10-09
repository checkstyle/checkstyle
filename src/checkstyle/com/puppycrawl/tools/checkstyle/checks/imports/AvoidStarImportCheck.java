////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2010  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.imports;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Check that finds import statements that use the * notation.
 * </p>
 * <p>
 * Rationale: Importing all classes from a package or static
 * members from a class leads to tight coupling between packages
 * or classes and might lead to problems when a new version of a
 * library introduces name clashes.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="AvoidStarImport"&gt;
 *   &lt;property name="excludes" value="java.io,java.net,java.lang.Math"/&gt;
 *   &lt;property name="allowClassImports" value="false"/&gt;
 *   &lt;property name="allowStaticMemberImports" value="false"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * The optional "excludes" property allows for certain packages like
 * java.io or java.net to be exempted from the rule. It also is used to
 * allow certain classes like java.lang.Math or java.io.File to be
 * excluded in order to support static member imports.
 *
 * The optional "allowClassImports" when set to true, will allow starred
 * class imports but will not affect static member imports.
 *
 * The optional "allowStaticMemberImports" when set to true will allow
 * starred static member imports but will not affect class imports.
 *
 * @author Oliver Burn
 * @author <a href="bschneider@vecna.com">Bill Schneider</a>
 * @author Travis Schneeberger
 * @version 2.0
 */
public class AvoidStarImportCheck
    extends Check
{
    /** the packages/classes to exempt from this check. */
    private String[] mExcludes = new String[0];

    /** whether to allow all class imports */
    private boolean mAllowClassImports;

    /** whether to allow all static member imports */
    private boolean mAllowStaticMemberImports;

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.IMPORT, TokenTypes.STATIC_IMPORT};
    }

    /**
     * Sets the list of packages or classes to be exempt from the check.
     * The excludes can contain a .* or not.
     * @param aExcludes a list of package names/fully-qualifies class names
     * where star imports are ok
     */
    public void setExcludes(String[] aExcludes)
    {
        mExcludes = appendDotStar(aExcludes);
    }

    /**
     * Sets whether or not to allow all non-static class imports.
     * @param aAllow true to allow false to disallow
     */
    public void setAllowClassImports(boolean aAllow)
    {
        mAllowClassImports = aAllow;
    }

    /**
     * Sets whether or not to allow all static member imports.
     * @param aAllow true to allow false to disallow
     */
    public void setAllowStaticMemberImports(boolean aAllow)
    {
        mAllowStaticMemberImports = aAllow;
    }

    @Override
    public void visitToken(final DetailAST aAST)
    {
        if (!mAllowClassImports
            && aAST.getType() == TokenTypes.IMPORT)
        {
            final DetailAST startingDot =
                aAST.getFirstChild();
            logsStarredImportViolation(startingDot, mExcludes);
        }
        else if (!mAllowStaticMemberImports
            && aAST.getType() == TokenTypes.STATIC_IMPORT)
        {
            //must navigate past the static keyword
            final DetailAST startingDot =
                aAST.getFirstChild().getNextSibling();
            logsStarredImportViolation(startingDot, mExcludes);
        }
    }

    /**
     * Appends a .* to the end of the string in a given
     * array of excludes.
     * @param aExcludes array of excludes (either package or class)
     * @return array of excludes with .*
     */
    private String[] appendDotStar(String[] aExcludes)
    {
        final String[] excludes = new String[aExcludes.length];
        for (int i = 0; i < aExcludes.length; i++) {
            excludes[i] = aExcludes[i];
            if (!excludes[i].endsWith(".*")) {
                // force all package names to end with ".*" to disambiguate
                // "java.io"
                excludes[i] += ".*";
            }
        }
        return excludes;
    }

    /**
     * Gets the full import identifier.  If the import is a starred import and
     * it's not excluded then a violation is logged.
     * @param aStartingDot the starting dot for the import statement
     * @param aExcludes an array of excludes
     */
    private void logsStarredImportViolation(DetailAST aStartingDot,
        String[] aExcludes)
    {
        final FullIdent name = FullIdent.createFullIdent(aStartingDot);

        if (isStaredImport(name)) {
            if (!isExempt(name.getText(), aExcludes)) {
                log(aStartingDot.getLineNo(),
                    "import.avoidStar", name.getText());
            }
        }
    }

    /**
     * Checks is an import is a stared import.
     * @param aImportIdent the full import identifier
     * @return true if a start import false if not
     */
    private boolean isStaredImport(FullIdent aImportIdent)
    {
        if ((aImportIdent != null) && aImportIdent.getText().endsWith(".*")) {
            return true;
        }
        return false;
    }

    /**
     * Checks if a class of package is exempt from a give array of excludes.
     * @param aClassOrPackage the class or package
     * @param aExcludes an array of excludes
     * @return true if except false if not
     */
    private boolean isExempt(String aClassOrPackage, String[] aExcludes)
    {
        for (final String exclude : aExcludes) {
            if (aClassOrPackage.equals(exclude)) {
                return true;
            }
        }
        return false;
    }
}
