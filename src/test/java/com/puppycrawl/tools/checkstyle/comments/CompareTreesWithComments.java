package com.puppycrawl.tools.checkstyle.comments;

import org.junit.Assert;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

public class CompareTreesWithComments extends Check {
    protected static DetailAST expectedTree;

    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[]{};
    }

    @Override
    public void beginTree(DetailAST aRootAST) {
        Assert.assertTrue(isAstEquals(expectedTree, aRootAST));
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
