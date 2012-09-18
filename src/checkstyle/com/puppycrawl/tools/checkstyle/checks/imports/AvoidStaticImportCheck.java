////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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
 * Check that finds static imports.
 * </p>
 * <p>
 * Rationale: Importing static members can lead to naming conflicts
 * between class' members. It may lead to poor code readability since it
 * may no longer be clear what class a member resides (without looking
 * at the import statement).
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="AvoidStaticImport"&gt;
 *   &lt;property name="excludes"
 *       value="java.lang.System.out,java.lang.Math.*"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * The optional "excludes" property allows for certain classes via a star
 * notation to be excluded such as java.lang.Math.* or specific
 * static members to be excluded like java.lang.System.out for a variable
 * or java.lang.Math.random for a method.
 *
 * <p>
 * If you exclude a starred import on a class this automatically
 * excludes each member individually.
 * </p>
 *
 * <p>
 * For example:
 * Excluding java.lang.Math.* will allow the import of
 * each static member in the Math class individually like
 * java.lang.Math.PI
 * </p>
 * @author Travis Schneeberger
 * @version 1.0
 */
public class AvoidStaticImportCheck
    extends Check
{
    /** the classes/static members to exempt from this check. */
    private String[] mExcludes = new String[0];

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.STATIC_IMPORT};
    }

    /**
     * Sets the list of classes or static members to be exempt from the check.
     * @param aExcludes a list of fully-qualified class names/specific
     * static members where static imports are ok
     */
    public void setExcludes(String[] aExcludes)
    {
        mExcludes = aExcludes.clone();
    }

    @Override
    public void visitToken(final DetailAST aAST)
    {
        final DetailAST startingDot =
            aAST.getFirstChild().getNextSibling();
        final FullIdent name = FullIdent.createFullIdent(startingDot);

        if ((null != name) && !isExempt(name.getText())) {
            log(startingDot.getLineNo(), "import.avoidStatic", name.getText());
        }
    }

    /**
     * Checks if a class or static member is exempt from known excludes.
     *
     * @param aClassOrStaticMember
     *                the class or static member
     * @return true if except false if not
     */
    private boolean isExempt(String aClassOrStaticMember)
    {
        for (String exclude : mExcludes) {
            if (aClassOrStaticMember.equals(exclude)) {
                return true;
            }
            else if (exclude.endsWith(".*")) {
                //this section allows explicit imports
                //to be exempt when configured using
                //a starred import
                final String excludeMinusDotStar =
                    exclude.substring(0, exclude.length() - 2);
                if (aClassOrStaticMember.startsWith(excludeMinusDotStar)) {
                    final String member =
                        aClassOrStaticMember.substring(
                            excludeMinusDotStar.length() + 1);
                    //if it contains a dot then it is not a member but a package
                    if (member.indexOf('.') == -1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
