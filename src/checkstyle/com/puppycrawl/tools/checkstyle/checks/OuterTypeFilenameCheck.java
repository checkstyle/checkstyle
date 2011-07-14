////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import java.io.File;

/**
 * Checks that the outer type name and the file name match.
 * @author Oliver Burn
 */
public class OuterTypeFilenameCheck extends Check
{
    /** indicates whether the first token has been seen in the file. */
    private boolean mSeenFirstToken;

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF, TokenTypes.ANNOTATION_DEF,
        };
    }

    @Override
    public void beginTree(DetailAST aAST)
    {
        mSeenFirstToken = false;
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        // Only check first declaration
        if (mSeenFirstToken) {
            return;
        }
        mSeenFirstToken = true;

        final String outerTypeName =
            aAST.findFirstToken(TokenTypes.IDENT).getText();

        // Calculate the file name without the leading path or
        // the trailing .java suffix. Will be lax and just remove whatever
        // is after the '.' character.
        String fname = getFileContents().getFilename();
        fname = fname.substring(fname.lastIndexOf(File.separatorChar) + 1);
        fname = fname.replaceAll("\\.[^\\.]*$", "");

        if (!(fname.equals(outerTypeName))) {
            log(aAST.getLineNo(), "type.file.mismatch");
        }
    }
}
