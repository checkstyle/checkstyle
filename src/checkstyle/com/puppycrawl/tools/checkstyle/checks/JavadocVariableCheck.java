////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2002  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.Scope;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.ScopeUtils;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Checks that a variable has Javadoc comment
 *
 * @author <a href="mailto:checkstyle@puppycrawl.com">Oliver Burn</a>
 * @version 1.0
 */
public class JavadocVariableCheck
    extends Check
{
    private Scope mScope = Scope.PRIVATE;

    public void setScope(String aFrom)
    {
        mScope = Scope.getInstance(aFrom);
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.VARIABLE_DEF};
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST)
    {
        if (!ScopeUtils.inCodeBlock(aAST)) {
            final DetailAST mods = aAST.findFirstToken(TokenTypes.MODIFIERS);
            final Scope declaredScope = ScopeUtils.getScopeFromMods(mods);
            final Scope variableScope =
                ScopeUtils.inInterfaceBlock(aAST) ? Scope.PUBLIC : declaredScope;
            if (variableScope.isIn(mScope)) {
                final Scope surroundingScope = ScopeUtils.getSurroundingScope(aAST);
                if (surroundingScope.isIn(mScope)) {
                    final FileContents contents = getFileContents();
                    final String[] cmt =
                        contents.getJavadocBefore(aAST.getLineNo());
                    if (cmt == null) {
                        log(aAST.getLineNo(), aAST.getColumnNo(),
                            "javadoc.missing");
                    }
                }
            }
        }
    }

}
