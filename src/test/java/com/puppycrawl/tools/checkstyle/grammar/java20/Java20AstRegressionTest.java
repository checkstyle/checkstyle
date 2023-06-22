package com.puppycrawl.tools.checkstyle.grammar.java20;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractTreeTestSupport;

public class Java20AstRegressionTest extends AbstractTreeTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/grammar/java20";
    }


    @Test
    public void testRecordDecompositionEnhancedForLoop() throws Exception {
        verifyAst(
                getNonCompilablePath(
                        "ExpectedJava20RecordDecompositionEnhancedForLoop.txt"),
                getNonCompilablePath(
                        "InputJava20RecordDecompositionEnhancedForLoop.java"));
    }
}
