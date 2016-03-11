////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * Holds the current file contents for global access when configured
 * as a TreeWalker sub-module. For example,
 * a filter can access the current file contents through this module.
 * @author Mike McMahon
 * @author Rick Giles
 */
public class FileContentsHolder
    extends AbstractCheck {
    /** The current file contents. */
    private static final ThreadLocal<FileContents> FILE_CONTENTS = new ThreadLocal<>();

    /**
     * @return the current file contents.
     */
    public static FileContents getContents() {
        return FILE_CONTENTS.get();
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return CommonUtils.EMPTY_INT_ARRAY;
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        FILE_CONTENTS.set(getFileContents());
    }

    @Override
    public void destroy() {
        // This needs to be called in destroy, rather than finishTree()
        // as finishTree() is called before the messages are passed to the
        // filters. Without calling remove, there is a memory leak.
        FILE_CONTENTS.remove();
    }
}
