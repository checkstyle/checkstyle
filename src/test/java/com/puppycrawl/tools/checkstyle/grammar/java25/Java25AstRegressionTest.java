package com.puppycrawl.tools.checkstyle.grammar.java25;

import com.puppycrawl.tools.checkstyle.AbstractTreeTestSupport;
import org.junit.jupiter.api.Test;

public class Java25AstRegressionTest extends AbstractTreeTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/grammar/java25";
    }

    @Test
    public void testFlexibleConstructorBody() throws Exception {
        verifyAst(
            getNonCompilablePath(
                    "ExpectedFlexibleConstructorBody.txt"),
            getNonCompilablePath(
                    "InputFlexibleConstructorBody.java"));
    }

    @Test
    public void testFlexibleConstructorBodyWithReturn() throws Exception {
         verifyAst(
            getNonCompilablePath(
                    "ExpectedFlexibleConstructorBodyWithReturn.txt"),
            getNonCompilablePath(
                    "InputFlexibleConstructorBodyWithReturn.java"));
    }

    @Test
    public void testFlexibleConstructorBodyWithNestedClass() throws Exception {
        verifyAst(
            getNonCompilablePath(
                    "ExpectedFlexibleConstructorBodyWithNestedClass.txt"),
            getNonCompilablePath(
                    "InputFlexibleConstructorBodyWithNestedClass.java"));
    }
}
