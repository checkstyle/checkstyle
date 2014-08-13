////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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
 * @author maxvetrenko
 */
public class OuterTypeFilenameCheck extends Check
{
    /** indicates whether the first token has been seen in the file. */
    private boolean mSeenFirstToken;

    /** Current file name*/
    private String mFileName;

    /** If file has public type*/
    private boolean mHasPublic;

    /** If first type has has same name as file*/
    private boolean mValidFirst;

    /** Outer type with mismatched file name*/
    private DetailAST mWrongType;

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
        mFileName = getFileName();
        mSeenFirstToken = false;
        mValidFirst = false;
        mHasPublic = false;
        mWrongType = null;
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        final String outerTypeName = aAST.findFirstToken(TokenTypes.IDENT).getText();
        if (!mSeenFirstToken) {

            if (mFileName.equals(outerTypeName)) {
                mValidFirst = true;
            }
            else {
                mWrongType = aAST;
            }
        }
        else {
            final DetailAST modifiers = aAST.findFirstToken(TokenTypes.MODIFIERS);
            if (modifiers.findFirstToken(TokenTypes.LITERAL_PUBLIC) != null
                    && aAST.getParent() == null)
            {
                mHasPublic = true;
            }
        }
        mSeenFirstToken = true;
    }

    @Override
    public void finishTree(DetailAST aRootAST)
    {
        if (!(mValidFirst || mHasPublic) && mWrongType != null) {
            log(mWrongType.getLineNo(), "type.file.mismatch");
        }
    }

    /**
     * Get source file name.
     * @return source file name.
     */
    private String getFileName()
    {
        String fname = getFileContents().getFilename();
        fname = fname.substring(fname.lastIndexOf(File.separatorChar) + 1);
        fname = fname.replaceAll("\\.[^\\.]*$", "");
        return fname;
    }
}
