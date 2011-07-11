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
package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;

/**
 * Holds the current file contents for global access when configured
 * as a TreeWalker sub-module. For example,
 * a filter can access the current file contents through this module.
 * @author Mike McMahon
 * @author Rick Giles
 */
public class FileContentsHolder
    extends Check
{
    /** The current file contents. */
    private static final ThreadLocal<FileContents> FILE_CONTENTS =
        new ThreadLocal<FileContents>();

    /** @return the current file contents. */
    public static FileContents getContents()
    {
        return FILE_CONTENTS.get();
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[0];
    }

    @Override
    public void beginTree(DetailAST aRootAST)
    {
        FILE_CONTENTS.set(getFileContents());
    }

    @Override
    public void destroy()
    {
        // This needs to be called in destroy, rather than finishTree()
        // as finishTree() is called before the messages are passed to the
        // filters. Without calling remove, there is a memory leak.
        FILE_CONTENTS.remove();
    }
}
