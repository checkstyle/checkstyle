package com.puppycrawl.tools.checkstyle.comments;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

class CompareTreesWithComments extends Check {
    static DetailAST expectedTree;

    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    @Override
    public int[] getDefaultTokens() {
        return ArrayUtils.EMPTY_INT_ARRAY;
    }

    @Override
    public int[] getAcceptableTokens() {
        return ArrayUtils.EMPTY_INT_ARRAY;
    }

    @Override
    public int[] getRequiredTokens() {
        return ArrayUtils.EMPTY_INT_ARRAY;
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        Assert.assertTrue(isAstEquals(expectedTree, rootAST));
    }

    private static boolean isAstEquals(DetailAST expected, DetailAST actual) {
        boolean result = false;
        if (expected == actual) {
            result = true;
        }
        else if (actual == null || expected == null) {
            result = false;
        } else {
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
                DetailAST childExpected = expected.getFirstChild();
                DetailAST childActual = actual.getFirstChild();
                result = isAstEquals(childExpected, childActual);
                if (result) {
                    DetailAST nextSiblingExpected = expected.getNextSibling();
                    DetailAST nextSiblingActual = actual.getNextSibling();
                    result = isAstEquals(nextSiblingExpected, nextSiblingActual);
                }
            }
        }
        if (!result) {
            System.out.println("Expected: " + expected + " | Actual: " + actual);
        }
        return result;
    }
}
