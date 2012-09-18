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
package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import java.io.File;

/**
 * Ensures there is a package declaration.
 * Rationale: Classes that live in the null package cannot be
 * imported. Many novice developers are not aware of this.
 *
 * @author <a href="mailto:simon@redhillconsulting.com.au">Simon Harris</a>
 * @author Oliver Burn
 */
public final class PackageDeclarationCheck extends Check
{
    /** is package defined. */
    private boolean mDefined;
    /** whether to ignore the directory name matches the package. */
    private boolean mIgnoreDirectoryName;

    /**
     * Set whether to ignore checking the directory name.
     * @param aValue the new value.
     */
    public void setIgnoreDirectoryName(boolean aValue)
    {
        mIgnoreDirectoryName = aValue;
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.PACKAGE_DEF};
    }

    @Override
    public int[] getRequiredTokens()
    {
        return getDefaultTokens();
    }

    @Override
    public void beginTree(DetailAST aAST)
    {
        mDefined = false;
    }

    @Override
    public void finishTree(DetailAST aAST)
    {
        if (!mDefined) {
            log(aAST.getLineNo(), "missing.package.declaration");
        }
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        mDefined = true;
        if (mIgnoreDirectoryName) {
            return;
        }

        // Calculate the directory name, but stripping off the last
        // part.
        final String fname = getFileContents().getFilename();
        final int lastPos = fname.lastIndexOf(File.separatorChar);
        final String dirname = fname.substring(0, lastPos);

        // Convert the found package name into the expected directory name.
        final DetailAST nameAST = aAST.getLastChild().getPreviousSibling();
        final FullIdent full = FullIdent.createFullIdent(nameAST);
        final String expected = full.getText().replace('.', File.separatorChar);

        // Finally see that the real directory ends with the expected directory
        if (!dirname.endsWith(expected)) {
            log(full.getLineNo(),
                full.getColumnNo(),
                "package.dir.mismatch",
                expected);
        }
    }
}
