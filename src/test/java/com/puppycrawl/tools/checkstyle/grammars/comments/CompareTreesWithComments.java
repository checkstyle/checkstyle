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

package com.puppycrawl.tools.checkstyle.grammars.comments;

import org.junit.Assert;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

class CompareTreesWithComments extends AbstractCheck {
    private static DetailAST expectedTree;

    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    @Override
    public int[] getDefaultTokens() {
        return CommonUtils.EMPTY_INT_ARRAY;
    }

    @Override
    public int[] getAcceptableTokens() {
        return CommonUtils.EMPTY_INT_ARRAY;
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtils.EMPTY_INT_ARRAY;
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        if (!isAstEquals(expectedTree, rootAST)) {
            Assert.assertEquals(expectedTree, rootAST);
        }
    }

    private static boolean isAstEquals(DetailAST expected, DetailAST actual) {
        final boolean result;
        if (expected == actual) {
            result = true;
        }
        else if (actual == null || expected == null) {
            result = false;
        }
        else {
            result = isAstEqualsSafe(expected, actual);
        }
        return result;
    }

    private static boolean isAstEqualsSafe(DetailAST expected, DetailAST actual) {
        boolean result = false;

        if (expected.getType() == actual.getType()
                && expected.getLineNo() == actual.getLineNo()
                && expected.getColumnNo() == actual.getColumnNo()) {
            if (expected.getText() == null) {
                result = actual.getText() == null;
            }
            else if (expected.getText().equals(actual.getText())) {
                result = true;
            }
        }

        if (result) {
            final DetailAST childExpected = expected.getFirstChild();
            final DetailAST childActual = actual.getFirstChild();
            result = isAstEquals(childExpected, childActual);
            if (result) {
                final DetailAST nextSiblingExpected = expected.getNextSibling();
                final DetailAST nextSiblingActual = actual.getNextSibling();
                result = isAstEquals(nextSiblingExpected, nextSiblingActual);
            }
        }

        return result;
    }

    public static void setExpectedTree(DetailAST expectedTree) {
        CompareTreesWithComments.expectedTree = expectedTree;
    }
}
