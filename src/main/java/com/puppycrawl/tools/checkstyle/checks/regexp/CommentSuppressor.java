////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.regexp;

import com.puppycrawl.tools.checkstyle.api.FileContents;

/**
 * Implementation of a {@link MatchSuppressor} that suppresses based on
 * whether in a comment.
 * @author Oliver Burn
 */
class CommentSuppressor implements MatchSuppressor {
    /** File contents to check for comments. */
    private FileContents currentContents;

    @Override
    public boolean shouldSuppress(int startLineNo, int startColNo,
            int endLineNo, int endColNo) {
        return currentContents != null
                && currentContents.hasIntersectionWithComment(startLineNo,
                        startColNo, endLineNo, endColNo);
    }

    /**
     * Set the current file contents.
     * @param currentContents the new contents.
     */
    public void setCurrentContents(FileContents currentContents) {
        this.currentContents = currentContents;
    }
}
