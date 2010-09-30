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
 * <p> Rationale: Importing all classes from a package leads to tight coupling
 * between packages and might lead to problems when a new version of a library
 * introduces name clashes.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="AvoidStarImport"&gt;
 *   &lt;property name="excludes" value="java.io,java.net"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * The optional "excludes" property allows for certain packages like
 * java.io or java.net to be exempted from the rule. Note that the excludes
 * property is not recursive, subpackages of excluded packages are not
 * automatically excluded.
 *
 * Compatible with Java 1.5 source.
 *
 * @author Oliver Burn
 * @author <a href="bschneider@vecna.com">Bill Schneider</a>
 * @version 1.0
 */
public class AvoidStarImportCheck
    extends Check
{
    /** the packages to exempt from this check. */
    private String[] mExcludes = new String[0];

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.IMPORT};
    }

    /**
     * Sets the list of packages to exempt from the check.
     * @param aExcludes a list of package names where star imports are ok
     */
    public void setExcludes(String[] aExcludes)
    {
        mExcludes = new String[aExcludes.length];
        for (int i = 0; i < aExcludes.length; i++) {
            mExcludes[i] = aExcludes[i];
            if (!mExcludes[i].endsWith(".*")) {
                // force all package names to end with ".*" to disambiguate
                // "java.io"
                mExcludes[i] = mExcludes[i] + ".*";
            }
        }
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        final FullIdent name = FullIdent.createFullIdentBelow(aAST);
        if ((name != null) && name.getText().endsWith(".*")) {
            boolean exempt = false;
            for (int i = 0; (i < mExcludes.length) && !exempt; i++) {
                if (name.getText().equals(mExcludes[i])) {
                    exempt = true;
                }
            }
            if (!exempt) {
                log(aAST.getLineNo(), "import.avoidStar", name.getText());
            }
        }
    }
}
